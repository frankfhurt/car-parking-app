package up.com.carparking.push

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import up.com.carparking.fragment.ParkingStatusFragment

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "firebase"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            ParkingStatusFragment.mContext.let {
                val updateStatusIntent = Intent("updateParkingStatus")
                LocalBroadcastManager.getInstance(it).sendBroadcast(updateStatusIntent)
            }
        }

        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: " + token!!)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // Aqui deve ficar a sua lógica para enviar o token para o servidor ...
    }
}
