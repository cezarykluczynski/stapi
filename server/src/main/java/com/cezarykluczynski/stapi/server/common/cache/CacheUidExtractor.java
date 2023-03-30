package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheUidExtractor {

	private static final String UID = "uid";

	Optional<String> extractUid(Object criteria) {
		try {
			return Optional.ofNullable(ReflectionUtil.getFieldValue(criteria.getClass(), criteria, UID, String.class));
		} catch (NoSuchFieldException e) {
			return Optional.empty();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
