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
import br.com.victor.lembrete.model.Lembrete
import br.com.victor.lembrete.ui.recyclerview.adapter.LembreteAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaLembretesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaLembretesBinding.inflate(layoutInflater)
    }

    private val lembreteDao by lazy {
        AppDataBase.getInstance(this).lembreteDao()
    }


    private val adapter = LembreteAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraRecyclerView()

        configuraFab()

        lifecycleScope.launch {
            lembreteDao.buscaTodos().collect{lembrete ->
                adapter.atualiza(lembrete)
            }


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