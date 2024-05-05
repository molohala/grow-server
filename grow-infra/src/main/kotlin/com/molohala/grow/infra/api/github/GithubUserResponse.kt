package com.molohala.grow.infra.api.github

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ArrayNode
import com.molohala.grow.core.info.application.dto.GithubContribute

data class GithubUserResponse @JsonCreator constructor(
    val user: GithubUser
)

data class GithubUser(
    val name: String?,
    val avatarUrl: String,
    val bio: String?,
    val contributionsCollection: ContributionsCollection
)

data class ContributionsCollection @JsonCreator constructor(
    val contributionCalendar: ContributionCalendar
)

@JsonDeserialize(using = ContributionCalendar.CalendarDeserializer::class)
data class ContributionCalendar(
    val totalContributions: Long,
    val weeks: List<GithubContribute>
) {
    class CalendarDeserializer : StdDeserializer<ContributionCalendar>(ContributionCalendar::class.java) {
        override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): ContributionCalendar {
            val tree = parser.codec.readTree<JsonNode>(parser)

            return ContributionCalendar(
                tree["totalContributions"].asLong(),
                (tree["weeks"] as ArrayNode).map {
                    it["contributionDays"].map { contribute ->
                        parser.codec.treeToValue(
                            contribute,
                            GithubContribute::class.java
                        )
                    }
                }.flatten()
            )
        }
    }
}