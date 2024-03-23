package com.keyboard.myanglish.utils

fun String.isJavaIdentifier(): Boolean {
    if (this.isEmpty()) return false
    if (!this[0].isJavaIdentifierStart()) return false
    for (i in 1..<this.length) {
        if (!this[i].isJavaIdentifierPart()) return false
    }
    return true
}
