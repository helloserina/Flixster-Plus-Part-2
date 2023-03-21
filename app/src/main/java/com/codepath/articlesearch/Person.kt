package com.codepath.articlesearch

//import android.support.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Keep
@Serializable
data class SearchResponse(
    @SerialName("results")
    val results: List<Person>?
)

//@Keep
@Serializable
data class Person (
    @SerialName("name")
    val name: String?,

    @SerialName("popularity")
    val popularity: Float?,

    @SerialName("known_for_department")
    val known_for_department: String?,

    @SerialName("gender")
    val gender: Int?,

    @SerialName("profile_path")
    val profile_path: String?
) : java.io.Serializable {
    val profileImageUrl = "https://image.tmdb.org/t/p/w500/${profile_path}"
}