package dev.ruisantos.criticalnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import dev.ruisantos.criticalnews.databinding.RowHeadlineBinding
import dev.ruisantos.criticalnews.network.Article

class HeadlineAdapter(var items: List<Article>, val onClick: (Article) -> Unit) :
    RecyclerView.Adapter<HeadlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(
            RowHeadlineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.binding.apply {
            val article = items[position]
            tvTitle.text = article.title

            Glide.with(ivCover)
                .load(article.urlToImage)
                .into(ivCover)

            container.setOnClickListener {
                onClick.invoke(article)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class HeadlineViewHolder(val binding: RowHeadlineBinding) : ViewHolder(binding.root)

