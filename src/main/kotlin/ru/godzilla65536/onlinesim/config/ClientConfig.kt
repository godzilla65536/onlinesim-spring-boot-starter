package ru.godzilla65536.onlinesim.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.godzilla65536.onlinesim.client.OnlineSimSmsApiClient
import ru.godzilla65536.onlinesim.client.OnlineSimUserApiClient
import ru.godzilla65536.onlinesim.mapper.sms.GetNumMapper
import ru.godzilla65536.onlinesim.mapper.sms.GetNumbersStatsMapper
import java.util.function.Consumer


@Configuration
@EnableConfigurationProperties(OnlineSimClientProps::class)
@ComponentScan(basePackageClasses = [GetNumbersStatsMapper::class, GetNumMapper::class])
class ClientConfig(
    private val props: OnlineSimClientProps,
    private val getNumbersStatsMapper: GetNumbersStatsMapper,
    private val getNumMapper: GetNumMapper,
) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    val webClient: WebClient by lazy {
        WebClient
            .builder()
            .exchangeStrategies(
                ExchangeStrategies
                    .builder()
                    .codecs { it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) }
                    .build()
            )
            .baseUrl(props.baseUrl.toString())
            .filter(logRequest())
            .build()
    }

    @Bean
    fun onlineSimUserApiClient() = OnlineSimUserApiClient(webClient, props)

    @Bean
    fun onlineSimSmsApiClient() = OnlineSimSmsApiClient(webClient, props, getNumbersStatsMapper, getNumMapper)

    private fun logRequest() = ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
        logger.info("Request: {} {}", clientRequest.method(), clientRequest.url())
        clientRequest.headers()
            .forEach { name: String?, values: List<String?> ->
                values.forEach(
                    Consumer { value: String? ->
                        logger.info("{}={}",
                            name,
                            value)
                    })
            }
        Mono.just(clientRequest)
    }


}