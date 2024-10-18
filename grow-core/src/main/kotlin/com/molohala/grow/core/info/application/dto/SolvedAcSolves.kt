package com.molohala.grow.core.info.application.dto

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.time.LocalDate

@JsonDeserialize(using = SolvedAcSolves.SolveDeserializer::class)
data class SolvedAcSolves(
    val date: LocalDate,
    val solvedCount: Int = 0,
    val keepStreakReason: String? = null
) {
    class SolveDeserializer : StdDeserializer<SolvedAcSolves>(SolvedAcSolves::class.java) {
        override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): SolvedAcSolves {
            val tree = parser.codec.readTree<JsonNode>(parser)
            val value = tree["value"]

            return SolvedAcSolves(
                date = LocalDate.parse(tree.get("date").asText()),
                solvedCount = if (value.isNumber) value.asInt() else 0,
                keepStreakReason = if (value.isTextual) value.asText() else null
            )
        }
    }
}
