package io.slack.rtm

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.okhttp.FormEncodingBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.util.HashMap

public class Client(val token: String, val autoReconnect: Boolean = true, val autoMark: Boolean = false) {

    val client = OkHttpClient()
    val parser = JsonParser()
    var authenticated = false
    var socketUrl: String? = null

    fun login() {
        api("rtm.start", hashMapOf("agent" to "java-slack")) { onLogin(it) }
    }

    private fun onLogin(response: JsonObject) {
        println(response.toString())

        if (response.get("ok").getAsBoolean()) {
            authenticated = true
            val data = response.get("data").getAsJsonObject()
            socketUrl = data.get("url").getAsString()

            connect()
        }
    }

    fun connect() {
        if (socketUrl != null) {
                    
        }
    }

    private fun api(path: String, params: HashMap<String, String>?, callback: (JsonObject) -> Unit) {
        val parameters = with(FormEncodingBuilder()) {
            params?.forEach {
                add(it.getKey(), it.getValue())
            }
            add("token", token)
            build()
        }

        val request = with(Request.Builder()) {
            url("${URL}/${path}")
            header("Content-Type", "application/x-www-form-urlencoded")
            post(parameters)
            build()
        }

        val response = client.newCall(request).execute()
        val body = response.body().charStream()
        callback(parser.parse(body).getAsJsonObject())
    }
}