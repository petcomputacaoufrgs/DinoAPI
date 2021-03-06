package br.ufrgs.inf.pet.dinoapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    public static <T> String convertToJson(T obj) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(obj);
    }

    public static <T> String convertToJson(T obj, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T convertJsonToObj(String json, Class<T> valueType) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, valueType);
    }

    public static <T> T convertJsonToObj(String json, Class<T> valueType, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(json, valueType);
    }
}
