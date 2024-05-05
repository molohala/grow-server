package com.molohala.grow.core.comment.domain.entity

import com.molohala.grow.core.comment.domain.consts.CommentState
import com.molohala.grow.core.common.BaseIdAndTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "tbl_comment")
class Comment(
    content: String,
    commentState: CommentState,
    memberId: Long,
    communityId: Long
): BaseIdAndTimeEntity(null, null) {
    @Column(nullable = false)
    var content = content
        private set

    @Column(nullable = false)
    var commentState = commentState
        private set

    @Column(nullable = false)
    var memberId = memberId
        private set

    @Column(nullable = false)
    var communityId = communityId
        private set

    fun delete(){
        commentState = CommentState.DELETED
    }

    fun modify(content: String){
        this.content = content
    }
}