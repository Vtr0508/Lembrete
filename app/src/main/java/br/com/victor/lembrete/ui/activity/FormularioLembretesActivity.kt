package br.com.victor.lembrete.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityFormularioLembretesBinding
import br.com.victor.lembrete.dialog.FormularioImagemDialog
import br.com.victor.lembrete.extensions.tentaCarregarImagem
import br.com.victor.lembrete.model.Lembrete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioLembretesActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioLembretesBinding.inflate(layoutInflater)
    }

    private val lembreteDao by lazy {
        AppDataBase.getInstance(this).lembreteDao()

    }

    private var url: String? = null
    private var lembreteId: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Lembretes"
        configuraBotaoSalvar()


        val imagem = binding.activityFormularioLembretesImageview
        imagem.setOnClickListener {

            FormularioImagemDialog(this).mostraDialog(url) { imagem ->
                url = imagem
                binding.activityFormularioLembretesImageview.tentaCarregarImagem(url)


            }


        }
        tentaCarregarLembrete()
        buscaLembretePorId()


    }



    private fun buscaLembretePorId() {

        lifecycleScope.launch {
            lembreteDao.buscaPorId(lembreteId).collect { lembrete ->

                lembrete?.let {
                    title = "Editar Lembrete ${it.titulo}"
                    preencheCampos(it)
                }


            }


        }
    }

    private fun tentaCarregarLembrete() {
        lembreteId = intent.getLongExtra(CHAVE_LEMBRETE, 0L)
    }

    private fun preencheCampos(lembrete: Lembrete) {
        with(binding) {
            activityFormularioLembretesTextinputedittextTitulo.setText(lembrete.titulo)
            activityFormularioLembretesImageview.tentaCarregarImagem(lembrete.imagem)
            activityFormularioLembretesTextinputedittextDesc.setText(lembrete.descricao)
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioLembretesBotao
        botaoSalvar.setOnClickListener {
            val lembreteCriado = criaLembrete()

            lifecycleScope.launch {
                lembreteDao.salva(lembreteCriado)
                finish()
            }


        }
    }

    private fun criaLembrete(): Lembrete {
        val campoTitulo = binding.activityFormularioLembretesTextinputedittextTitulo
        val titulo = campoTitulo.text.toString()

        val campoDesc = binding.activityFormularioLembretesTextinputedittextDesc
        val desc = campoDesc.text.toString()

        return Lembrete(id = lembreteId, titulo = titulo, descricao = desc, imagem = url)
    }

}
