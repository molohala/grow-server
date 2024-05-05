package com.molohala.grow.core.rank.application.scheduler

import com.molohala.grow.core.info.GithubInfoClient
import com.molohala.grow.core.info.SolvedAcInfoClient
import com.molohala.grow.core.info.application.dto.GithubUserInfo
import com.molohala.grow.core.info.application.dto.SolvedAcSolves
import com.molohala.grow.core.member.domain.consts.SocialType
import com.molohala.grow.core.member.repository.SocialAccountQueryRepository
import com.molohala.grow.core.rank.domain.dto.RedisSocialAccount
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Suppress("LoggingSimilarMessage")
@Component
class RankScheduler(
    val redisTemplate: RedisTemplate<String, RedisSocialAccount>,
    val githubInfoClient: GithubInfoClient,
    val socialAccountJpaRepository: SocialAccountQueryRepository,
    val solvedAcInfoClient: SolvedAcInfoClient
) {
    private val logger = LoggerFactory.getLogger(RankScheduler::class.java)

    @Scheduled(fixedRate = 1000L * 60L * 60L)
    fun githubScheduler() {
        val accounts = socialAccountJpaRepository.getSocialAccountsWithMemberInfo(SocialType.GITHUB)
        val iter = accounts.iterator()
        val service = Executors.newFixedThreadPool(5) as ThreadPoolExecutor

        val infos = hashMapOf<RedisSocialAccount, GithubUserInfo>()
        while (iter.hasNext()) {
            if (service.activeCount == service.maximumPoolSize) {
                Thread.sleep(100L) // wait for finish
                continue
            }
            val run = Runnable {
                val nextAccount = iter.next()
                githubInfoClient.getInfo(nextAccount.socialId)?.let {
                    infos[nextAccount] = it
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

        redisTemplate.delete(
            listOf(
                "githubWeek", "githubToday", "githubTotal"
            )
        )

        for ((userInfo, inf) in infos) {
            zSet.add("githubTotal", userInfo, inf.totalCommits.toDouble())
            zSet.add("githubWeek", userInfo, inf.weekCommits.sumOf { it.contributionCount }.toDouble())
            zSet.add("githubToday", userInfo, inf.todayCommits.contributionCount.toDouble())
        }

        logger.info("fin. {} {} {}", service.isTerminating, service.isShutdown, service.activeCount)
    }

    @Scheduled(fixedRate = 1000L * 60L * 60L)
    fun solvedAcScheduler() {
        val accounts = socialAccountJpaRepository.getSocialAccountsWithMemberInfo(SocialType.SOLVED_AC)
        val iter = accounts.iterator()
        val service = Executors.newFixedThreadPool(5) as ThreadPoolExecutor

        // TODO: tier for each user caching
        val infos =
            hashMapOf<RedisSocialAccount, List<SolvedAcSolves>/*Pair<SolvedAcUserInfo, List<SolvedAcSolves>>*/>()
        while (iter.hasNext()) {
            if (service.activeCount == service.maximumPoolSize) {
                Thread.sleep(100L) // wait for finish
                continue
            }
            val run = Runnable {
                val nextAccount = iter.next()
                solvedAcInfoClient.getUserGrass(nextAccount.socialId).takeIf { it.isNotEmpty() }?.let {
                    infos[nextAccount] = it.sortedBy { it.date }
                }
//                solvedAcInfoClient.getUserInfo(nextAccount.socialId)?.let {
//                    infos[nextAccount] = it to solvedAcInfoClient.getUserGrass(nextAccount.socialId)
//                }
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

        redisTemplate.delete(
            listOf(
                "solvedAcWeek", "solvedAcToday", "solvedAcTotal"
            )
        )

        for ((userInfo, grass) in infos) {
            zSet.add("solvedAcTotal", userInfo, grass.sumOf { it.solvedCount }.toDouble())
            zSet.add("solvedAcWeek", userInfo,
                List(7) {
                    val date = LocalDate.now().minusDays(7L - it)
                    grass.lastOrNull { g -> date == g.date } ?: SolvedAcSolves(date)
                }.sumOf { it.solvedCount }.toDouble()
            )
            zSet.add("solvedAcToday",
                userInfo,
                (grass.lastOrNull { it.date == LocalDate.now() }
                    ?: SolvedAcSolves(LocalDate.now())).solvedCount.toDouble())
        }

        logger.info("fin. {} {} {}", service.isTerminating, service.isShutdown, service.activeCount)
    }
}