package ru.godzilla65536.onlinesim.dto.sms

data class GetNumbersStatsResponse(
    val name: String?,
    val position: Int?,
    val code: Int?,
    val other: Int?,
    val new: Boolean?,
    val enabled: Boolean?,
    val services: List<Service>
)

data class Service(
    val count: Int?,
    val popular: Boolean?,
    val code: Int?,
    val price: Int?,
    val id: Int?,
    val service: String?,
    val slug: String?
)