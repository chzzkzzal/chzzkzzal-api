package com.chzzkzzal.core.chzzk.adapter.out.core;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ChzzkJsonMapper {

	private final ObjectMapper objectMapper;

	public <T> T parseContent(String rawJson, Class<T> clazz) {
		try {
			JsonNode root = objectMapper.readTree(rawJson);

			JsonNode contentNode = root.get("content");
			if (contentNode == null || contentNode.isNull()) {
				throw new RuntimeException("No 'content' field found in the response JSON.");
			}

			JsonNode dataNode = contentNode.get("data");

			if (dataNode != null && dataNode.isArray()) {
				if (dataNode.size() == 0) {
					throw new RuntimeException("'content.data' is an empty array.");
				}
				JsonNode firstItem = dataNode.get(0);
				return objectMapper.treeToValue(firstItem, clazz);
			}

			return objectMapper.treeToValue(contentNode, clazz);

		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse JSON", e);
		}
	}

}
