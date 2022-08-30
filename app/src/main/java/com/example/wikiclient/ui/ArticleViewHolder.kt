package com.example.wikiclient.ui

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wikiclient.ui.model.Article
import jp.ac.it_college.std.s21018.qiitasample.databinding.ItemArticleBinding

class ArticleViewHolder (private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.title.text = article.title
        binding.thumbnail.load(article.thumbnailUrl)
    }
}
