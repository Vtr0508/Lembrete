package br.com.victor.lembrete.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.R
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityDetalheLembreteBinding
import br.com.victor.lembrete.extensions.tentaCarregarImagem
import br.com.victor.lembrete.model.Lembrete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalheLembreteActivity : AppCompatActivity() {

    private var lembreteId: Long = 0L
    private var lembrete: Lembrete? = null

    private val binding by lazy {
        ActivityDetalheLembreteBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDataBase.getInstance(this).lembreteDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
        buscaProdutoPorId()


    }



    private fun buscaProdutoPorId() {
        lifecycleScope.launch {
            dao.buscaPorId(lembreteId).collect{lembrete ->
                lembrete?.let {
                    preencheCampo(lembrete)
                    title = it.titulo

                } ?: finish()
            }


        }

    }


    private fun tentaCarregarProduto() {
        lembreteId = intent.getLongExtra(CHAVE_LEMBRETE, 0L)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_lembrete, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_detalhe_editar -> {
                Intent(this, FormularioLembretesActivity::class.java).apply {
                    putExtra(CHAVE_LEMBRETE, lembreteId)
                    startActivity(this)

                }

            }

            R.id.menu_detalhe_remover -> {
                lembrete?.let {

                    lifecycleScope.launch() {
                        dao.remove(it)
                    }


                }
                finish()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun preencheCampo(lembreteCarregado: Lembrete) {

        with(binding) {
            detalheLembreteImageview.tentaCarregarImagem(lembreteCarregado.imagem)
            detalheLembreteTextviewTitulo.text = lembreteCarregado.titulo
            detalheLembreteTextviewDesc.text = lembreteCarregado.descricao
        }

    }
}