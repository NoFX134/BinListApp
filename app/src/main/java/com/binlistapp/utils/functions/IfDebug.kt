package com.binlistapp.utils.functions

import com.example.binlistapp.BuildConfig

fun ifDebug(action: ()-> Unit) {
    if (BuildConfig.DEBUG) action()
}