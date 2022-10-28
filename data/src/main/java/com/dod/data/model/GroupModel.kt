package com.dod.data.model

import com.google.gson.annotations.SerializedName

data class GroupModel (
    @SerializedName("idx")
    override val idx: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("leaderId")
    val leaderUserIdx: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("users")
    val users: List<GroupMember>
) : DefaultData, java.io.Serializable
