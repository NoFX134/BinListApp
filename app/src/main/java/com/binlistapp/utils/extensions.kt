package com.binlistapp.utils

fun String.titleCaseFirstChar() = replaceFirstChar(Char::titlecase)

fun String.toURL() = if (!this.startsWith("http://") && !this.startsWith("https://"))
    "http://$this" else this

fun String.toPhone() = if (!this.startsWith("+") && !this.startsWith("8"))
    "+$this" else this