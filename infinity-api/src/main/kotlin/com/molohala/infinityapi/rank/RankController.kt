package com.molohala.infinityapi.rank

import com.molohala.infinitycore.rank.application.service.RankService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rank")
class RankController(
    val rankService: RankService
) {

}