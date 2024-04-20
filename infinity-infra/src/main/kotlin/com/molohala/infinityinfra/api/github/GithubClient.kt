package com.molohala.infinityinfra.api.github

import com.fasterxml.jackson.databind.ObjectMapper
import com.molohala.infinitycore.info.GithubInfoClient
import com.molohala.infinitycore.info.application.dto.GithubContribute
import com.molohala.infinitycore.info.application.dto.GithubUserInfo
import org.slf4j.LoggerFactory
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GithubClient(
    private val graphQlClient: HttpGraphQlClient,
    private val objectMapper: ObjectMapper
) : GithubInfoClient {
    private val logger = LoggerFactory.getLogger(GithubClient::class.java)
    override fun getInfo(name: String): GithubUserInfo? {
        val res = graphQlClient.document(
            """
               |query q(${"$"}username: String!) {
               |	user(login: ${"$"}username) {
               |		name
               |		avatarUrl
               |		bio
               |		contributionsCollection {
               |			contributionCalendar {
               |				totalContributions
               |				weeks {
               |					contributionDays {
               |						date
               |						contributionCount
               |					}
               |				}
               |			}
               |		}
               |	}
               |}
            """.trimMargin()
        )
            .variables(mapOf("username" to name))
            .execute()
            .block()!!



        for (err in res.errors) {
            logger.error("error: {}", err)
        }

        val entity = objectMapper.convertValue(res.toMap()["data"], GithubUserResponse::class.java).user

        val calendar = entity.contributionsCollection.contributionCalendar

        val today = LocalDate.now()
        val weekContribute = today.minusDays(7).datesUntil(today).map { date ->
            calendar.weeks.find { it.date == date } ?: GithubContribute(date, 0)
        }

        return GithubUserInfo(
            name = entity.name,
            avatarUrl = entity.avatarUrl,
            bio = entity.bio,
            totalCommits = calendar.totalContributions,
            weekCommits = weekContribute.toList(),
            todayCommits = calendar.weeks.find { it.date == today } ?: GithubContribute(today, 0)
        )
    }
}