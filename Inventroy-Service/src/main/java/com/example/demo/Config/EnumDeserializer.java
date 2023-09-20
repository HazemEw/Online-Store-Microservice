package com.example.demo.Config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EnumDeserializer<T extends Enum<T>> extends JsonDeserializer<T> {

    private final Class<T> enumType;

    public EnumDeserializer(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value != null) {
            try {
                return Enum.valueOf(enumType, value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid enum value: " + value);
            }
        }
        return null;
    }
}