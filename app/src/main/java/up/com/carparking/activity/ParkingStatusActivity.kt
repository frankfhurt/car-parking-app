package up.com.carparking.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.app_bar_main_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.nav_header_main_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import up.com.carparking.R
import up.com.carparking.activity.login.LoginActivity
import up.com.carparking.extensions.addFragment
import up.com.carparking.fragment.ParkingStatusFragment

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
class ParkingStatusActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "main activity"

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        email = intent.extras.getString("id")

        val toggle = object : ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                txEmail.text = email
            }
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_drawe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity<LoginActivity>()
                finish()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_parking_status -> {
                val parkingStatusFragment = ParkingStatusFragment.newInstance("E1_ParkingUP")
                addFragment(R.id.layout_fragment, parkingStatusFragment)
            }
            R.id.nav_about -> {
                toast("Author: Franklyn Vieira")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
