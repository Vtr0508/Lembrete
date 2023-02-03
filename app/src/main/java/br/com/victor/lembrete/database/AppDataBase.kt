package br.com.victor.lembrete.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.victor.lembrete.database.dao.LembreteDao
import br.com.victor.lembrete.database.dao.UsuarioDao
import br.com.victor.lembrete.model.Lembrete
import br.com.victor.lembrete.model.Usuario

@Database(
    entities = [Lembrete::class, Usuario::class],
    version = 3,
    exportSchema = false
)
 abstract class AppDataBase : RoomDatabase() {

    abstract fun lembreteDao(): LembreteDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
       @Volatile private var db: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "lembrete.db"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                .also {
                    db = it
                }
        }

    }


}