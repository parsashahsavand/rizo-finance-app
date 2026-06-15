package com.example.Parsa.presentation_Screens.aa_Navigation

import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import kotlinx.serialization.Serializable

@Serializable
object Mian

@Serializable
object Insert

@Serializable
object List

@Serializable
data class Add_data(
    val screen_name: String,
    val edit: Boolean = false,
    val id: Int = 0,
)

@Serializable
object Filter

@Serializable
data class ViewInformation(
    val id: Int,
    val type: TransactionType,
)
