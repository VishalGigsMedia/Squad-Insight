package com.squad_insight.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squad_insight.R
import com.squad_insight.common_helper.DefaultHelper.decrypt
import com.squad_insight.databinding.RowItemDynamicLinkBinding
import com.squad_insight.ui.home.model.MatchDetailsModel


class DynamicLinksAdapter(private val context: Context, private val list: List<MatchDetailsModel.Data.Prediction.FantasyGameLink?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemDynamicLinkBinding: RowItemDynamicLinkBinding = RowItemDynamicLinkBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        mcontext = viewGroup.context

        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.row_item_dynamic_link, viewGroup, false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {

            val url = decrypt(list?.get(position)?.link.toString())
            //holder.itemDynamicLinkBinding.tvLinks.text = "https://www.google.com/"
            holder.itemDynamicLinkBinding.tvTitle.text = Html.fromHtml(decrypt(list?.get(position)?.title.toString()))
            holder.itemDynamicLinkBinding.tvDescription.text = Html.fromHtml(decrypt(list?.get(position)?.description.toString()))
            holder.itemDynamicLinkBinding.tvLinks.text = decrypt(list?.get(position)?.link.toString())

            holder.itemDynamicLinkBinding.tvLinks.setOnClickListener {
                setOpenUrl(url)
            }
        }
    }

    private fun setOpenUrl(url: String) {
        try {
            if (!checkNull(url)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }

        } catch (e: Exception) {
            e.message
        }

    }

    private fun checkNull(str: String): Boolean {
        if (str.isEmpty() && str == "null") {
            return true
        }
        return false
    }
}




