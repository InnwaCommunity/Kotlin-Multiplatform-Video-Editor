package com.keyboard.myanglish.utils

import android.graphics.Rect
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RectSerializer : KSerializer<Rect> {
    override val descriptor: SerialDescriptor
        get() = TODO("Not yet implemented")

    override fun deserialize(decoder: Decoder): Rect {
        TODO("Not yet implemented")
    }

    override fun serialize(encoder: Encoder, value: Rect) {
        TODO("Not yet implemented")
    }
}