package com.ssafy.preparetest.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Memo (
    val id : Int?,
    val name: String,
    val content: String,
    val createdAt : String,
    val rate : Int
) : Parcelable {
    override fun toString(): String {
        return "메모 : $name -> 내용 : $content"
    }
}
