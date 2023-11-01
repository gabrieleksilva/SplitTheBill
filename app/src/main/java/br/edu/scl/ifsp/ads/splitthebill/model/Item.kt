package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Item (
    var produto: String,
    var valor: Double
): Parcelable