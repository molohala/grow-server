package com.molohala.grow.core.comment.repository

import com.molohala.grow.core.comment.domain.entity.Comment
import org.springframework.data.repository.CrudRepository

interface CommentJpaRepository : CrudRepository<Comment, Long>