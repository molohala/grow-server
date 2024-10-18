package com.molohala.grow.core.community.repository

import com.molohala.grow.core.community.domain.entity.Community
import org.springframework.data.jpa.repository.JpaRepository

interface CommunityJpaRepository : JpaRepository<Community, Long>