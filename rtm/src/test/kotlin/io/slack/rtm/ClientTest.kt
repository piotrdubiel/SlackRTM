package io.slack.rtm

import org.junit.Assert.*
import org.junit.Test as test
import org.junit.Before as before

public class ClientTest {
    val client = Client(token = System.getenv().get("SLACK_API_TOKEN")!!)

    test fun shouldLogin() {
        client.login()
        assert(false)
    }
}