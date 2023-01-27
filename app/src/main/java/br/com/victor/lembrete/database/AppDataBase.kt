package br.com.victor.lembrete.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.victor.lembrete.database.dao.LembreteDao
import br.com.victor.lembrete.model.Lembrete

@Database(entities = [Lembrete::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun lembreteDao(): LembreteDao

    companion object {
       @Volatile private var db: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "lembrete.db"
            )
                .build()
                .also {
                    db = it
                }
        }

    }


}