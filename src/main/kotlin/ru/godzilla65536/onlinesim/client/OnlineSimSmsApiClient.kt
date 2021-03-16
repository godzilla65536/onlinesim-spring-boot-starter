package ru.godzilla65536.onlinesim.client

import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import ru.godzilla65536.onlinesim.config.OnlineSimClientProps
import ru.godzilla65536.onlinesim.dto.sms.GetNumResponse
import ru.godzilla65536.onlinesim.dto.sms.GetNumbersStatsResponse
import ru.godzilla65536.onlinesim.mapper.sms.GetNumMapper
import ru.godzilla65536.onlinesim.mapper.sms.GetNumbersStatsMapper
import ru.onlinesim.dto.Country
import ru.onlinesim.dto.sms.GetNumRequest

class OnlineSimSmsApiClient(
    private val webClient: WebClient,
    private val props: OnlineSimClientProps,
    private val getNumbersStatsMapper: GetNumbersStatsMapper,
    private val getNumMapper: GetNumMapper,
) {

    suspend fun getNumbersStats(
        country: Country? = Country.RUSSIA,
    ): List<GetNumbersStatsResponse> {

        val onlineSimResponse = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(props.getNumbersStatsUrn.toString())
                    .queryParam("apikey", props.apiKey)
                    .queryParam("country", country!!.countryCode)
                    .build()
            }
            .retrieve()
            .bodyToMono(JsonNode::class.java)
            .awaitSingle()

        return getNumbersStatsMapper.mapResponse(onlineSimResponse, country!!)
    }


    suspend fun getNum(
        service: String,
        country: Country? = Country.RUSSIA,
    ): GetNumResponse {

        val onlineSimResponse = webClient.post()
            .uri(props.getNumUrn.toString())
            .body(
                BodyInserters.fromValue(
                    GetNumRequest(
                        props.apiKey,
                        service,
                        country!!.countryCode
                    )
                )
            )
            .retrieve()
            .bodyToMono(ru.onlinesim.dto.sms.GetNumResponse::class.java)
            .awaitSingle()

        return getNumMapper.mapResponse(onlineSimResponse)
    }

}