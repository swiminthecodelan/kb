package com.ltl.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

  private static ObjectMapper objectMapper = new ObjectMapper();
  private static TypeReference<Map<String, Object>> typeMapObjectValueReference = new TypeReference<Map<String, Object>>() {};

  public static String objectToJson(Object object) {
    if (object == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("failed to convert object to json", e);
    }
  }

  public static <T> T jsonToObject(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException("failed to convert json to object", e);
    }
  }

  public static Map<String, Object> jsonToObjectValueMap(String json) {
    try {
      return objectMapper.readValue(json, typeMapObjectValueReference);
    } catch (IOException e) {
      throw new RuntimeException("failed to convert json to object value map");
    }
  }
}
