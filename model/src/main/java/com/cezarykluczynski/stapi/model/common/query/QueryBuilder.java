package com.cezarykluczynski.stapi.model.common.query;

import com.cezarykluczynski.stapi.model.common.dto.RequestOrderClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestOrderEnumDTO;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

	private List<RequestOrderClauseDTO> requestOrderClauseDTOList = Lists.newArrayList();

	QueryBuilder(EntityManager entityManager, Class baseClass, Pageable pageable) {
		Preconditions.checkNotNull(entityManager, "EntityManager has to be set");
		Preconditions.checkNotNull(baseClass, "Base class has to be set");
		Preconditions.checkNotNull(pageable, "Pageable has to be set");

		this.entityManager = entityManager;
		this.baseClass = baseClass;
		this.pageable = pageable;
		this.prepare();
	}

	public QueryBuilder<T> like(SingularAttribute<? super T, String> key, String value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.like(baseRoot.get(key), wildcardLike(value)));
		}
		return this;
	}

	public QueryBuilder<T> joinPageIdsIn(Set<Long> value) {
		if (value != null) {
			predicateList.add(baseRoot.get("page").get("pageId").in(value));
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

	public QueryBuilder<T> equal(SingularAttribute<? super T, Boolean> key, Boolean value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, String> key, String value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, Long> key, Long value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, Gender> key, Gender value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> between(SingularAttribute<? super T, LocalDate> key, LocalDate from, LocalDate to) {
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

	public QueryBuilder<T> between(SingularAttribute<? super T, Integer> key, Integer from, Integer to) {
//		validateAttributeExistenceAndType(key, Integer.class);

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

	public QueryBuilder<T> fetch(SetAttribute<T, ?> name) {
		baseRoot.fetch(name);

		return this;
	}

	public QueryBuilder<T> fetch(SetAttribute<T, ?> name, boolean doFetch) {
		return doFetch ? fetch(name) : this;
	}

	public QueryBuilder<T> setOrder(RequestOrderDTO requestOrderDTO) {
		if (requestOrderDTO == null || CollectionUtils.isEmpty(requestOrderDTO.getClauses())) {
			return this;
		}

		requestOrderClauseDTOList.addAll(requestOrderDTO.getClauses());

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

		baseCriteriaQuery.orderBy(getOrderByList());
		baseTypedQuery = entityManager.createQuery(baseCriteriaQuery);
		baseTypedQuery.setMaxResults(pageable.getPageSize());
		baseTypedQuery.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
	}

	private List<javax.persistence.criteria.Order> getOrderByList() {
		return getSortedRequestOrderClauseDTOList()
				.stream()
				.map(requestOrderClauseDTO -> {
					RequestOrderEnumDTO requestOrderEnumDTO = Optional
							.ofNullable(requestOrderClauseDTO.getOrder())
							.orElse(RequestOrderEnumDTO.ASC);

					Path<String> path;
					String name = requestOrderClauseDTO.getName();
					try {
						path = baseRoot.get(name);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(String.format("Could not find field \"%s\" in resource \"%s\".",
								name, baseClass.getSimpleName()));
					}

					return requestOrderEnumDTO.equals(RequestOrderEnumDTO.ASC) ? criteriaBuilder.asc(path) :
							criteriaBuilder.desc(path);
				})
				.collect(Collectors.toList());
	}

	private List<RequestOrderClauseDTO> getSortedRequestOrderClauseDTOList() {
		Integer maxOrder = requestOrderClauseDTOList
				.stream()
				.map(RequestOrderClauseDTO::getClauseOrder)
				.filter(clauseOrder -> clauseOrder != null)
				.reduce(Integer::max)
				.orElse(0);

		List<RequestOrderClauseDTO> requestOrderClauseDTOListWithoutClauseOrder = requestOrderClauseDTOList
				.stream()
				.filter(requestOrderClauseDTO -> requestOrderClauseDTO.getClauseOrder() == null)
				.collect(Collectors.toList());

		for (RequestOrderClauseDTO requestOrderClauseDTO : requestOrderClauseDTOListWithoutClauseOrder) {
			maxOrder++;
			requestOrderClauseDTO.setClauseOrder(maxOrder);
		}

		return requestOrderClauseDTOList
				.stream()
				.sorted((a, b) -> a.getClauseOrder().compareTo(b.getClauseOrder()))
				.collect(Collectors.toList());
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
