package up.com.carparking.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import up.com.carparking.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        fbAuth = FirebaseAuth.getInstance()

        btCadastrar_Cadastro.setOnClickListener { onClickCadastrar() }
    }

    private fun onClickCadastrar() {

        val email = tEmail.text.toString()
        val password = tPassword_Cadastro.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showError(R.string.usuario_senha_incorreto)
            return
        }

        fbAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                dismissProgress()
                if (task.isSuccessful) {
                    toast("$email Cadastrado Com Sucesso!")
                    finish()
                } else {
                    toast("Error: ${task.exception?.message}")
                }
            }
    }

    fun showError(messageResId: Int) {
        alert(messageResource = messageResId, titleResource = R.string.error).show()
    }

    fun showProgress() {
        progress_cadastro.visibility = View.VISIBLE
    }

    fun dismissProgress() {
        progress_cadastro.visibility = View.GONE
    }
}
