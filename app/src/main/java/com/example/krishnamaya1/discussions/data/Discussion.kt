package com.example.krishnamaya1.discussions.data

data class Discussion(
    val userId: String = "",
    val discussionId: String = "",

    val text: String = "",

    val timeStamp: String = "",
    val listOfReplies: List<Reply> = emptyList()
)