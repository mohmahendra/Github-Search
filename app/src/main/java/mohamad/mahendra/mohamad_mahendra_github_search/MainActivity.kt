package mohamad.mahendra.mohamad_mahendra_github_search

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import mohamad.mahendra.mohamad_mahendra_github_search.adapter.ListAdapter
import mohamad.mahendra.mohamad_mahendra_github_search.api.GithubApi
import mohamad.mahendra.mohamad_mahendra_github_search.listener.EditTextListener
import mohamad.mahendra.mohamad_mahendra_github_search.listener.ScrollListener
import mohamad.mahendra.mohamad_mahendra_github_search.model.Items
import mohamad.mahendra.mohamad_mahendra_github_search.model.Users
import mohamad.mahendra.mohamad_mahendra_github_search.service.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager : LinearLayoutManager
    private var adapter : ListAdapter? = null
    private var listUsers : MutableList<Users> = mutableListOf()
    private var handler : Handler = Handler()
    private var queryCurrent = ""
    private var currPage = 1
    private var querySearched = 0
    private val delay : Long = 2000
    private val perPage = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        initListener()

        adapter = ListAdapter(listUsers)
        recyclerView.adapter = adapter

    }

    fun initListener() {
        // Init Scroll Listener
        recyclerView.addOnScrollListener(object : ScrollListener(linearLayoutManager){
            override fun onLoadMore(currentPage : Int) {
                if (adapter?.itemCount != 0) {
                    currPage++
                    doSearch(queryCurrent, perPage, currPage)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (adapter?.itemCount == 0) {
                    resetValue()
                }
            }
        })

        // Init Text Listener
        et_search.addTextChangedListener(object : EditTextListener(){
            override fun clearList() {
                doResetList()
            }

            override fun removeCallback() {
                handler.removeCallbacksAndMessages(null)
            }

            override fun queryText() {
                if (queryCurrent != et_search.text.toString()){
                    if (querySearched < et_search.text.length) {
                        handler.postDelayed(handlerSearch(), delay)
                    }
                }
                querySearched = et_search.text.toString().length
            }
        })
    }

    fun handlerSearch() = Runnable {
        doResetList()
        doSearch(et_search.text.toString(), perPage, currPage)
    }

    fun doSearch(q: String, pp: Int, p: Int) {
        val api = GithubApi().build().create(GithubService::class.java)
        api.getUsers(q,pp,p).enqueue(object : Callback<Items>{
            override fun onResponse(call: Call<Items>, response: Response<Items>) {
                if (response.body()!!.totalCount > 0) {
                    if (response.code() == 200) {
                        response.body()?.items?.let {
                            listUsers.addAll(it)
                        }
                    }
                    response.body()?.items?.size?.let {
                        adapter?.notifyItemRangeInserted(listUsers.size, it)
                    }
                } else {
                    notFound(getString(R.string.text_dataNotFound))
                }
            }

            override fun onFailure(call: Call<Items>, t: Throwable) {
                notFound(getString(R.string.text_failedResponse))
            }
        })
        queryCurrent = q
    }

    fun doResetList() {
        if (adapter != null) {
            queryCurrent = ""
            currPage = 1
            listUsers.clear()
            adapter?.clear()
            adapter?.notifyItemRangeChanged(0,0)
            errorLayout.visibility = View.GONE
        }
    }

    fun notFound(msg : String) {
        errorLayout.visibility = View.VISIBLE
        tv_errorDescription.text = msg
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
