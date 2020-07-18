package com.flowz.consuminggithubapi.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flowz.consuminggithubapi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter (private val repoList: RepoResult): RecyclerView.Adapter<RepoListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(repoList.items[position])
    }

//    override fun getItemCount(): Int {
//       return repoList.items.size
//    }
override fun getItemCount(): Int = repoList.items.size



    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(repo: Item){

            itemView.username.text = repo.owner?.login.orEmpty()

            itemView.repoName.text = repo.full_name.orEmpty()

            itemView.repoDescription.text = repo.description.orEmpty()

            Picasso.get().load(repo.owner?.avatar_url).into(itemView.pics)
        }
    }

}