package com.mtc.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mtc.rickandmorty.R
import com.mtc.rickandmorty.model.location.LocationModel

class LocationCategoryAdapter(
    var locations: List<LocationModel>,
    val listener: ILocationAdapterListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition = -1
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_category, parent, false)
                ItemViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            val location = locations[position]
            holder.locationChip.text = location.name

            if (position == selectedPosition) {
                holder.locationChip.setChipBackgroundColorResource(com.google.android.material.R.color.m3_chip_stroke_color)
            } else {
                holder.locationChip.setChipBackgroundColorResource(com.google.android.material.R.color.m3_chip_background_color)
            }
            holder.locationChip.setOnClickListener {
                selectedPosition = holder.adapterPosition
                listener.onClickLocation(location)
                notifyDataSetChanged()
            }
        } else if (holder is LoadingViewHolder) {
            // delay kullanmamız hem görsel olarak işimize yarayacak hemde sürekli istek atmanın önüne geçecek
            holder.progressBar.visibility = View.VISIBLE
            if (listener.loadingListener()){
                android.os.Handler().postDelayed({
                    listener.sendLocationRequest()
                    holder.progressBar.visibility = View.GONE
                },500)
            }else{
                holder.progressBar.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position <= locations.size-1) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun getItemCount(): Int {
        return if (locations.isEmpty()) {
            0
        } else {
            locations.size + 1 // Progress bar öğesi için 1 ekliyoruz
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationChip: Chip = view.findViewById(R.id.locationChip)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.loadingProgresbar)
    }

}