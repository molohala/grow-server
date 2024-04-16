package com.molohala.infinityinfra.api.solvedac

import com.fasterxml.jackson.annotation.JsonCreator
import com.molohala.infinitycore.info.application.dto.SolvedAcSolves

data class SolvedAcGrassResponse @JsonCreator constructor(
    val grass: List<SolvedAcSolves>
)