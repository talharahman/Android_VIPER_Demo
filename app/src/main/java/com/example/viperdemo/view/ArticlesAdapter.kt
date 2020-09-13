package com.example.viperdemo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.viperdemo.R
import com.example.viperdemo.databinding.ArticleItemviewBinding
import com.example.viperdemo.entity.Article

class ArticlesAdapter() : ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ArticleItemviewBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticlesViewHolder(private var binding: ArticleItemviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.article = item
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("title")
fun TextView.setTitleHeader(item: Article?) {
    item?.let { text = item.title }
}

@BindingAdapter("source")
fun TextView.setSourceHeader(item: Article?) {
    item?.let { text = item.source.name }
}

@BindingAdapter("image")
fun ImageView.setImageHeader(item: String?) {
    if (item != null) {
        Glide
            .with(this)
            .load(item)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_baseline_broken_image))
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_baseline_broken_image)
    }
}




