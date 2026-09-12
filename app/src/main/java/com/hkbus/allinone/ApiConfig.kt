package com.hkbus.allinone
import java.net.URLEncoder

object ApiConfig {
    const val STATIC_BASE = "https://raw.githubusercontent.com/benyeung08/hketaapp/main/docs/data/"
    const val PROXY_BASE = "https://hketaapp.vercel.app/api/proxy?url="
    
    private fun proxy(url: String) = PROXY_BASE + URLEncoder.encode(url, "UTF-8")

    // 九巴 - 修正 serviceType 要補零
    fun kmbEta(stopId: String, route: String, serviceType: String): String {
        val st = serviceType.padStart(2, '0')
        return proxy("https://data.etabus.gov.hk/v1/transport/kmb/eta/$stopId/$route/$st")
    }
    // 城巴新 API v2
    fun ctbEta(company: String, stopId: String, route: String): String {
        return proxy("https://rt.data.gov.hk/v2/transport/citybus/eta/$company/$stopId/$route")
    }
    // 嶼巴要 POST，proxy 改用 GET ?action=estimatedArrivals&stopId=...
    fun nlbEta(stopId: String, routeId: String): String {
        return proxy("https://rt.data.gov.hk/v1/transport/nlb/stop.php?action=estimatedArrivals&stopId=$stopId&routeId=$routeId&language=zh")
    }
    // 綠小正確域名
    fun gmbEta(routeId: String, stopId: String): String {
        return proxy("https://data.etagmb.gov.hk/eta/route-stop/$routeId/$stopId")
    }
    // 港鐵要加 timestamp
    fun mtrEta(line: String, sta: String): String {
        return proxy("https://www.mtr.com.hk/nextTrainData?line=$line&sta=$sta&lang=TC&_=${System.currentTimeMillis()}")
    }
}
