package com.fastcampus.issueservice.model

import com.fastcampus.issueservice.domain.Comment
import com.fastcampus.issueservice.domain.Issue
import com.fastcampus.issueservice.domain.enums.IssuePriority
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.domain.enums.IssueType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class IssueRequest (

    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,

)

data class IssueResponse (
    val id: Long,
    val comments: List<CommentResponse> = emptyList(),
    val summary: String,
    val description: String,
    val userId: Long,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        // invoke를 이용한 DTO변
        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    comments = comments.sortedByDescending(Comment::id).map(Comment::toResponse),
                    summary = summary,
                    description = description,
                    userId = userId,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }

    }
}