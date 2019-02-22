package up.com.carparking.activity

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import up.com.carparking.repository.ParkingStatusService

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
class ParkingStatusPresenter(private val view: StatusView) {

    @SuppressLint("CheckResult")
    fun taskParkingStatus() {
        view.showProgress()

        Observable.fromCallable { ParkingStatusService.getStatus("E1_ParkingUP") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { status ->
                    view.setParkingStatus(status)
                },
                {
                    view.alert("Erro na requisição")
                }
            )
    }
}