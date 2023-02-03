package br.com.victor.lembrete.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    val usuarioId: String,
    val nome: String,
    val senha: String
) {
}