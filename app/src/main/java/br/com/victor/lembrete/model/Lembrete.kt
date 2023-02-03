package br.com.victor.lembrete.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Lembrete(
     @PrimaryKey(autoGenerate = true) var id: Long = 0L,
     val titulo: String,
     val descricao: String,
     val imagem:String? = null,
     val usuarioId: String? = null
): Parcelable
