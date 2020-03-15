package m.tech.basemvvm.util

sealed class DataState<T>(
    var data: T? = null,
    var error: Event<String>? = null
) {
    class Success<T>(data: T) : DataState<T>(data = data)
    class Loading<T>() : DataState<T>()
    class Error<T>(message: String, data: T? = null) : DataState<T>(data = data, error = Event.createErrorEvent(message))
}











