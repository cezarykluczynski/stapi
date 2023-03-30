package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Service
public class EagerCacheReflectionHelper {

	@SuppressWarnings("ConstantName")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EagerCacheReflectionHelper.class);
	private static final String UID = "uid";

	public Object createCriteria(Class criteriaClass, Object entity) {
		try {
			final Object criteria = criteriaClass.getConstructors()[0].newInstance();
			// Most classes have UIDs via PageAwareEntity, but `entity` check is performed first, because `entity` is dynamic.
			final Field entityUid = ReflectionUtil.getAccessibleField(UID, entity.getClass(), PageAwareEntity.class);
			final Field criteriaUid = ReflectionUtil.getAccessibleField(UID, criteria.getClass());
			criteriaUid.set(criteria, ReflectionUtil.getFieldValue(entityUid, entity, String.class));
			return criteria;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Class getCriteriaClass(CriteriaMatcher crudRepositoryImplementingCriteriaMatcher) {
		Type[] types = crudRepositoryImplementingCriteriaMatcher.getClass().getGenericInterfaces();
		for (Type type : types) {
			final String typeName = type.getTypeName();
			final String customRepositoryName = String.format("%sCustom", typeName);
			Class customRepositoryClass;
			try {
				customRepositoryClass = Class.forName(customRepositoryName);
			} catch (ClassNotFoundException e) {
				log.error("Class {} not found, continuing.", customRepositoryName);
				continue;
			}
			Type[] interfaces = customRepositoryClass.getGenericInterfaces();
			ParameterizedType customRepositoryCriteriaType = (ParameterizedType) interfaces[0];
			Type actualType = customRepositoryCriteriaType.getActualTypeArguments()[0];
			Class<?> concreteType = (Class<?>) actualType;
			return concreteType;
		}
		log.error("Criteria class {} not found for criteria matcher, returning null.", crudRepositoryImplementingCriteriaMatcher);
		return null;
	}

}
