package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.ListaParticipantesBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Participante

class ParticipantAdapter(context: Context,
    private val participantList: MutableList<Participante>
): ArrayAdapter<Participante>(context, R.layout.lista_participantes, participantList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val participant = participantList[position]
        var tcb: ListaParticipantesBinding? = null

        var participantListaView = convertView
        if (participantListaView == null){ // se a lista estiver vazia
            tcb = ListaParticipantesBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )

            participantListaView = tcb.root
            val participantListaHolder = ListaParticipantesHolder(tcb.namePt, tcb.valPt)
            participantListaView.tag = participantListaHolder

        }
        val holder = participantListaView.tag as ListaParticipantesBinding
        holder.namePt.setText(participant.nome)
        holder.valPt.setText(participant.qtde_pagou)

        return participantListaView
    }

private data class ListaParticipantesHolder(val nome: TextView, val valorPt: TextView)
}