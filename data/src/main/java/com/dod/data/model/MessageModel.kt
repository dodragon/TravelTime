package com.dod.data.model

data class MessageModel(
    val isError: Boolean,
    val message: String
) : java.io.Serializable
