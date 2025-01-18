package org.runcfg

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(val version: String, val target: String, val enabled: String)

fun main() {
    val client = LoadConfigAsType<Configuration>()
    println(client.target)
}