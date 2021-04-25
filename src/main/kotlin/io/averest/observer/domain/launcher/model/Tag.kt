package io.averest.observer.domain.launcher.model

data class Tag(
    val jobIdent: String,
    val type: String,
    val id: String = jobIdent + type,
)
