package com.example.quizonline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OdsAdapter(private val odsList: List<OdsModel>) :
    RecyclerView.Adapter<OdsAdapter.OdsViewHolder>() {

    class OdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.odsTitle)
        val subtitle: TextView = itemView.findViewById(R.id.odsSubtitle)
        val description: TextView = itemView.findViewById(R.id.odsDescription)
        val readMore: TextView = itemView.findViewById(R.id.odsReadMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OdsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ods_item_row, parent, false)
        return OdsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OdsViewHolder, position: Int) {
        val item = odsList[position]
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
        holder.description.text = item.description

        holder.readMore.setOnClickListener {
            if (holder.description.visibility == View.GONE) {
                holder.description.visibility = View.VISIBLE
                holder.readMore.text = "Ver menos"
            } else {
                holder.description.visibility = View.GONE
                holder.readMore.text = "Ler mais"
            }
        }
    }

    override fun getItemCount(): Int = odsList.size
}
