package com.example.krishnamaya1.stories

import com.example.krishnamaya1.authentication.data.KrishnamayaUser

data class Story (
    val storyId: String = "",
    val userId: String= "",
    val type: String = "image",
    val url: String= "",
    val seenBy: List<String> = emptyList<String>(),
    val seenByCount: Int = 0
)