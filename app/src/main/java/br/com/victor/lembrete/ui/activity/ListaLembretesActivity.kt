package br.com.victor.lembrete.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.R
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.databinding.ActivityListaLembretesBinding
import br.com.victor.lembrete.extensions.vaiPara
import br.com.victor.lembrete.model.Lembrete
import br.com.victor.lembrete.model.Usuario
import br.com.victor.lembrete.preferences.dataStore
import br.com.victor.lembrete.preferences.usuarioLogado
import br.com.victor.lembrete.ui.recyclerview.adapter.LembreteAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

class ListaLembretesActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityListaLembretesBinding.inflate(layoutInflater)
    }

    private val lembreteDao by lazy {
        AppDataBase.getInstance(this).lembreteDao()
    }
    private val usuarioDao by lazy {
        AppDataBase.getInstance(this).usuarioDao()
    }


    private val adapter = LembreteAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraRecyclerView()

        configuraFab()

        lifecycleScope.launch {
            launch {
                usuario.filterNotNull().collect{
                    buscaProdutoUsuario(it.usuarioId)
                }

            }


        }
    }

    private suspend fun buscaProdutoUsuario(usuarioId: String) {
        lembreteDao.buscaLembreteUsuario(usuarioId).collect{
            adapter.atualiza(it)
        }
    }


    private fun configuraFab() {
        val fab = binding.activityListaLembretesFab
        fab.setOnClickListener {
            vaiParaFormulario()
        }
    }

    private fun vaiParaFormulario() {
        val intent = Intent(this, FormularioLembretesActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_ordenar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        lifecycleScope.launch {
            val listaOrdenada: List<Lembrete>? = when (item.itemId) {
                R.id.menu_ordenar_asc ->
                    lembreteDao.buscaAsc()


                R.id.menu_ordenar_desc ->
                    lembreteDao.buscaDesc()
                else -> null

            }
            listaOrdenada?.let {
                adapter.atualiza(it)
            }

            when (item.itemId) {
                R.id.menu_perfil ->
                    vaiPara(PerfilUsuarioActivity::class.java)

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaLembretesRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(this, DetalheLembreteActivity::class.java).apply {
                putExtra(CHAVE_LEMBRETE, it.id)
            }
            startActivity(intent)
        }
    }


}