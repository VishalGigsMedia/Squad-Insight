package com.project.prediction_application.ui.home.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.prediction_application.R
import com.project.prediction_application.common_helper.CustomRunnable
import com.project.prediction_application.common_helper.DefaultHelper.decrypt
import com.project.prediction_application.databinding.RowItemMatchListBinding
import com.project.prediction_application.ui.home.CricketMatchListFragment
import com.project.prediction_application.ui.home.model.MatchListModel
import java.text.SimpleDateFormat
import java.util.*

class MatchListAdapter(
    private val context: Context,
    private val list: List<MatchListModel.Data.Match>,
    private val matchListClickListener: CricketMatchListFragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mcontext: Context
    var customRunnable: CustomRunnable? = null
    private val handler = Handler()

    companion object {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemOffersBinding: RowItemMatchListBinding = RowItemMatchListBinding.bind(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position//ConstantHelper.VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = viewGroup.context

        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.row_item_match_list,
            viewGroup,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //val offerList = list?.get(position)
        if (holder is ItemViewHolder) {

            holder.itemOffersBinding.tvTitle.text = decrypt(list[position].title)
            holder.itemOffersBinding.tvFirstTeamName.text = "IND"
            holder.itemOffersBinding.tvSecondTeamName.text = "NZ"
            holder.itemOffersBinding.tvVs.text = "VS"
            holder.itemOffersBinding.tvDateTime.text = decrypt(list[position].match_date)
            val receivedTime = decrypt(list[position].match_date) //"25-03-2021 14:05:00"
            //println("receivedTime : $receivedTime")
            formatter.isLenient = false
            val curDate = Date()
            val curMillis = curDate.time
            val curTime = formatter.format(curDate)

              val receivedDate = formatter.parse(receivedTime)
              val receivedMillis = receivedDate.time

              val d1 = formatter.parse(curTime)
              val d2 = formatter.parse(receivedTime)
              println("d1 : $d1 d2 : $d2")
              val differenceInTime = d2.time - d1.time
              val differenceInDays = ((differenceInTime / (1000 * 60 * 60 * 24)) % 365)
              println("differenceInDays : $differenceInDays")
              if (differenceInDays.toInt() == 0) //only start when difference is less than 24 hrs
              {
                  if (receivedMillis > curMillis) {
                      customRunnable =
                          CustomRunnable(
                              handler,
                              holder.itemOffersBinding.tvDateTime,
                              receivedTime.toString()
                          )
                      handler.removeCallbacks(customRunnable!!)
                      customRunnable!!.holder = holder.itemOffersBinding.tvDateTime
                      handler.postDelayed(customRunnable!!, 100)
                  } else {
                      holder.itemOffersBinding.tvDateTime.text = "Match has been completed already on : $receivedTime"
                  }
              } else if (differenceInDays.toInt() > 0) {
                  holder.itemOffersBinding.tvDateTime.text = "Match will be start on : $receivedTime"
              } else {
                  holder.itemOffersBinding.tvDateTime.visibility = View.GONE
              }

            holder.itemOffersBinding.cvParent.setOnClickListener {
                matchListClickListener.onMatchClick("", "")
            }
        }

    }


    public interface MatchListClickListener {
        fun onMatchClick(id: String, browser: String)
    }

    //Unix seconds
    /* val unixSeconds: Long = 1617996600
     val date = Date(unixSeconds * 1000L)
     val jdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
     jdf.timeZone = TimeZone.getTimeZone("GMT") // -4
     val javaDate = jdf.format(date)
     val hrs = javaDate.split(" ")[1]
     println("javaDate : $javaDate hrs: $hrs")*/
}