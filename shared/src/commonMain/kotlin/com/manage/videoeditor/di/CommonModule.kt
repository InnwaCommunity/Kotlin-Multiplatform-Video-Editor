package com.manage.videoeditor.di

import com.manage.videoeditor.domain.utils.Constants.BASE_URL
import com.manage.videoeditor.domain.utils.Constants.URL_PATH
import io.ktor.client.HttpClient
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule(enableNetworkLogs: Boolean) = module {
    single {
        HttpClient {
            expectSuccess = true
            addDefaultResponseValidation()

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path(URL_PATH)
                    parameters.append("api_key", BuildConfig)
                }
            }
        }
    }
}

expect fun platformModule() : Module