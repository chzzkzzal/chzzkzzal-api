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
	// public <T> T parseContent(String rawJson, Class<T> clazz) {
	//     try {
	//         JsonNode root = objectMapper.readTree(rawJson);
	//         JsonNode contentNode = root.get("content");
	//         if (contentNode == null || contentNode.isNull()) {
	//             throw new RuntimeException("No 'content' field found in the response JSON.");
	//         }
	//         return objectMapper.treeToValue(contentNode, clazz);
	//     } catch (JsonProcessingException e) {
	//         throw new RuntimeException("Failed to parse JSON", e);
	//     }
	// }
	public <T> T parseContent(String rawJson, Class<T> clazz) {
		try {
			JsonNode root = objectMapper.readTree(rawJson);

			JsonNode contentNode = root.get("content");
			if (contentNode == null || contentNode.isNull()) {
				throw new RuntimeException("No 'content' field found in the response JSON.");
			}

			// 1) "content" 내부의 "data" 필드를 확인
			JsonNode dataNode = contentNode.get("data");

			// 2) "data"가 배열이라면, 첫 번째 아이템을 T로 매핑
			if (dataNode != null && dataNode.isArray()) {
				if (dataNode.size() == 0) {
					throw new RuntimeException("'content.data' is an empty array.");
				}
				JsonNode firstItem = dataNode.get(0);
				return objectMapper.treeToValue(firstItem, clazz);
			}

			// 3) "data"가 없거나 배열이 아니라면,
			//    그냥 "content" 자체를 T로 매핑
			return objectMapper.treeToValue(contentNode, clazz);

		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse JSON", e);
		}
	}

}
