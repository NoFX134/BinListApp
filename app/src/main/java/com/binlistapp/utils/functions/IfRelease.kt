package com.binlistapp.utils.functions

import com.example.binlistapp.BuildConfig

fun ifRelease(action: () -> Unit) {
    if (!BuildConfig.DEBUG) action()
}