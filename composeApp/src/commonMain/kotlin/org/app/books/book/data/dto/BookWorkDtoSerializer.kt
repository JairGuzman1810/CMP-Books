package org.app.books.book.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * [BookWorkDtoSerializer] is a custom serializer for [BookWorkDto].
 *
 * This serializer handles the case where the 'description' field can be either a
 * simple string or a JSON object with a 'value' field.
 *
 * It deserializes the 'description' field based on its type, handling both
 * simple strings and JSON objects.
 *
 * It serializes the 'description' field as a string.
 */
object BookWorkDtoSerializer : KSerializer<BookWorkDto> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookWorkDto::class.simpleName!!
    ) {
        element<String?>("description") // Define the 'description' element.
    }

    /**
     * Deserialize the [BookWorkDto] from the given decoder.
     *
     * @param decoder The decoder to use for deserialization.
     * @return The deserialized [BookWorkDto].
     * @throws SerializationException If the decoder is not a JsonDecoder or if there is an unexpected index.
     */
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String? = null // Initialize the description to null.

        // Loop through the elements of the structure.
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    // Check if the decoder is a JsonDecoder.
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This decoder only works with JSON."
                    )
                    // Decode the JSON element.
                    val element = jsonDecoder.decodeJsonElement()
                    // Check if the element is a JsonObject.
                    description = if (element is JsonObject) {
                        // Decode the DescriptionDto from JSON.
                        decoder.json.decodeFromJsonElement(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value // Get the value from the DescriptionDto.
                    } else if (element is JsonPrimitive && element.isString) {
                        // If it's a JsonPrimitive and a string, get the content.
                        element.content
                    } else {
                        // If it's not a JsonObject or a JsonPrimitive, set description to null.
                        null
                    }
                }

                CompositeDecoder.DECODE_DONE -> break // End of the structure.
                else -> throw SerializationException("Unexpected index $index") // Throw an exception for unexpected index.
            }
        }

        // Return the BookWorkDto with the deserialized description.
        return@decodeStructure BookWorkDto(description)
    }

    /**
     * Serialize the [BookWorkDto] to the given encoder.
     *
     * @param encoder The encoder to use for serialization.
     * @param value The [BookWorkDto] to serialize.
     */
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(
        descriptor
    ) {
        // Serialize the description if it's not null.
        value.description?.let {
            encodeStringElement(descriptor, 0, it) // Encode the description as a string.
        }
    }
}