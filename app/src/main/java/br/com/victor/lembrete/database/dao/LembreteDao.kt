package br.com.victor.lembrete.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.victor.lembrete.model.Lembrete
import kotlinx.coroutines.flow.Flow

@Dao
interface LembreteDao {


    @Query("SELECT * FROM Lembrete")
    fun buscaTodos(): Flow<List<Lembrete>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg lembrete: Lembrete)

    @Delete
    suspend fun remove(lembrete: Lembrete)


    @Query("SELECT * FROM Lembrete WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Lembrete?>

    @Query("SELECT * FROM Lembrete ORDER BY titulo ASC")
    suspend fun buscaAsc(): List<Lembrete>?

    @Query("SELECT * FROM Lembrete ORDER BY titulo DESC")
    suspend fun buscaDesc(): List<Lembrete>?

    @Query("SELECT * FROM lembrete WHERE usuarioId = :usuarioId")
    fun buscaLembreteUsuario(usuarioId: String): Flow<List<Lembrete>>

}
