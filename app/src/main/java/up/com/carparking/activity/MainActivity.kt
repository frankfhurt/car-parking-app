package up.com.carparking.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_progress.*
import org.jetbrains.anko.toast
import up.com.carparking.R
import up.com.carparking.adapter.ParkingStatusAdapter
import up.com.carparking.repository.domain.ParkingLot
import up.com.carparking.repository.domain.ParkingStatus

class MainActivity : BaseActivity() {

    val TAG = "main"

    private val presenter: ParkingStatusPresenter by lazy {
        ParkingStatusPresenter(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btGet.setOnClickListener { onClickGet() }

        // Views
        recyclerView.layoutManager = GridLayoutManager(context, 5)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        FirebaseMessaging.getInstance().subscribeToTopic("parking")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
                toast(msg)
            }
    }

    override fun onResume() {
        super.onResume()
        presenter.taskParkingStatus()
    }

    private fun onClickGet() {
        presenter.taskParkingStatus()
    }

    private fun onClickParkingLot(status: ParkingLot) {
        toast(status.status)
    }

    /**
     * View
     */
    private val view = object : StatusView {
        override fun alert(msg: String) {
            progress.visibility = View.INVISIBLE
            toast(msg)
        }

        override fun setParkingStatus(status: ParkingStatus) {
            val adapter = ParkingStatusAdapter(status) { status -> onClickParkingLot(status) }
            recyclerView.adapter = adapter

            progress.visibility = View.INVISIBLE
            toast("AH  -  $status")
            Log.d(TAG, status.toString())
        }

        override fun showProgress() {
            progress.visibility = View.VISIBLE
        }
    }
}
