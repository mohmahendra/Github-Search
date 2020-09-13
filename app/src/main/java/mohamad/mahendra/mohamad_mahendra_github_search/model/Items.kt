package mohamad.mahendra.mohamad_mahendra_github_search.model

import com.google.gson.annotations.SerializedName

data class Items(
    val items: List<Users>,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResult : Boolean
)