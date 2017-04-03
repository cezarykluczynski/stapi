package com.cezarykluczynski.stapi.model.common.query;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.criteria.path.RootImpl;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import java.lang.reflect.Field;
import java.util.Map;

@Service
@Slf4j
public class CachingStrategy {

	private static final String GUID = "guid";

	public boolean isCacheable() {
		return true;
	}

	public boolean isCacheable(QueryBuilder queryBuilder) {
		RootImpl root = (RootImpl) queryBuilder.getBaseCriteriaQuery().getSelection();
		Map<String, Path> pathMap;

		try {
			Field field = root.getClass().getSuperclass().getSuperclass().getDeclaredField("attributePathRegistry");
			field.setAccessible(true);
			pathMap = (Map<String, Path>) field.get(root);
		} catch (Exception e) {
			log.warn("Could not get \"attributePathRegistry\" from root because of {}", e);
			return false;
		}

		return pathMap.entrySet()
				.stream()
				.anyMatch(stringPathEntry -> GUID.equals(stringPathEntry.getKey()));
	}

}
