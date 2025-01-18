package org.runcfg

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

@Serializable
data class RunCfg(val projectId: String, val clientToken: String)

@Throws(Exception::class)
inline fun <reified T> LoadConfigAsType(): T {
    var lineList = ""
    val client = OkHttpClient()

    if (File("default.runcfg").exists()) {
        File("default.runcfg").useLines { lines ->
            lines.forEach { lineList += it }
        }
    } else {
        throw Exception("default.runcfg not found")
    }

    val conf = Json.decodeFromString<RunCfg>(lineList)
    val target = "https://runcfg.com/app/project/${conf.projectId}/view"
    val request: Request = Request.Builder()
        .url(target)
        .addHeader("Authorization", "${conf.clientToken}")
        .addHeader("User-Agent", "runcfg-kt/1.0.0")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }
        val data = response.body?.string()?.replace("\\","")

        val trm = data?.substring(1, data.length.minus(1))
        val loaded = Json.decodeFromString<T>(trm!!)
        return loaded
    }
}