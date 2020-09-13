package mohamad.mahendra.mohamad_mahendra_github_search.model

import com.google.gson.annotations.SerializedName

data class Users(
    val login: String,
    val id: Int,
    val url: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("repos_url")
    val reposUrl: String
)