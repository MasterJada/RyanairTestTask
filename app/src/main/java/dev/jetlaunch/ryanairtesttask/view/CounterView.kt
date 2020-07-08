package dev.jetlaunch.ryanairtesttask.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import dev.jetlaunch.ryanairtesttask.R

class CounterView: LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val minusBtn = ImageButton(context)
    private val plusBtn = ImageButton(context)
    private val textView = TextView(context)
    private var minValue = 0
    private var maxValue = 100

    var listener: NumberChangeListener? = null

    private var number = 0

    init {

        orientation = HORIZONTAL
        setBackgroundResource(R.drawable.num_picker_text)
        minusBtn.setImageResource(R.drawable.ic_remove)
        plusBtn.setImageResource(R.drawable.ic_add)
        textView.text = number.toString()
        textView.maxLines = 1

        addView(minusBtn)
        addView(textView)
        addView(plusBtn)

        plusBtn.setOnClickListener(::onClick)
        minusBtn.setOnClickListener(::onClick)
        setupTextView()
    }


    private fun setupTextView() {
        val lp = textView.layoutParams as LayoutParams
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        textView.layoutParams = lp
        textView.gravity = Gravity.CENTER
        textView.textSize = 24F
    }

    private fun onClick(v: View) {
        when (v) {
            plusBtn -> if (number < maxValue){
                number++
                textView.text = number.toString()
                listener?.onNumberChanged(number)
            }
            minusBtn -> if (number > minValue){
                number--
                textView.text = number.toString()
                listener?.onNumberChanged(number)
            }
        }


    }


    fun getNumber(): Int = number
    fun setNumber(num: Int) {
        number = num
        textView.text = num.toString()
    }


    interface NumberChangeListener {
        fun onNumberChanged(number: Int)
    }
}

