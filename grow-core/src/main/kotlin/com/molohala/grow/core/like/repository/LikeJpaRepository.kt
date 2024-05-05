package com.molohala.grow.core.like.repository

import com.molohala.grow.core.like.domain.entity.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository : JpaRepository<Like, Long>