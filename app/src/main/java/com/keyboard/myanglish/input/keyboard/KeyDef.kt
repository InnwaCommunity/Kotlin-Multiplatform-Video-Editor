package com.keyboard.myanglish.input.keyboard

import androidx.annotation.DrawableRes
import com.keyboard.myanglish.data.InputFeedbacks

open class KeyDef(
    val appearance: Appearance,
    val behaviors: Set<Behavior>,
    val popup: Array<Popup>? = null
) {
    sealed class Appearance(
        val percentWidth: Float,
        val variant: Variant,
        val border: Border,
        val margin: Boolean,
        val viewId: Int,
        val soundEffect: InputFeedbacks.SoundEffect
    ) {
        enum class Variant {
            Normal, AltForeground, Alternative, Accent
        }

        enum class Border {
            Default, On, Off, Special
        }

        class Image(
            @DrawableRes
            val src: Int,
            percentWidth: Float = 0.1f,
            variant: Variant = Variant.Normal,
            border: Border = Border.Default,
            margin: Boolean = true,
            viewId: Int = -1,
            soundEffect: InputFeedbacks.SoundEffect = InputFeedbacks.SoundEffect.Standard
        ) : Appearance(percentWidth, variant, border, margin, viewId, soundEffect)

    }

    sealed class Behavior {

    }
    sealed class Popup {

    }
}