package ru.godzilla65536.onlinesim.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val OBJECT_MAPPER = ObjectMapper().registerKotlinModule()