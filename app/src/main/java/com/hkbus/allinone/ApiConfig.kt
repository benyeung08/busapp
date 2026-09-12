package com.hkbus.allinone
import java.net.URLEncoder
object ApiConfig {
    const val STATIC_BASE = "https://raw.githubusercontent.com/benyeung08/hketaapp/main/docs/data/"
    const val PROXY_BASE = "https://hketaapp.vercel.app/api/proxy?url="
    fun proxy(url: String): String = PROXY_BASE + URLEncoder.encode(url, "UTF-8")
    fun kmbEtaUrl(stopId: String, route: String, serviceType: String): String {
        return proxy("https://data.etabus.gov.hk/v1/transport/kmb/eta/${stopId}/${route}/${serviceType}")
    }
}
