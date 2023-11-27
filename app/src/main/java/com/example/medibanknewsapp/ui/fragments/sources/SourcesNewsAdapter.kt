package com.example.medibanknewsapp.ui.fragments.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.medibanknewsapp.data.model.SourcesResponse.Source
import com.example.medibanknewsapp.databinding.ItemSourcePreviewBinding
import com.example.medibanknewsapp.util.setSafeOnClickListener

class SourcesNewsAdapter(private val itemClickCallback: (Source) -> Unit) :
    RecyclerView.Adapter<SourcesNewsAdapter.SourcesNewsViewHolder>() {

    inner class SourcesNewsViewHolder(val binding: ItemSourcePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesNewsViewHolder {
        val binding =
            ItemSourcePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourcesNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SourcesNewsViewHolder, position: Int) {
        val source = differ.currentList[position]
        with(holder.binding) {
            sourceName.text = source.name
            sourceDescription.text = source.description
            sourceCategory.text=source.category
            root.setSafeOnClickListener {
                toggleSelection(source)
                itemClickCallback(source)
            }
            val bgColorRes = when {
                selectedItems.contains(source) -> {
                    android.R.color.darker_gray
                }
                else -> {
                    android.R.color.white
                }
            }
            root.setBackgroundResource(bgColorRes)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(newList: List<Source>, localSavedSources: List<Source>) {
        differ.submitList(newList)
        selectedItems.clear()
        selectedItems.addAll(localSavedSources)
        notifyDataSetChanged()
    }

    private fun toggleSelection(source: Source) {
        when {
            selectedItems.contains(source) -> {
                selectedItems.remove(source)
            }
            else -> {
                selectedItems.add(source)
            }
        }
        notifyDataSetChanged()
    }


    private val selectedItems = HashSet<Source>()
}
