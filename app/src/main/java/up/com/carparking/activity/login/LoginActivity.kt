package up.com.carparking.activity.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import up.com.carparking.R
import up.com.carparking.activity.SignUpActivity
import up.com.carparking.activity.ParkingStatusActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fbAuth = FirebaseAuth.getInstance()

        btLogin.setOnClickListener { onClickLogin() }
        btCadastrar.setOnClickListener { onClickCadastrar() }
    }

    override fun onStart() {
        super.onStart()
        fbAuth.currentUser?.let {
            startParkingStatusActivity()
        }
    }

    @SuppressLint("CheckResult")
    private fun onClickLogin() {

        val login = tLogin.text.toString()
        val senha = tPassword.text.toString()

        if (login.isEmpty() || senha.isEmpty()) {
            showError(R.string.usuario_senha_incorreto)
            return
        }

        showProgress()

        fbAuth.signInWithEmailAndPassword(login, senha)
            .addOnCompleteListener(this) { task ->
                dismissProgress()
                if (task.isSuccessful) {
                    startParkingStatusActivity()
                } else {
                    toast("Error: ${task.exception?.message}")
                }
            }
    }

    private fun onClickCadastrar() {
        startActivity<SignUpActivity>()
    }

    private fun startParkingStatusActivity() {
        var intent = Intent(this, ParkingStatusActivity::class.java)
        intent.putExtra("id", fbAuth.currentUser?.email)
        startActivity(intent)

        finish()
    }

    fun showError(messageResId: Int) {
        alert(messageResource = messageResId, titleResource = R.string.error).show()
    }

    fun showProgress() {
        progress_login.visibility = View.VISIBLE
    }

    fun dismissProgress() {
        progress_login.visibility = View.GONE
    }
}
