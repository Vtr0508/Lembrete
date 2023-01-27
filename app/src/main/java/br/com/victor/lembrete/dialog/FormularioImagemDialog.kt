package br.com.victor.lembrete.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import br.com.victor.lembrete.databinding.FormularioDialogBinding
import br.com.victor.lembrete.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

    fun mostraDialog(urlPadrao: String? = null, quandoCarregarImagem: (String) -> Unit) {


        FormularioDialogBinding.inflate((LayoutInflater.from(context))).apply {

            urlPadrao?.let {

                formularioDialogImageview.tentaCarregarImagem(it)
                formularioDialogTextinputedittext.setText(it)


            }

            formularioDialogBotaoCarregar.setOnClickListener {
                val url = formularioDialogTextinputedittext.text.toString()
                formularioDialogImageview.tentaCarregarImagem(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar"){_,_ ->
                    val url = formularioDialogTextinputedittext.text.toString()
                    quandoCarregarImagem(url)


                }
                .setNegativeButton("Cancelar"){_,_ ->

                }
                .show()

        }

    }
}