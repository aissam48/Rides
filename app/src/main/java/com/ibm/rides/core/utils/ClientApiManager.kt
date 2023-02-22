package com.ibm.rides.core.utils


import com.ibm.rides.remote.HttpRoutes
import com.ibm.rides.remote.dto.ErrorHandler
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.JSONArray
import org.json.JSONTokener
import javax.inject.Inject

class ClientApiManager @Inject constructor(private val client: HttpClient) {

    suspend fun makeRequest(
        parameterFormData: ArrayList<Pair<String, Any>>? = null,
        successCallback: suspend (response: JSONArray) -> Unit,
        failureCallback: suspend (error: Resource.Error<*>) -> Unit
    ) {

        try {

            val httpResponse: HttpResponse? =
                client.get(HttpRoutes.RIDES_API) {

                    contentType(ContentType.Application.Json)

                    formData {
                        if (parameterFormData != null) {
                            for (param in parameterFormData) {
                                parameter(param.first, param.second)
                            }
                        }
                    }
                }


            val response: String? = httpResponse?.receive()
            val jsonObject = JSONTokener(response).nextValue() as JSONArray

            successCallback(jsonObject)

        } catch (e: RedirectResponseException) {
            // 3xx - Response
            failureCallback(
                Resource.Error<Any>(
                    message = "Error ${e.response.status.description}",
                    errorBody = ErrorHandler(
                        e.response.status.value,
                        e.response.content.toString(),
                    )
                )
            )
        } catch (e: ClientRequestException) {
            // 4xx - Response
            failureCallback(
                Resource.Error<Any>(
                    message = "Error ${e.message}",
                    errorBody = ErrorHandler(e.response.status.value, e.message)
                )
            )
        } catch (e: ServerResponseException) {
            // 5xx - Response
            failureCallback(
                Resource.Error<Any>(
                    message = "Error ${e.response.status.description}",
                    errorBody = ErrorHandler(
                        e.response.status.value,
                        e.response.content.toString(),
                    )
                )
            )
        } catch (e: Exception) {
            // 5xx - Response
            failureCallback(
                Resource.Error<Any>(
                    message = "Error ${e.message}",
                    errorBody = ErrorHandler(500, e.message.toString())
                )
            )

        }

    }
}
