package com.auf.cea.realmdiscussion.views.model

data class Pet (
    val id: String,
    val name: String,
    val age: Int,
    val petType: String = "",
    val ownerName: String = ""
)