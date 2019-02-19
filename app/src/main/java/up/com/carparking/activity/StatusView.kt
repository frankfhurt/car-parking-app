package up.com.carparking.activity

import up.com.carparking.repository.domain.ParkingStatus

interface StatusView {
    fun showProgress()
    fun alert(msg: String)
    fun setParkingStatus(status: ParkingStatus)

}
