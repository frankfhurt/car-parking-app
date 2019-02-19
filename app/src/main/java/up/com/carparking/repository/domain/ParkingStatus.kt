package up.com.carparking.repository.domain

/**
 * Created by Franklyn 19/02/2019
 */
class ParkingStatus {

    var lat = ""
    var long = ""
    var parkingId = ""
    var parkingLots: List<ParkingLot>? = null

    override fun toString(): String {
        return "lat: $lat, long: $long, parkingId: $parkingId"
    }
}