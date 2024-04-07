package com.molohala.infinitycore.community.repository

import com.molohala.infinitycore.community.domain.entity.Community
import org.springframework.data.jpa.repository.JpaRepository

interface CommunityJpaRepository: JpaRepository<Community, Long> {
}