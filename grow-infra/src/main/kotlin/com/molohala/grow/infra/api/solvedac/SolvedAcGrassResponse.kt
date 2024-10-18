package com.molohala.grow.infra.api.solvedac

import com.fasterxml.jackson.annotation.JsonCreator
import com.molohala.grow.core.info.application.dto.SolvedAcSolves

data class SolvedAcGrassResponse @JsonCreator constructor(
    val grass: List<SolvedAcSolves>
)