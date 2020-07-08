package dev.jetlaunch.ryanairtesttask.model

data class NetworkResponse<T>(
    val data: T? = null,
    val errorMessage: String? = null
){
  val error: Boolean
    get() = errorMessage != null
}