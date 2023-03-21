package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val peopleList: List<Person>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = peopleList[position]
        holder.bind(article)
    }

    override fun getItemCount() = peopleList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val mediaImageView = itemView.findViewById<ImageView>(R.id.personImage)
        private val nameTextView = itemView.findViewById<TextView>(R.id.personName)
        private val departmentTextView = itemView.findViewById<TextView>(R.id.personDepartment)
        private val popularityTextView = itemView.findViewById<TextView>(R.id.personPopularity)

        init {
            itemView.setOnClickListener(this)
        }

        // Write a helper method to help set up the onBindViewHolder method
        fun bind(person: Person) {
            nameTextView.text = person.name
            popularityTextView.text = person.popularity.toString()
            departmentTextView.text = person.known_for_department

            Glide.with(context)
                .load(person.profileImageUrl)
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            // Get selected person
            val article = peopleList[absoluteAdapterPosition]

            //  Navigate to Details screen and pass selected person
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
        }
    }


}