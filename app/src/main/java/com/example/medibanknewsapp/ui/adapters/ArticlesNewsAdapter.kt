package com.example.medibanknewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.data.model.NewsResponse.Article
import com.example.medibanknewsapp.databinding.ItemArticlePreviewBinding
import com.example.medibanknewsapp.util.setSafeOnClickListener


class ArticlesNewsAdapter(private val itemClickCallback: (Article) -> Unit) :
    RecyclerView.Adapter<ArticlesNewsAdapter.ArticlesNewsViewHolder>() {

    inner class ArticlesNewsViewHolder(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesNewsViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        with(holder.binding) {
            articleTitle.text = article.title
            articleDescription.text = article.description.toString()
            articleAuthor.text = article.author
            Glide.with(holder.itemView)
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(articleImage)

            root.setSafeOnClickListener {
                itemClickCallback(article)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newList: List<Article>) {
        differ.submitList(newList)
    }
}
