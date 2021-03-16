package ru.godzilla65536.onlinesim.mapper.sms

import org.springframework.stereotype.Component
import ru.godzilla65536.onlinesim.dto.sms.GetNumResponse

@Component
class GetNumMapper {

    fun mapResponse(response: ru.onlinesim.dto.sms.GetNumResponse) =
        GetNumResponse(
            response = response.response,
            operationId = response.tzid,
            number = response.number
        )

}
