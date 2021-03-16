package ru.godzilla65536.onlinesim.mapper.sms

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Component
import ru.godzilla65536.onlinesim.dto.sms.GetNumbersStatsResponse
import ru.godzilla65536.onlinesim.dto.sms.Service
import ru.godzilla65536.onlinesim.util.OBJECT_MAPPER
import ru.onlinesim.dto.Country
import ru.onlinesim.dto.sms.Service as OnlineSimService

@Component
class GetNumbersStatsMapper {

    fun mapResponse(response: JsonNode, country: Country): List<GetNumbersStatsResponse> {

        val convertedResponse = when (country) {
            Country.ALL_COUNTRIES -> response.elements().asSequence().map {
                OBJECT_MAPPER.treeToValue(it, ru.onlinesim.dto.sms.GetNumbersStatsResponse::class.java)
            }.toList()
            else -> listOf(
                OBJECT_MAPPER.treeToValue(response, ru.onlinesim.dto.sms.GetNumbersStatsResponse::class.java)
            )
        }

        return convertedResponse.map {
            GetNumbersStatsResponse(
                name = it.name,
                position = it.position,
                code = it.code,
                other = when (it.other) {
                    "false" -> null
                    else -> it.other?.toInt()
                },
                new = it.new,
                enabled = it.enabled,
                services = it.services.values.map { service -> mapService(service) }
            )
        }

    }

    private fun mapService(service: OnlineSimService) = Service(
        count = service.count,
        popular = service.popular,
        code = service.code,
        price = service.price,
        id = service.id,
        service = service.service,
        slug = service.slug
    )

}
