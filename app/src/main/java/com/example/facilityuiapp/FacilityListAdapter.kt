package com.example.facilityuiapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.facilityuiapp.models.Exclusion
import com.example.facilityuiapp.models.Facility
import com.example.facilityuiapp.models.Option
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class FacilityListAdapter(private val listener: List<Facility>) : RecyclerView.Adapter<FacilityViewHolder>() {

    private var facilityList: MutableList<Facility> = ArrayList()
    private var exclusionList: MutableList<List<Exclusion>> = ArrayList()
    private var exHashMap: HashMap<Int, Int> = HashMap()
    private lateinit var context: Context
    private var rbHashMap: HashMap<Int, RadioButton> = HashMap() // radioButtonHashMap


    // hashmap that contains list of corresponding disabled RadioButtons with id
    private var disableRadioButtonHashMap: HashMap<Int, MutableList<RadioButton>> = HashMap()

    constructor(
        facilityList: MutableList<Facility>,
        exclusionList: MutableList<List<Exclusion>>,
        exHashMap: HashMap<Int, Int>,
        context: Context
    ) : this(facilityList) {
        this.facilityList = facilityList
        this.exclusionList = exclusionList
        this.exHashMap = exHashMap
        this.context = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            FacilityViewHolder = FacilityViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
    )


    override fun getItemCount(): Int = facilityList.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.titleText.text = facilityList[position].name

        val optionsList: MutableList<Option> = ArrayList()
        optionsList.addAll(facilityList[position].options)

        for (i in optionsList) {
            val radioButton = RadioButton(context)
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.text = i.name
            radioButton.id = Integer.parseInt(i.id)
            radioButton.textSize = 17.toFloat()

            rbHashMap[Integer.parseInt(i.id)] = radioButton
            holder.radioGroup.addView(radioButton)
        }

        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val facilityID: Int = Integer.parseInt(facilityList[position].facility_id)

            if (disableRadioButtonHashMap.containsKey(facilityID)) {
                disableRadioButtonHashMap[facilityID] = LinkedList()
            }

            if (exHashMap.containsKey(checkedId)) {
                var radioButton: RadioButton? = rbHashMap.get(exHashMap.get(checkedId))
                if (radioButton != null) {

                    if (!disableRadioButtonHashMap.containsKey(facilityID)) {
                        disableRadioButtonHashMap[facilityID] = LinkedList()
                    }

                    disableRadioButtonHashMap[facilityID]!!.add(radioButton)

                }
            }

            renderRadioButtons()
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderRadioButtons() {

        val disabled: HashSet<RadioButton> = HashSet()

        disableRadioButtonHashMap.forEach { items, buttonList -> disabled.addAll(buttonList) }

        rbHashMap.forEach { optionId, radioButton ->
            val enabled = !disabled.contains(radioButton)
            if (!enabled) radioButton.isChecked = false
            radioButton.setEnabled(enabled)
        }
    }
}


class FacilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleText: TextView = itemView.findViewById(R.id.textView)
    val radioGroup: RadioGroup  = itemView.findViewById(R.id.radioGroup)
}
