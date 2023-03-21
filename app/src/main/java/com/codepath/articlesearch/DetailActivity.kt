package com.codepath.articlesearch

import android.app.ActionBar
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.w3c.dom.Text

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var departmentTextView: TextView
    private lateinit var popularityTextView: TextView
    private lateinit var genderTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // sets views to be altered
        mediaImageView = findViewById(R.id.personImage)
        nameTextView = findViewById(R.id.personName)
        departmentTextView = findViewById(R.id.personDepartment)
        popularityTextView = findViewById(R.id.personPopularity)
        genderTextView = findViewById(R.id.personGender)

        // intent will pass info from ArticleAdapter
        val article = intent.getSerializableExtra(ARTICLE_EXTRA) as Person

        // sets the new information pulled from
        nameTextView.text = "Name: " + article.name
        departmentTextView.text = "Department: " + article.known_for_department
        popularityTextView.text = "Popularity level: " + article.popularity.toString()
        val gender = if (article.gender == 1) "Female" else "Male"
        genderTextView.text = "Gender: $gender"

        val params = mediaImageView.layoutParams
        params.width = 500
        mediaImageView.setLayoutParams(params)

        Glide.with(this)
            .load(article.profileImageUrl)
            .into(mediaImageView)
    }
}