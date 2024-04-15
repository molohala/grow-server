package com.molohala.infinitycore.comment.repository

import com.molohala.infinitycore.comment.domain.entity.Comment
import org.springframework.data.repository.CrudRepository

interface CommentJpaRepository: CrudRepository<Comment, Long> {
}