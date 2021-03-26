package com.project.prediction_application.ui.home.model

data class MatchListModel(
    val `data`: Data,
    val force_logout: Int,
    val message: String?,
    val status: Int
) {
    data class Data(
        val match_list: List<Match>
    ) {
        data class Match(
            val id: String,
            val match_date: String,
            val title: String,
            val venue: String
        )
    }
}