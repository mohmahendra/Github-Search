package mohamad.mahendra.mohamad_mahendra_github_search.service

import mohamad.mahendra.mohamad_mahendra_github_search.model.Items
import mohamad.mahendra.mohamad_mahendra_github_search.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    fun getUsers(@Query("q") q: String,
                 @Query("per_page") per_page:Int,
                 @Query("page") page: Int)
            : Call<Items>
}