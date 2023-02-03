package br.com.victor.lembrete.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityPerfilUsuarioBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilUsuarioActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            preencheCampo()
        }

        configuraBotaoDeslogar()


    }

    private fun configuraBotaoDeslogar() {
        binding.perfiUsuarioBotaoDeslogar.setOnClickListener {
            lifecycleScope.launch {
                deslogarUsuario()

            }
        }
    }

    private fun preencheCampo() {
        lifecycleScope.launch{
            launch {
                usuario.filterNotNull().collect{
                    binding.perfiUsuarioTextview.text = it.nome
                }
            }
        }


    }

}


