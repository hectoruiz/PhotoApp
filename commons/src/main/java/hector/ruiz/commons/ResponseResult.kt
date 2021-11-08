package hector.ruiz.commons

data class ResponseResult<T>(
    val errorCode: Int?,
    val data: T?
)
