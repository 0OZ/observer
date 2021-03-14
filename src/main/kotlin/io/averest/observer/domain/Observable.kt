package io.averest.observer.domain

data class Observable(
    val jobIdent: String,
    val type: String,
    val id: String = jobIdent + type,
)
