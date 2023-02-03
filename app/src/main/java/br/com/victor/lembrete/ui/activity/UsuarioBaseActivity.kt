package br.com.victor.lembrete.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.victor.lembrete.database.AppDataBase
import br.com.victor.lembrete.extensions.vaiPara
import br.com.victor.lembrete.model.Usuario
import br.com.victor.lembrete.preferences.dataStore
import br.com.victor.lembrete.preferences.usuarioLogado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity: AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDataBase.getInstance(this).usuarioDao()
    }

    private val _usuario : MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario = _usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch{
            verificaUsuarioLogado()

        }



    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect{preferences ->
            preferences[usuarioLogado]?.let {usuarioId ->
                buscaUsuario(usuarioId)

            }?: vaiParaLogin()

        }

    }

    protected suspend fun buscaUsuario(usuarioId: String):Usuario? {
        return usuarioDao
            .buscaUsuarioId(usuarioId)
            .firstOrNull()
            .also {
                _usuario.value = it
            }


    }

    protected fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java){
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()

    }

    protected suspend fun deslogarUsuario() {
        dataStore.edit {preferences ->
            preferences.remove(usuarioLogado)

        }

    }


}