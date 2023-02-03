package br.com.victor.lembrete.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityLoginBinding
import br.com.victor.lembrete.extensions.vaiPara
import br.com.victor.lembrete.preferences.dataStore
import br.com.victor.lembrete.preferences.usuarioLogado
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDataBase.getInstance(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraBotaoCadastrar()
        binding.loginBotaoEntrar.setOnClickListener {
            val usuario = binding.loginTextinputedittextNome.text.toString()
            val senha = binding.loginTextinputedittextSenha.text.toString()

            autenticaUsuario(usuario, senha)


        }
    }

    private fun autenticaUsuario(usuario: String, senha: String) {
        lifecycleScope.launch {
            usuarioDao.autentica(usuario, senha)?.let {

                dataStore.edit { preferences ->
                    preferences[usuarioLogado] = it.usuarioId
                }
                vaiPara(ListaLembretesActivity::class.java)
                finish()

            } ?: Toast.makeText(
                this@LoginActivity,
                "Usuario inválido",
                Toast.LENGTH_SHORT
            ).show()


        }
    }

    private fun configuraBotaoCadastrar() {
        binding.loginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }
}