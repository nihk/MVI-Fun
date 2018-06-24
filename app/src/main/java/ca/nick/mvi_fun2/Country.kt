package ca.nick.mvi_fun2

data class Country(
    val name: String,
    val code: String,
    val flag: String = code.toFlagEmoji()
)