package com.psiance.albertsons_acronym_api.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psiance.albertsons_acronym_api.R
import com.psiance.albertsons_acronym_api.models.LongFormDTO

import kotlinx.android.synthetic.main.acronym_list_item.view.*

class AcronymListAdapter(private var onItemClick: (LongFormDTO) -> Unit) :
    CustomRecyclerViewAdapter<LongFormDTO, AcronymListAdapter.AcronymViewHolder>() {
    val acronymList: ArrayList<LongFormDTO> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymViewHolder =
        AcronymViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.acronym_list_item, parent, false),
            onItemClick
        )

    override fun onBindViewHolder(holder: AcronymViewHolder, position: Int) =
        holder.loadData(position)

    override fun getItemCount(): Int = acronymList.size

    inner class AcronymViewHolder(
        private val view: View,
        private val onClickItem: ((LongFormDTO) -> Unit?)?
    ) : RecyclerView.ViewHolder(view) {

        fun loadData(position: Int) {
            val data = acronymList[position]

            with(view) {
                tv_long_form.text = data.lf
                tv_freq.text = data.freq.toString()
                setOnClickListener {
                    onClickItem?.let { onClick -> onClick(data) }
                }
            }
        }

    }

    override fun updateData(data: List<LongFormDTO>) {
        this.acronymList.clear()
        if (data.isNotEmpty()) {
            this.acronymList.addAll(data)
        }
        notifyDataSetChanged()
    }
}