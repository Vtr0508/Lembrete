package br.com.victor.lembrete.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.victor.lembrete.model.Usuario
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDataBase.getInstance(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()

        criaUsuario()


    }

    private fun configuraBotaoCadastrar() {
        binding.cadastroUsuarioBotaoCadstrar.setOnClickListener {
            val usuarioNovo = criaUsuario()

            cadastraNovoUsuario(usuarioNovo)



        }
    }

    private fun cadastraNovoUsuario(usuario: Usuario) {
        lifecycleScope.launch {
            try {
                usuarioDao.salva(usuario)
                finish()
            } catch (e: Exception){
                Toast.makeText(
                    this@FormularioCadastroUsuarioActivity,
                    "Usuario já cadastrado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val campoUsuario = binding.cadastroUsuarioTextinputedittextUsuario.text.toString()
        val campoNome = binding.cadastroUsuarioTextinputedittextNome.text.toString()
        val campoSenha = binding.cadastroUsuarioTextinputedittextSenha.text.toString()

        return Usuario(campoUsuario,campoNome,campoSenha)

    }
}