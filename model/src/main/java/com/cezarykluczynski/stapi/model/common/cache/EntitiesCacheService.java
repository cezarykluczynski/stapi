package com.cezarykluczynski.stapi.model.common.cache;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class EntitiesCacheService {

	private static final String UID = "uid";

	@SneakyThrows
	@SuppressWarnings("EmptyBlock")
	public boolean isCacheable(Object criteria, Pageable pageable) {
		try {
			Field field = criteria.getClass().getDeclaredField(UID);
			field.setAccessible(true);
			String uid = (String) field.get(criteria);
			return uid != null;
		} catch (NoSuchFieldException pass) {
		}
		return false;
	}

	@SneakyThrows
	public String resolveKey(Object criteria, Pageable pageable) {
		Field field = criteria.getClass().getDeclaredField(UID);
		field.setAccessible(true);
		String uid = (String) field.get(criteria);
		if (uid != null) {
			return "UID:" + uid;
		}
		throw new StapiRuntimeException(String.format("Cannot generate key for criteria %s and pageable %s", criteria, pageable));
	}

}
