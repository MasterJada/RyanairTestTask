package dev.jetlaunch.ryanairtesttask.utils


import android.widget.DatePicker
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import dev.jetlaunch.ryanairtesttask.view.CounterView
import java.util.*


@BindingAdapter("number")
fun setNumber(view: CounterView, number: Int) {
    if (view.getNumber() != number) {
        view.setNumber(number)
    }
}
@InverseBindingAdapter(attribute = "number")
fun getNumber(view: CounterView): Int{
    return view.getNumber()
}
@BindingAdapter(value = ["onNumberChanged","numberAttrChanged"], requireAll = false)
fun setListener(view: CounterView,
                numberChangeListener: CounterView.NumberChangeListener?,
                attrChangeListener: InverseBindingListener?){
    view.listener =   if(attrChangeListener == null) numberChangeListener
    else object : CounterView.NumberChangeListener{
        override fun onNumberChanged(number: Int) {
                attrChangeListener.onChange()
        }

    }
}

@BindingAdapter("date")
fun setDate(picker: DatePicker, date: Date?) {
    val calendar = Calendar.getInstance()
    calendar.time = date ?: Date()
    picker.updateDate(
        calendar.get(Calendar.YEAR),
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
}

@InverseBindingAdapter(attribute = "date")
fun getDate(datePicker: DatePicker): Date {
    val calendar = Calendar.getInstance()
    calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth, 0, 0, 0)
    return calendar.time
}

@BindingAdapter(value = ["onDateChanged", "dateAttrChanged"], requireAll = false)
fun setListeners(
    datePicker: DatePicker,
    dateChangedListener: DatePicker.OnDateChangedListener?,
    inverseBindingListener: InverseBindingListener?
) {
    val newListener = if (inverseBindingListener == null) dateChangedListener
    else DatePicker.OnDateChangedListener { _, _, _, _ -> inverseBindingListener.onChange() }
    datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth, newListener)
}