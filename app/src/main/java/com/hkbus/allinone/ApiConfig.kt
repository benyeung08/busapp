package com.hkbus.allinone
import java.net.URLEncoder
object ApiConfig {
    const val STATIC_BASE = "https://raw.githubusercontent.com/YOUR_USERNAME/hk-bus-all-in-one/main/docs/data/"
    const val PROXY_BASE = "https://YOUR_PROJECT.vercel.app/api/proxy?url="
    fun proxy(url: String): String { return PROXY_BASE + URLEncoder.encode(url, "UTF-8") }
    fun kmbEtaUrl(stopId: String, route: String, serviceType: String): String {
        return proxy("https://data.etabus.gov.hk/v1/transport/kmb/eta/${stopId}/${route}/${serviceType}")
    }
}
