package com.chzzkzzal.core.client.callback;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ChzzkJsonHelper {

    private final ObjectMapper objectMapper;

    /**
     * rawJson 안에서 "content" 노드를 찾아서, DTO로 매핑
     */
    public <T> T parseContent(String rawJson, Class<T> clazz) {
        try {
            JsonNode root = objectMapper.readTree(rawJson);
            JsonNode contentNode = root.get("content");
            if (contentNode == null || contentNode.isNull()) {
                throw new RuntimeException("No 'content' field found in the response JSON.");
            }
            return objectMapper.treeToValue(contentNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
