package com.cezarykluczynski.stapi.model.common.query;

import com.cezarykluczynski.stapi.model.common.entity.Gender;
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
import java.time.LocalDate;
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

	private Set<Attribute<? super T, ?>> attributeSet;

	private TypedQuery<T> baseTypedQuery;

	private TypedQuery<Long> countTypedQuery;

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
		validateAttributeExistenceAndType(key, String.class);
		if (value != null) {
			predicateList.add(criteriaBuilder.like(baseRoot.get(key), wildcardLike(value)));
		}
		return this;
	}

	public QueryBuilder<T> joinIn(String join, String key, Set<Long> value, Class joinClassType) {
		validateAttributeExistenceAndType(join, joinClassType);
		if (value != null) {
			predicateList.add(baseRoot.get(join).get(key).in(value));
		}
		return this;
	}

	public QueryBuilder<T> joinEquals(String join, String key, Enum<?> value, Class joinClassType) {
		validateAttributeExistenceAndType(join, joinClassType);
		if (value != null) {
			predicateList.add(baseRoot.get(join).get(key).in(Lists.newArrayList(value)));
		}
		return this;
	}

	public QueryBuilder<T> equal(String key, Boolean value) {
		validateAttributeExistenceAndType(key, Boolean.class);
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(String key, String value) {
		validateAttributeExistenceAndType(key, String.class);
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(String key, Long value) {
		validateAttributeExistenceAndType(key, Long.class);
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(String key, Gender value) {
		validateAttributeExistenceAndType(key, Gender.class);
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> between(String key, LocalDate from, LocalDate to) {
		validateAttributeExistenceAndType(key, LocalDate.class);

		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
		}

		if (from != null && to == null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
		}

		if (from == null && to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
		}

		return this;
	}

	public QueryBuilder<T> between(String key, Integer from, Integer to) {
		validateAttributeExistenceAndType(key, Integer.class);

		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
		}

		if (from != null && to == null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
		}

		if (from == null && to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
		}

		return this;
	}

	public Page<T> findPage() {
		prepareQueries();

		countTypedQuery = entityManager.createQuery(countCriteriaQuery);

		List<T> baseEntityList = baseTypedQuery.getResultList();
		Long count = countTypedQuery.getSingleResult();

		return new PageImpl<>(baseEntityList, pageable, count);
	}

	public List<T> findAll() {
		prepareQueries();

		return baseTypedQuery.getResultList();
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
		attributeSet = entityType.getAttributes();
	}

	private void prepareQueries() {
		if (predicateList.size() > 0) {
			Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			baseCriteriaQuery.where(predicate);
			countCriteriaQuery.where(predicate);
		}

		baseTypedQuery = entityManager.createQuery(baseCriteriaQuery);
		baseTypedQuery.setMaxResults(pageable.getPageSize());
		baseTypedQuery.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
	}

	private void validateAttributeExistenceAndType(String key, Class type) {
		attributeSet.stream()
				.filter(tAttribute -> key.equals(tAttribute.getName()))
				.filter(tAttribute -> type.equals(Boolean.class) ? type.getName().equals("java.lang.Boolean") :
						type.equals(tAttribute.getJavaType()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException(String.format("No attribute named %s of type %s for entity " +
						"%s found", key, type, baseClass.getName())));
	}

	private String wildcardLike(String subject) {
		return "%" + subject.replaceAll("\\s", "%") + "%";
	}

}
