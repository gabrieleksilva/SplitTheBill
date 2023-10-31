package br.edu.scl.ifsp.ads.contatospdm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
// data class nao precisa de get e set
data class Participante(
    var id: Int,
    var name: String,
    var qtde_paga: String,
    var descricao: String
): Parcelable