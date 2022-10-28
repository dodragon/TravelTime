package com.dod.data.model

import com.google.gson.annotations.SerializedName

data class GroupMember(
    @SerializedName("groupMemberId")
    val groupMemberId: Long,
    @SerializedName("userIdx")
    val userIdx: Long,
    @SerializedName("groupIdx")
    val groupIdx: Long
) : java.io.Serializable
