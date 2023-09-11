package com.sh.experiment.errorstates

sealed class ErrorState {
    data class NotFound(val reason: String) : ErrorState()
    data class Exists(val reason: String) : ErrorState()
    data class BadInput(val reason: String) : ErrorState()
}
