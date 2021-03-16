package ru.godzilla65536.onlinesim.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue
import java.net.URI
import java.net.URL

@ConstructorBinding
@ConfigurationProperties(prefix = "onlinesim")
data class OnlineSimClientProps(
    val apiKey: String,
    @DefaultValue("https://onlinesim.ru/api")
    val baseUrl: URL,

    @DefaultValue("/getBalance.php")
    val getBalanceUrn: URI,
    @DefaultValue("/getNumbersStats.php")
    val getNumbersStatsUrn: URI,
    @DefaultValue("/getNum.php")
    val getNumUrn: URI
)