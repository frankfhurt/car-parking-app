package up.com.carparking.activity

import up.com.carparking.repository.domain.ParkingStatus

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
interface StatusView {
    fun showProgress()
    fun alert(msg: String)
    fun setParkingStatus(status: ParkingStatus)

}
