package org.catinthedark.vvtf.shared

fun Float.toMillis(): Long = (this * 1000f).toLong()
fun Long.toSeconds(): Float = (this.toDouble() / 1000.0).toFloat()