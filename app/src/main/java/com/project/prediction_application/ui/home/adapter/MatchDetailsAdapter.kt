package com.project.prediction_application.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.prediction_application.R
import com.project.prediction_application.databinding.RowItemMatchDetailsBinding
import java.util.*

class MatchDetailsAdapter(
    private val context: Context,
    private val list: ArrayList<String>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mcontext: Context

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemMatchDetailsBinding: RowItemMatchDetailsBinding =
            RowItemMatchDetailsBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return 10//list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = viewGroup.context

        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.row_item_match_details,
            viewGroup,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {

            // holder.itemOffersBinding.tvTitle.text = "2nd Test Match Team Prediction"

        }
    }

}