package up.com.carparking.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_parking_status.*
import kotlinx.android.synthetic.main.include_progress.*
import org.jetbrains.anko.toast
import up.com.carparking.R
import up.com.carparking.activity.ParkingStatusPresenter
import up.com.carparking.activity.StatusView
import up.com.carparking.adapter.ParkingStatusAdapter
import up.com.carparking.repository.domain.ParkingLot
import up.com.carparking.repository.domain.ParkingStatus

private const val PARKING_ID = "parkingId"

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
class ParkingStatusFragment : Fragment() {

    val TAG = "main"

    private var parkingId = "parkingId"

    private val presenter: ParkingStatusPresenter by lazy {
        ParkingStatusPresenter(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = activity!!.applicationContext

        parkingId = arguments?.getSerializable("parkingId") as String

        // Register to broadcast
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, IntentFilter("updateParkingStatus"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parking_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        recyclerViewFragment.layoutManager = GridLayoutManager(context, 5)
        recyclerViewFragment.itemAnimator = DefaultItemAnimator()
        recyclerViewFragment.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.taskParkingStatus()
        ParkingStatusFragment.isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        ParkingStatusFragment.isActivityVisible = false
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ParkingStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(PARKING_ID, param1)
                }
            }

        lateinit var mContext: Context

        var isActivityVisible: Boolean = false
    }

    private fun onClickParkingLot(status: ParkingLot) {
        activity?.toast(status.status)
    }

    /**
     * View
     */
    private val view = object : StatusView {
        override fun alert(msg: String) {
            progress.visibility = View.INVISIBLE
            activity?.toast(msg)
        }

        override fun setParkingStatus(status: ParkingStatus) {
            val adapter = ParkingStatusAdapter(status) { status -> onClickParkingLot(status) }
            recyclerViewFragment.adapter = adapter

            progress.visibility = View.INVISIBLE
            activity?.toast("AH  -  $status")
            Log.d(TAG, status.toString())
        }

        override fun showProgress() {
            progress.visibility = View.VISIBLE
        }
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ParkingStatusFragment.isActivityVisible)
                presenter.taskParkingStatus()
        }
    }
}
