package net.bruty.un_motivational_quotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewPageAdapter(private var items: List<Quote>):
    RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val about: TextView = itemView.findViewById(R.id.about)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPageAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPageAdapter.Pager2ViewHolder, position: Int) {
        val quote = items[position]
        holder.title.text = quote.quote
        holder.about.text = StringBuilder()
            .append("Quote from: ")
            .append(quote.name)
            .append(" ")
            .append(quote.createdAt)
            .toString()
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}