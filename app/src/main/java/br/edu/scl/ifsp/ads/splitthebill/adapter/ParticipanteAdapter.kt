package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.databinding.TileContactBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Participante
import br.edu.scl.ifsp.ads.splitthebill.model.Item

class ParticipanteAdapter(context: Context,
                          private val contactList: MutableList<Participante>
                      , private val itemList: MutableList<Item>
): ArrayAdapter<Participante>(context, R.layout.tile_contact, contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val participante = contactList[position]
       // val item = itemList[position]
        var tcb: TileContactBinding? = null

        var participanteTileView = convertView
        if (participanteTileView == null){
            tcb = TileContactBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )

           participanteTileView = tcb.root
            val tileContactHolder = TileContactHolder(tcb.nameTv,tcb.produtoTv, tcb.valorTv)
            participanteTileView.tag = tileContactHolder

        }
        val holder = participanteTileView.tag as TileContactHolder
        holder.nameTv.setText(participante.name)
                for (i in 0..2){
                    //$i
                    itemList[i].produto
                    holder.produtoTv.setText(itemList[i].produto + ", ")
                    holder.valorTv.setText(itemList[i].valor.toString())
                }


        return participanteTileView
    }
    private data class TileContactHolder(val nameTv: TextView, val produtoTv: TextView, val valorTv: TextView)
}
