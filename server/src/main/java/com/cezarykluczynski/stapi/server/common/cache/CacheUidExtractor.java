package com.cezarykluczynski.stapi.server.common.cache;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class CacheUidExtractor {

	private static final String UID = "uid";

	Optional<String> extractUid(Object criteria) {
		try {
			Field field = criteria.getClass().getDeclaredField(UID);
			field.setAccessible(true);
			String uid = (String) field.get(criteria);
			return Optional.ofNullable(uid);
		} catch (NoSuchFieldException e) {
			return Optional.empty();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
