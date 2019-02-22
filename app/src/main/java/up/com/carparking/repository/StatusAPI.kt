package up.com.carparking.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import up.com.carparking.repository.domain.ParkingStatus

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
interface StatusAPI {

    @GET("/dev/parking/{id}")
    fun getStatus(@Header("x-api-key") apiKey: String,
                  @Path("id") id: String): Call<ParkingStatus>

}