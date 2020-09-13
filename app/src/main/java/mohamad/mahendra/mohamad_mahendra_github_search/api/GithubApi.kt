package mohamad.mahendra.mohamad_mahendra_github_search.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubApi {

    fun build() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}