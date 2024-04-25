package com.molohala.infinitycore.rank.application.scheduler

import com.molohala.infinitycore.info.GithubInfoClient
import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import com.molohala.infinitycore.member.domain.consts.SocialType
import com.molohala.infinitycore.member.domain.entity.RedisSocialAccount
import com.molohala.infinitycore.member.repository.SocialAccountJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Component
class RankScheduler(
    val redisTemplate: RedisTemplate<String, RedisSocialAccount>,
    val githubInfoClient: GithubInfoClient,
    val socialAccountJpaRepository: SocialAccountJpaRepository
) {
    private val logger = LoggerFactory.getLogger(RankScheduler::class.java)

    @Scheduled(fixedRate = 1000L * 60L * 60L)
    fun githubScheduler() {
        val accounts = socialAccountJpaRepository.findSocialAccountsBySocialType(SocialType.GITHUB)
        val iter = accounts.iterator()
        val service = Executors.newFixedThreadPool(4) as ThreadPoolExecutor

        val infos = hashMapOf<RedisSocialAccount, GithubUserInfo>()
        while (iter.hasNext()) {
            if (service.activeCount == service.maximumPoolSize) {
                Thread.sleep(100L) // wait for finish
                continue
            }
            val run = Runnable {
                val nextAccount = iter.next()
                githubInfoClient.getInfo(nextAccount.socialId)?.let {
                    infos[RedisSocialAccount.from(nextAccount)] = it
                }
            }
            service.submit(run).cancel(false)
        }

        service.shutdown()
        service.awaitTermination(10, TimeUnit.SECONDS) // yes

        while (service.activeCount > 0) {
            logger.info("waiting cause executor service is running.. (bruh)")
            Thread.sleep(100L) // Awaited but not finishing well
        }

        val zSet = redisTemplate.opsForZSet()
        logger.info("got: {}", infos.size)

        for ((key, inf) in infos) {
            zSet.remove("githubWeek", key)
            zSet.remove("githubToday", key)
            zSet.add("githubTotal", key, inf.totalCommits.toDouble())
            zSet.add("githubWeek", key, inf.weekCommits.sumOf { it.contributionCount }.toDouble())
            zSet.add("githubToday", key, inf.todayCommits.contributionCount.toDouble())
        }

        logger.info("fin. {} {} {}", service.isTerminating, service.isShutdown, service.activeCount)
    }
}