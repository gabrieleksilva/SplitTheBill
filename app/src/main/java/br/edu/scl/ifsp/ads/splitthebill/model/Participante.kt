package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Participante (
    var id: Int,
    var nome: String,
    var qtde_pagou: String,
    var descricao: String,// descricao do que comprou
): Parcelable