package io.averest.observer.domain.model

data class Tag(
    val jobIdent: String,
    val type: String,
    val id: String = jobIdent + type,
)
