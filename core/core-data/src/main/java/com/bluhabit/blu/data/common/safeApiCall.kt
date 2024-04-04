/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.data.common

import android.util.Log
import com.bluhabit.blu.data.model.BaseResponse
import com.bluhabit.blu.data.model.BaseResponseError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import java.net.ConnectException

suspend inline fun <reified T> safeApiCall(call: () -> HttpResponse): Response<T> {
    return try {
        val response = call.invoke()
        when (response.status.value) {
            in 200..209 -> {
                val data = response.body<BaseResponse<T>>()
                if (data.statusCode in 200..209) {
                    Response.Result(data.data)
                } else {
                    Log.e("safeApiCall", "here")
                    Response.Error(data.message, data.statusCode)
                }
            }

            404 -> {
                Response.Error("Tidak dapat menemukan server.", 404)
            }

            else -> {
                val data = response.body<BaseResponse<List<Any>>>()
                Response.Error(data.message, data.statusCode)
            }
        }
    } catch (e: ClientRequestException) {
        val data = e.response.body<BaseResponseError<Any>>()
        if (data.statusCode == 1008) {
            Response.Error(buildString {
                append(data.message)
                append(" ")
                data.errorField.forEach {
                    it.forEach { (key, value) ->
                        append(key.plus(": ").plus(value).plus(","))
                    }
                }
            }, data.statusCode)
        } else {
            Response.Error(data.message, data.statusCode)
        }
    } catch (connectionException: ConnectException) {
        Response.Error("Failed connect to server", 503)
    } catch (e: NoTransformationFoundException) {
        Response.Error("Server error", 500)
    } catch (e: ConnectTimeoutException) {
        Response.Error("Server timeout", 500)
    } catch (e: ServerResponseException) {
        Response.Error("Server Error", 500)
    }
}
