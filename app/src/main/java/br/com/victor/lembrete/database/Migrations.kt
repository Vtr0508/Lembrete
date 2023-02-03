package br.com.victor.lembrete.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Usuario` (`usuarioId` TEXT NOT NULL, `nome` TEXT NOT NULL, `senha` TEXT NOT NULL, PRIMARY KEY(`usuarioId`))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Lembrete ADD COLUMN 'usuarioId' TEXT")
    }


}