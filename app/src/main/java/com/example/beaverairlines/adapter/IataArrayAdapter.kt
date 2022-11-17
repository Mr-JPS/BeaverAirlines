package com.example.beaverairlines.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.Iata

class IataArrayAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    iata: List<Iata>
) :
    ArrayAdapter<Iata>(mContext, mLayoutResourceId, iata) {

    private val iataCodes: MutableList<Iata> = ArrayList(iata)
    private var allIataCodes: List<Iata> = ArrayList(iata)

    override fun getCount(): Int {
        return iataCodes.size

    }
    override fun getItem(position: Int): Iata {
        return iataCodes[position]

    }
//    override fun getItemId(position: Int): Long {
//        return iataCodes[position].iata.toLong()
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val iata: Iata = getItem(position)
            val cityAutoCompleteView = convertView!!.findViewById<View>(R.id.item_iataAirportName) as TextView
            cityAutoCompleteView.text = iata.name
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any) :String {
                return (resultValue as Iata).name
            }
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val iataNameSuggestion: MutableList<Iata> = ArrayList()
                    for (iataCodes in allIataCodes) {
                        if (iataCodes.name.toLowerCase().startsWith(constraint.toString().toLowerCase())
                        ) {
                            iataNameSuggestion.add(iataCodes)
                        }
                    }
                    filterResults.values = iataNameSuggestion
                    filterResults.count = iataNameSuggestion.size
                }
                return filterResults
            }
            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                iataCodes.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is Iata) {
                            iataCodes.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    iataCodes.addAll(allIataCodes)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
