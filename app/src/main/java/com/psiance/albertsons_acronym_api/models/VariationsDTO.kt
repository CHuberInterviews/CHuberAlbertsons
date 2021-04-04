package com.psiance.albertsons_acronym_api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VariationsDTO (
        val lf: String?,
        val freq: Int?,
        val since: Int?
        ) : Parcelable