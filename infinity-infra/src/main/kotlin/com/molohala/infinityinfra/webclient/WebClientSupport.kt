package com.molohala.infinityinfra.webclient

import com.molohala.infinitycommon.exception.GlobalExceptionCode
import com.molohala.infinitycommon.exception.custom.CustomException
import com.molohala.infinityinfra.webclient.exception.WebClientException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WebClientSupport(
    private val webClient:WebClient
) {
    private val log = LoggerFactory.getLogger(WebClientSupport::class.java)

    fun <T> get(url: String, responseDtoClass: Class<T>, vararg headers: String): Mono<T> {
        return webClient.get()
            .uri(url)
            .headers { convertStringToHttpHeaders(*headers).invoke(it) }
            .retrieve()
            .onStatus(HttpStatusCode::isError, ::onError)
            .bodyToMono(responseDtoClass)
    }

    fun <T, V : Any> post(url: String, body: V, responseClass: Class<T>, vararg headers: String?): Mono<T> {
        return webClient.post()
            .uri(url)
            .headers { convertStringToHttpHeaders(*headers).invoke(it) }
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatusCode::isError, ::onError)
            .bodyToMono(responseClass)
    }

    fun <T> postWithFormData(
        url: String,
        body: MultiValueMap<String, String>,
        responseClass: Class<T>,
        vararg headers: String
    ): Mono<T> {
        return webClient.post()
            .uri(url)
            .headers { convertStringToHttpHeaders(*headers).invoke(it) }
            .body(BodyInserters.fromFormData(body))
            .retrieve()
            .onStatus(HttpStatusCode::isError, ::onError)
            .bodyToMono(responseClass)
    }

    private fun onError(response: ClientResponse): Mono<Throwable> {
        log.error("WebClient Error Status : ${response.statusCode()}")
        log.error("WebClient Error Body : ${response.bodyToMono(String::class.java)}")
        throw WebClientException(response.statusCode().value())
    }

    private fun convertStringToHttpHeaders(vararg headers: String?): (HttpHeaders) -> Unit {
        if (headers.size % 2 != 0) {
            throw CustomException(GlobalExceptionCode.INTERNAL_SERVER)
        }
        return { httpHeaders ->
            headers.asList().chunked(2) { (key, value) ->
                if (key != null) {
                    httpHeaders.add(key, value)
                }
            }
        }
    }
}