package com.keyboard.myanglish.data.theme

import com.keyboard.myanglish.utils.NostalgicSerializer
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CustomThemeSerializer : JsonTransformingSerializer<Theme.Custom>(Theme.Custom.serializer()) {

    val WithMigrationStatus = NostalgicSerializer(this) {
        it.jsonObject[VERSION]?.jsonPrimitive?.content != CURRENT_VERSION
    }

    private const val VERSION = "version"

    private const val CURRENT_VERSION = "2.0"
    private const val FALLBACK_VERSION = "1.0"
}