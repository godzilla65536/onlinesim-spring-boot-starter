package ru.godzilla65536.onlinesim.dto.user

data class GetBalanceResponse(
    val response: String,
    val balance: String,
    val zbalance: Int
)
