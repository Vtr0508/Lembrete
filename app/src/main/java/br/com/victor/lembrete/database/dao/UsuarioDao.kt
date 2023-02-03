package br.com.victor.lembrete.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.victor.lembrete.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE usuarioId = :usuarioId AND senha = :senha")
    suspend fun autentica(usuarioId: String, senha: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE usuarioId = :usuarioId")
     fun buscaUsuarioId(usuarioId: String):Flow<Usuario>




}