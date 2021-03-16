package io.averest.observer.domain

data class Identifier(
    val jobIdent: String,
    val type: String,
    val id: String = jobIdent + type,
)
