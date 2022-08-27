package com.android.developer.sequis_test.core.data.util

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


/**
 * Method to Filter Response
 *
 * @param from are representative feedback from API
 * @see Response
 **/
suspend fun <T> getResponse(
    from: suspend () -> retrofit2.Response<T>,
): Response<T> {
    return try {
        val response = from()
        if (response.isSuccessful && response.body() != null) {
            Response.Success(data = response.body()!!)
        } else {
            Response.Failed(response.message())
        }
    } catch (e: IOException) {
        Response.ConnectionError("Connection Error")

    } catch (e: HttpException) {
        e.generateErrorServer()
    }
}

private fun <T> HttpException.generateErrorServer(): Response.ServerError<T> {
    val jObjError = response()?.errorBody()?.string()?.let { JSONObject(it) }

    return (jObjError?.let { message ->
        Response.ServerError((message.toString()))
    } ?: Response.ServerError("Server Error"))
}


/**
 * Representative of Response State
 */
sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failed<T>(val message: String) : Response<T>()
    data class ConnectionError<T>(val message: String) : Response<T>()
    data class ServerError<T>(val message: String) : Response<T>()
}