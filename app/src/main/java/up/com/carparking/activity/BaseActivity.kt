package up.com.carparking.activity

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected val context: Context get() = this
}
