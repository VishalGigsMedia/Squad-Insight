package com.prediction_hub

data class UpdateApplicationModel(
    val data: Data?,
    val force_logout: Int,
    val message: String,
    val status: Int
) {
    data class Data(
        val update_type: Int
    )
}