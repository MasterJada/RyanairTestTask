package dev.jetlaunch.ryanairtesttask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import dev.jetlaunch.ryanairtesttask.entity.EntityStation
import java.util.*
import kotlin.collections.ArrayList

class StationsAdapter(
    context: Context,
    private val resource: Int = android.R.layout.simple_dropdown_item_1line,
    val objects: MutableList<EntityStation>
) :
    ArrayAdapter<EntityStation>(context, resource, objects) {
    private val allItems: List<EntityStation> = ArrayList(objects)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view =
            convertView ?: LayoutInflater.from(parent.context).inflate(resource, parent, false)

        getItem(position)?.let {
            view.findViewById<TextView>(android.R.id.text1).text = "${it.countryName} ${it.code}"
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(request: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                val search = request.toString().toLowerCase(Locale.ROOT)
                val items = allItems.filter {
                    it.countryName.toLowerCase(Locale.ROOT).contains(search) || it.code.toLowerCase(
                        Locale.ROOT
                    ).contains(search)
                }
                filterResult.count = items.size
                filterResult.values = items
                return filterResult
            }

            override fun publishResults(request: CharSequence?, filterResult: FilterResults?) {
                filterResult?.let { result ->
                    if (result.count > 0) {
                        (filterResult.values as? List<EntityStation>)?.let { items ->
                            clear()
                            addAll(items)
                        }
                    } else {
                        clear()
                    }
                    notifyDataSetChanged()
                }
            }

        }
    }
}