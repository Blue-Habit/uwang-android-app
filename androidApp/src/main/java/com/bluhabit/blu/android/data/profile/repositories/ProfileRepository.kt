/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.data.profile.repositories

import android.content.Context
import android.graphics.Bitmap
import com.bluhabit.blu.android.data.authentication.DataConstant
import com.bluhabit.blu.android.data.authentication.datasource.remote.request.PersonalizeProfilePictureRequest
import com.bluhabit.blu.android.data.profile.datasource.remote.request.EditProfileRequest
import com.bluhabit.blu.android.data.profile.datasource.remote.response.UserCredentialResponse
import com.bluhabit.blu.data.common.Response
import com.bluhabit.blu.data.common.safeApiCall
import com.bluhabit.blu.data.persistence.SharedPref
import com.bluhabit.core.ui.ext.toFile
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.streams.asInput
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val sharedPref: SharedPref,
    @ApplicationContext private val context: Context
) {
    suspend fun getProfile(): Response<UserCredentialResponse> = safeApiCall<UserCredentialResponse> {
        httpClient.get("account/v1/profile")
    }

    suspend fun updateProfile(
        fullName: String,
        interestTopic: String,
        link: String,
        bio: String,
        username: String
    ): Response<UserCredentialResponse> = safeApiCall<UserCredentialResponse> {
        httpClient.post("account/v1/update-profile") {
            setBody(
                EditProfileRequest(
                    fullName = fullName,
                    interestTopic = interestTopic,
                    link = link,
                    bio = bio,
                    username = username
                )
            )
        }
    }

    suspend fun uploadProfilePicture(
        bitmap: Bitmap
    ): Response<String> {
        val file = bitmap.toFile(context)
        return when (val result = safeApiCall<String> {
            httpClient.post("storage/v1/upload-profile-picture") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("file", InputProvider { file.inputStream().asInput() }, Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=user.png")
                            })
                        },
                        boundary = "WebAppBoundary"
                    )
                )
                onUpload { bytesSentTotal, contentLength ->
                    //tracking progress
                }
            }
        }) {
            is Response.Error -> result
            is Response.Result -> {
                if (result.data.isNotEmpty()) {
                    sharedPref.setPersistData(DataConstant.KEY_PROFILE_PICTURE, result.data)
                }

                result
            }
        }
    }

    suspend fun updateProfilePicture(): Response<String> {
        val url = sharedPref.getPersistData(DataConstant.KEY_PROFILE_PICTURE) ?: ""
        return safeApiCall {
            httpClient.post("account/v1/update-profile-picture") {
                setBody(PersonalizeProfilePictureRequest(url = url))
            }
        }
    }
}