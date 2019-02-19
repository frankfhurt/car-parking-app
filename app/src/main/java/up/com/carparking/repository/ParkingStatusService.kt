package up.com.carparking.repository

import up.com.carparking.repository.domain.ParkingStatus

/**
 * Created by Franklyn 19/02/2019
 */
object ParkingStatusService {
    private const val baseUrl = "URL GOES HERE"
    private const val apiKey = "KEY GOES HERE"
    private var service: StatusAPI

    init {
        service = RetrofitUtil.getRetrofit(baseUrl).create(StatusAPI::class.java)
    }

    fun getStatus(id: String): ParkingStatus {
        val call = service.getStatus(apiKey, id)
        val status = call.execute().body()
        return status ?: ParkingStatus()
    }
}