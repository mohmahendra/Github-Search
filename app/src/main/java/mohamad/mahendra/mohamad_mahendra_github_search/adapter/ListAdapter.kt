package mohamad.mahendra.mohamad_mahendra_github_search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_user.view.*
import mohamad.mahendra.mohamad_mahendra_github_search.R
import mohamad.mahendra.mohamad_mahendra_github_search.model.Users

class ListAdapter(val userList: MutableList<Users>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.rv_user, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.uname.text = user.login
        Picasso.get().load(user.avatarUrl)
            .resize(60,60)
            .into(holder.avatar)
    }

    fun clear() {
        userList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(inflView : View) : RecyclerView.ViewHolder(inflView){
        val uname : TextView = inflView.tv_uname
        val avatar : ImageView = inflView.iv_avatar
    }
}