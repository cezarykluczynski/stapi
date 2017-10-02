package com.cezarykluczynski.stapi.model.common.cache;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.criteria.path.RootImpl;

import javax.persistence.criteria.Path;
import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public class FullEntityCachingStrategy implements CachingStrategy {

	private static final String UID = "uid";

	public boolean isCacheable(QueryBuilder queryBuilder) {
		RootImpl root = (RootImpl) queryBuilder.getBaseCriteriaQuery().getSelection();
		Map<String, Path> pathMap;

		try {
			Field field = root.getClass().getSuperclass().getSuperclass().getDeclaredField("attributePathRegistry");
			field.setAccessible(true);
			pathMap = (Map<String, Path>) field.get(root);
		} catch (Exception e) {
			log.warn("Could not get \"attributePathRegistry\" from root because of exception", e);
			return false;
		}

		if (pathMap == null) {
			return true;
		}

		return pathMap.entrySet()
				.stream()
				.anyMatch(stringPathEntry -> UID.equals(stringPathEntry.getKey()));
	}

}
