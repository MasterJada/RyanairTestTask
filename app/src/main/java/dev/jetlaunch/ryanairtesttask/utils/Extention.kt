package dev.jetlaunch.ryanairtesttask.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jetlaunch.ryanairtesttask.model.NetworkResponse
import java.text.SimpleDateFormat
import java.util.*


inline infix fun <T> T.getOr(block: (T) -> Unit): T {
    if (this == null) block(this)
    return this
}
infix fun<T> MutableLiveData<T>.post(value: T){
    this.postValue(value)
}

fun <T> T.toNetworkResponse(): NetworkResponse<T> {
    return NetworkResponse(this)
}

fun Date.toString(format: String):String{
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

inline fun <T> LiveData<T>.reobserve(owner: LifecycleOwner, crossinline func: (T) -> (Unit)) {
    removeObservers(owner)
    observe(owner, androidx.lifecycle.Observer {
        func(it)
    })
}