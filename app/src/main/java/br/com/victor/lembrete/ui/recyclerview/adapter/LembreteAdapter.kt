package br.com.victor.lembrete.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.victor.lembrete.R
import br.com.victor.lembrete.databinding.LembreteItemBinding
import br.com.victor.lembrete.extensions.tentaCarregarImagem
import br.com.victor.lembrete.model.Lembrete
import br.com.victor.lembrete.ui.activity.ListaLembretesActivity

class LembreteAdapter(
    lembretes: List<Lembrete> = emptyList(),
    var quandoClicaNoItem: (lembrete:Lembrete) -> Unit = {},
    private val context: Context
) : RecyclerView.Adapter<LembreteAdapter.ViewHolder>() {

    private val lembretes = lembretes.toMutableList()

   inner class ViewHolder(private val binding: LembreteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var lembrete: Lembrete

        init {
            itemView.setOnClickListener {
                quandoClicaNoItem(lembrete)

            }
        }




        fun vincula(lembrete: Lembrete) {
            this.lembrete = lembrete
            val titulo = binding.lembreteItemTextTitulo
            titulo.text = lembrete.titulo

            val descricao = binding.lembreteItemTextDescricao
            descricao.text = lembrete.descricao

            val imagem = binding.lembreteItemTextImageview
            imagem.tentaCarregarImagem(lembrete.imagem)


        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LembreteItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lembrete = lembretes[position]
        holder.vincula(lembrete)
    }

    override fun getItemCount(): Int = lembretes.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(lembretes: List<Lembrete>){
        this.lembretes.clear()
        this.lembretes.addAll(lembretes)
        notifyDataSetChanged()

    }





}
