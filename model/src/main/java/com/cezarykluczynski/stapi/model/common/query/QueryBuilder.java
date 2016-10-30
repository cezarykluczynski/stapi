package com.cezarykluczynski.stapi.model.common.query;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Set;

public class QueryBuilder<T> {

	private EntityManager entityManager;

	private Class baseClass;

	private Pageable pageable;

	private CriteriaBuilder criteriaBuilder;

	private Root<T> baseRoot;

	private CriteriaQuery<Long> countCriteriaQuery;

	private CriteriaQuery<T> baseCriteriaQuery;

	private List<Predicate> predicateList;

	private Set<Attribute<T, ?>> attributeSet;

	QueryBuilder(EntityManager entityManager, Class baseClass, Pageable pageable) {
		Preconditions.checkNotNull(entityManager, "EntityManager has to be set");
		Preconditions.checkNotNull(baseClass, "Base class has to be set");
		Preconditions.checkNotNull(pageable, "Pageable has to be set");

		this.entityManager = entityManager;
		this.baseClass = baseClass;
		this.pageable = pageable;
		this.prepare();
	}

	public QueryBuilder<T> like(String key, String value) {
		if (value != null) {
			validateAttributeExistenceAndType(key, String.class);
			predicateList.add(criteriaBuilder.like(baseRoot.get(key), wildcardLike(value)));
		}
		return this;
	}

	public Page<T> search() {
		if (predicateList.size() > 0) {
			Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			baseCriteriaQuery.where(predicate);
			countCriteriaQuery.where(predicate);
		}

		TypedQuery<T> baseTypedQuery = entityManager.createQuery(baseCriteriaQuery);
		baseTypedQuery.setMaxResults(pageable.getPageSize());
		baseTypedQuery.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
		TypedQuery<Long> countTypedQuery = entityManager.createQuery(countCriteriaQuery);

		List<T> baseEntityList = baseTypedQuery.getResultList();
		Long count = countTypedQuery.getSingleResult();

		return new PageImpl<>(baseEntityList, pageable, count);
	}


	private void prepare() {
		Preconditions.checkNotNull(entityManager, "EntityManager has to be set");
		Preconditions.checkNotNull(baseClass, "Base class has to be set");

		criteriaBuilder = entityManager.getCriteriaBuilder();
		predicateList = Lists.newArrayList();

		prepareBase();
		prepareCount();
		prepareAttributeSets();
	}

	private void prepareBase() {
		baseCriteriaQuery = criteriaBuilder.createQuery(baseClass);
		baseRoot = baseCriteriaQuery.from(baseClass);
		baseCriteriaQuery.select(baseRoot);
	}

	private void prepareCount() {
		countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		countCriteriaQuery.from(baseClass);
		countCriteriaQuery.select(criteriaBuilder.count(baseRoot));
	}

	private void prepareAttributeSets() {
		EntityType<T> entityType = entityManager.getMetamodel().entity(baseClass);
		attributeSet = entityType.getDeclaredAttributes();
	}

	private void validateAttributeExistenceAndType(String key, Class type) {
		attributeSet.stream()
				.filter(tAttribute -> key.equals(tAttribute.getName()))
				.filter(tAttribute -> type.equals(tAttribute.getJavaType()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException(String.format("No attribute named %s of type %s for entity " +
						"%s found", key, type, baseClass.getName())));
	}

	private String wildcardLike(String subject) {
		return "%" + subject.replaceAll("\\s", "%") + "%";
	}



}
