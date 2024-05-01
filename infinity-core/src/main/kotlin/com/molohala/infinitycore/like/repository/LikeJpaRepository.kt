package com.molohala.infinitycore.like.repository

import com.molohala.infinitycore.like.domain.entity.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository : JpaRepository<Like, Long>