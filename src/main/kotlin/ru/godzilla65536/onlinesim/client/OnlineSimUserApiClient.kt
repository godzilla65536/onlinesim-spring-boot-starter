package ru.godzilla65536.onlinesim.client

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.reactive.function.client.WebClient
import ru.godzilla65536.onlinesim.config.OnlineSimClientProps
import ru.godzilla65536.onlinesim.dto.user.GetBalanceResponse

class OnlineSimUserApiClient(
    private val webClient: WebClient,
    private val props: OnlineSimClientProps,
) {

    suspend fun getBalance(): GetBalanceResponse =
        webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(props.getBalanceUrn.toString())
                    .queryParam("apikey", props.apiKey)
                    .build()
            }
            .retrieve()
            .bodyToMono(GetBalanceResponse::class.java)
            .awaitSingle()

}