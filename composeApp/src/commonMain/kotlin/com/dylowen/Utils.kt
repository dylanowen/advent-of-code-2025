package com.dylowen


fun Long.pow(exponent: Int): Long {
    var result = 1L
    repeat(exponent) {
        result *= this
    }
    return result
}