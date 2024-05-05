package com.molohala.grow.infra.api.github

import com.fasterxml.jackson.databind.ObjectMapper
import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import com.molohala.grow.core.info.GithubInfoClient
import com.molohala.grow.core.info.application.dto.GithubContribute
import com.molohala.grow.core.info.application.dto.GithubUserInfo
import com.molohala.grow.core.info.exception.InfoExceptionCode
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
            if (err.message?.contains("Could not resolve to a User with the login") == true) // err.errorType == ErrorType.NOT_FOUND && err.path == "user"
                throw CustomException(InfoExceptionCode.USER_NOT_FOUND) // handled, ErrorType throws Exception cause their parsing system is broken

            logger.error("Error on GraphQL Query: {}", err) // info for debugging
            throw CustomException(GlobalExceptionCode.INTERNAL_SERVER) // not handled. throwing
        }

        val data = res.toMap()["data"] // validate success
        val entity = objectMapper.convertValue(data, GithubUserResponse::class.java).user

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