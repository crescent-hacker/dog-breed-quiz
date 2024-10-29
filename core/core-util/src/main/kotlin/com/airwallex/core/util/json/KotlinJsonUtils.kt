package com.airwallex.core.util.json

import com.airwallex.core.util.TextHolder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.math.BigDecimal

val kotlinJson = Json { encodeDefaults = true }

object TextHolderSerializer : KSerializer<TextHolder> {
    private const val DELIMITER = "|"

    override val descriptor = PrimitiveSerialDescriptor("TextHolder", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: TextHolder) {
        return encoder.encodeString("${value.textRes}$DELIMITER${value.text}")
    }

    override fun deserialize(decoder: Decoder): TextHolder {
        val decodedValue = decoder.decodeString()
        val valuePair = decodedValue.split(DELIMITER)
        assert(valuePair.size == 2) {
            "Encoded TextHolder is corrupted, value = $decodedValue"
        }
        return TextHolder(
                textRes = valuePair[0].toInt(),
                text = valuePair[1]
        )
    }
}

object BigDecimalSerializer: KSerializer<BigDecimal> {
    override fun deserialize(decoder: Decoder): BigDecimal {
        return decoder.decodeString().toBigDecimal()
    }

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
}
