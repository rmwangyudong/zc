package com.wyd.zc.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	public final static ObjectMapper getInstance() {
		return objectMapper;
	}

	public final static String write(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final static <T> T read(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
