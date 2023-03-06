package com.cezarykluczynski.stapi.model.common.query;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LazyInitializationException;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"ClassFanOutComplexity", "MethodCount"})
public class QueryBuilder<T> {

	private static final String PERCENT_SIGN = "%";
	private static final String HIBERNATE_CACHEABLE = "org.hibernate.cacheable";

	private final EntityManager entityManager;

	private final Class baseClass;

	private final Pageable pageable;

	private CriteriaBuilder criteriaBuilder;

	private CriteriaBuilder countCriteriaBuilder;

	private Root<T> baseRoot;

	private List<Root<T>> baseRoots = Lists.newArrayList();

	private Root<T> countBaseRoot;

	private CriteriaQuery<Long> countCriteriaQuery;

	private CriteriaQuery<T> baseCriteriaQuery;

	private List<CriteriaQuery<T>> baseCriteriaQueries = Lists.newArrayList();

	private List<Predicate> predicateList;

	private List<Predicate> countPredicateList;

	private Set<Attribute<? super T, ?>> attributeSet;

	private TypedQuery<Long> countTypedQuery;

	private boolean singleEntitySearch;

	private List<RequestSortClauseDTO> requestSortClauseDTOList = Lists.newArrayList();

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
			predicateList.add(criteriaBuilder.like(criteriaBuilder.upper(baseRoot.get(key)), wildcardLike(value).toUpperCase()));
			countPredicateList.add(countCriteriaBuilder.like(countCriteriaBuilder.upper(countBaseRoot.get(key)), wildcardLike(value).toUpperCase()));
		}
		return this;
	}

	public QueryBuilder<T> like(SingularAttribute key, SingularAttribute key2, String value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.like(criteriaBuilder.upper(baseRoot.get(key).get(key2)), wildcardLike(value).toUpperCase()));
			countPredicateList.add(countCriteriaBuilder.like(countCriteriaBuilder.upper(countBaseRoot.get(key).get(key2)),
					wildcardLike(value).toUpperCase()));
		}
		return this;
	}

	public QueryBuilder<T> joinPageIdsIn(Set<Long> value) {
		if (value != null) {
			predicateList.add(baseRoot.get("page").get("pageId").in(value));
		}
		return this;
	}

	public QueryBuilder<T> joinPropertyEqual(SingularAttribute<? super T, ?> key, String propertyName, Object value) {
		if (value != null) {
			predicateList.add(baseRoot.get(key).get(propertyName).in(value));
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
			countPredicateList.add(countCriteriaBuilder.equal(countBaseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, String> key, String value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
			countPredicateList.add(countCriteriaBuilder.equal(countBaseRoot.get(key), value));
			singleEntitySearch = "uid".equals(key.getName());
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, Long> key, Long value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
			countPredicateList.add(countCriteriaBuilder.equal(countBaseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute key, SingularAttribute key2, Long value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key).get(key2), value));
			countPredicateList.add(countCriteriaBuilder.equal(countBaseRoot.get(key).get(key2), value));
		}
		return this;
	}

	public QueryBuilder<T> equal(SingularAttribute<? super T, ? extends Enum> key, Enum value) {
		if (value != null) {
			predicateList.add(criteriaBuilder.equal(baseRoot.get(key), value));
			countPredicateList.add(countCriteriaBuilder.equal(countBaseRoot.get(key), value));
		}
		return this;
	}

	public QueryBuilder<T> between(SingularAttribute<? super T, LocalDate> key, LocalDate from, LocalDate to) {
		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
			countPredicateList.add(countCriteriaBuilder.between(countBaseRoot.get(key), from, to));
		} else if (from != null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
			countPredicateList.add(countCriteriaBuilder.greaterThanOrEqualTo(countBaseRoot.get(key), from));
		} else if (to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
			countPredicateList.add(countCriteriaBuilder.lessThanOrEqualTo(countBaseRoot.get(key), to));
		}
		return this;
	}

	public QueryBuilder<T> between(SingularAttribute<? super T, Integer> key, Integer from, Integer to) {
		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
			countPredicateList.add(countCriteriaBuilder.between(countBaseRoot.get(key), from, to));
		} else if (from != null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
			countPredicateList.add(countCriteriaBuilder.greaterThanOrEqualTo(countBaseRoot.get(key), from));
		} else if (to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
			countPredicateList.add(countCriteriaBuilder.lessThanOrEqualTo(countBaseRoot.get(key), to));
		}
		return this;
	}

	public QueryBuilder<T> between(SingularAttribute<? super T, Float> key, Float from, Float to) {
		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
			countPredicateList.add(countCriteriaBuilder.between(countBaseRoot.get(key), from, to));
		} else if (from != null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
			countPredicateList.add(countCriteriaBuilder.greaterThanOrEqualTo(countBaseRoot.get(key), from));
		} else if (to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
			countPredicateList.add(countCriteriaBuilder.lessThanOrEqualTo(countBaseRoot.get(key), to));
		}
		return this;
	}

	public QueryBuilder<T> between(SingularAttribute<? super T, Double> key, Double from, Double to) {
		if (from != null && to != null) {
			predicateList.add(criteriaBuilder.between(baseRoot.get(key), from, to));
			countPredicateList.add(countCriteriaBuilder.between(countBaseRoot.get(key), from, to));
		} else if (from != null) {
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(baseRoot.get(key), from));
			countPredicateList.add(countCriteriaBuilder.greaterThanOrEqualTo(countBaseRoot.get(key), from));
		} else if (to != null) {
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(baseRoot.get(key), to));
			countPredicateList.add(countCriteriaBuilder.lessThanOrEqualTo(countBaseRoot.get(key), to));
		}
		return this;
	}

	public QueryBuilder<T> fetch(SetAttribute<T, ?> name) {
		baseRoot.fetch(name, JoinType.LEFT);
		return this;
	}

	public QueryBuilder<T> fetch(SingularAttribute<T, ?> name) {
		baseRoot.fetch(name, JoinType.LEFT);
		return this;
	}

	public QueryBuilder<T> fetch(SingularAttribute<? super T, ?> key, SingularAttribute<?, ?> keyOfKey) {
		SingularAttribute<? super Object, ?> keyOfKey2 = (SingularAttribute<? super Object, ?>) keyOfKey;
		baseRoot.fetch(key, JoinType.LEFT).fetch(keyOfKey2, JoinType.LEFT);
		return this;
	}

	public QueryBuilder<T> fetch(SetAttribute<? super T, ?> key, SingularAttribute<?, ?> keyOfKey) {
		SingularAttribute<? super Object, ?> keyOfKey2 = (SingularAttribute<? super Object, ?>) keyOfKey;
		baseRoot.fetch(key, JoinType.LEFT).fetch(keyOfKey2, JoinType.LEFT);
		return this;
	}

	public QueryBuilder<T> fetch(SetAttribute<T, ?> name, boolean doFetch) {
		return doFetch ? fetch(name) : this;
	}

	public QueryBuilder<T> fetch(SingularAttribute<T, ?> name, boolean doFetch) {
		return doFetch ? fetch(name) : this;
	}

	public QueryBuilder<T> fetch(SingularAttribute<? super T, ?> key, SingularAttribute<?, ?> keyOfKey, boolean doFetch) {
		return doFetch ? fetch(key, keyOfKey) : this;
	}

	public QueryBuilder<T> fetch(SetAttribute<? super T, ?> key, SingularAttribute<?, ?> keyOfKey, boolean doFetch) {
		return doFetch ? fetch(key, keyOfKey) : this;
	}

	public QueryBuilder<T> setSort(RequestSortDTO requestSortDTO) {
		if (requestSortDTO == null || CollectionUtils.isEmpty(requestSortDTO.getClauses())) {
			return this;
		}

		requestSortClauseDTOList.addAll(requestSortDTO.getClauses());
		return this;
	}

	// The purpose of this method is to create another query after previously declared fetches,
	// effectively dealing with Cartesian Product Problem by breaking query into several smaller queries.
	// However, maybe a better name could be found.
	public QueryBuilder<T> divideQueries() {
		return divideQueries(false);
	}

	@SneakyThrows
	@SuppressWarnings("NPathComplexity")
	private QueryBuilder<T> divideQueries(boolean finishingCall) {
		if (!singleEntitySearch) {
			return this;
		}
		if (finishingCall && baseRoots.isEmpty() && baseCriteriaQueries.isEmpty()) {
			return this;
		}
		SqmCopyContext sqmCopyContext = SqmCopyContext.simpleContext();
		if (finishingCall) {
			baseRoots.add(baseRoot);
			baseCriteriaQueries.add(baseCriteriaQuery);
			return this;
		}
		if (baseRoot instanceof SqmRoot) {
			final SqmRoot<T> baseRootCopy = ((SqmRoot<T>) baseRoot).copy(sqmCopyContext);
			try {
				final Field field = baseRootCopy.getClass().getSuperclass().getDeclaredField("joins");
				field.setAccessible(true);
				final List value = (List) field.get(baseRootCopy);
				if (value == null || value.isEmpty()) {
					// no more fetches, a legitimate search that should not be split
					return this;
				}
				value.clear();
			} catch (NoSuchFieldException e) {
				// ony happen in tests, ignore
			}
			baseRoots.add(baseRoot);
			baseRoot = baseRootCopy;
			if (baseCriteriaQuery instanceof SqmSelectStatement) {
				final SqmSelectStatement<T> baseCriteriaQueryCopy = ((SqmSelectStatement<T>) baseCriteriaQuery).copy(sqmCopyContext);
				baseCriteriaQueryCopy.select(baseRootCopy);
				baseCriteriaQueries.add(baseCriteriaQuery);
				baseCriteriaQuery = baseCriteriaQueryCopy;
			} else {
				throw new RuntimeException("Wrong implementation! Expected baseCriteriaQuery to be of type SqmSelectStatement.");
			}
		} else {
			throw new RuntimeException("Wrong implementation! Expected baseRoot to be of type SqmRoot.");
		}
		return this;
	}

	public Page<T> findPage() {
		return findResults();
	}

	public List<T> findAll() {
		return findResults().getContent();
	}

	private void prepare() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
		countCriteriaBuilder = entityManager.getCriteriaBuilder();
		predicateList = Lists.newArrayList();
		countPredicateList = Lists.newArrayList();

		baseCriteriaQuery = criteriaBuilder.createQuery(baseClass);
		baseRoot = baseCriteriaQuery.from(baseClass);
		baseCriteriaQuery.select(baseRoot);

		countCriteriaQuery = countCriteriaBuilder.createQuery(Long.class);
		countBaseRoot = countCriteriaQuery.from(baseClass);
		countCriteriaQuery.select(countCriteriaBuilder.count(countBaseRoot));

		EntityType<T> entityType = entityManager.getMetamodel().entity(baseClass);
		attributeSet = entityType.getAttributes();
	}

	@SneakyThrows
	@SuppressWarnings({"VariableDeclarationUsageDistance", "CyclomaticComplexity", "HiddenField", "NPathComplexity"})
	private Page<T> findResults() {
		divideQueries(true);
		if (baseCriteriaQueries.isEmpty()) {
			baseCriteriaQueries.add(baseCriteriaQuery);
		}
		if (baseRoots.isEmpty()) {
			baseRoots.add(baseRoot);
		}
		int pageSize;
		int pageNumber;
		if (singleEntitySearch) {
			// just to be sure
			pageSize = 1;
			pageNumber = 0;
		} else {
			pageSize = pageable.getPageSize();
			pageNumber = pageable.getPageNumber();
		}
		List<T> baseEntityList = List.of();
		for (int i = 0; i < baseCriteriaQueries.size(); i++) {
			TypedQuery<T> baseTypedQuery;
			CriteriaQuery<T> baseCriteriaQuery = baseCriteriaQueries.get(i);
			if (predicateList.size() > 0) {
				Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				baseCriteriaQuery.where(predicate);
			}
			baseCriteriaQuery.orderBy(getOrderByList());
			baseTypedQuery = entityManager.createQuery(baseCriteriaQuery);
			baseTypedQuery.setMaxResults(pageSize);
			baseTypedQuery.setFirstResult(pageSize * pageNumber);
			baseTypedQuery.setHint(HIBERNATE_CACHEABLE, false); // caching done on CriteriaMatcher interface level
			List<T> localBaseEntityList = baseTypedQuery.getResultList();
			if (i == 0) {
				baseEntityList = localBaseEntityList;
			} else if (baseEntityList.size() == 1 && singleEntitySearch) {
				T nextEntity = localBaseEntityList.get(0);
				T entity = baseEntityList.get(0);
				final Field[] declaredFields = entity.getClass().getDeclaredFields();
				for (Field field : declaredFields) {
					if (Set.class.isAssignableFrom(field.getType())) {
						field.setAccessible(true);
						Set entitySet = (Set) field.get(entity);
						Set nextEntitySet = (Set) field.get(nextEntity);
						boolean entityError = false;

						int entitySetSize = 0;
						int nextEntitySetSize = 0;
						try {
							entitySetSize = entitySet.size();
						} catch (LazyInitializationException ignore) {
							entityError = true;
						}
						try {
							nextEntitySetSize = nextEntitySet.size();
						} catch (LazyInitializationException ignore) {
							// do nothing
						}

						if (nextEntitySetSize > 0 && entitySetSize == 0) {
							field.set(entity, nextEntitySet);
						} else if (nextEntitySetSize == 0 && entityError) {
							field.set(entity, Sets.newHashSet());
						} else if (nextEntitySetSize > 0 && entitySetSize > 0) {
							if (nextEntitySetSize == entitySetSize) {
								throw new RuntimeException("Misconfigured: nextEntitySetSize == entitySetSize, but non-zero.");
							} else {
								throw new RuntimeException("Misconfigured: nextEntitySetSize != entitySetSize, but non-zero.");
							}
						}
					}
				}
			}
		}

		Long count;
		if (pageSize > baseEntityList.size() && pageNumber == 0 || singleEntitySearch) {
			count = (long) baseEntityList.size();
		} else {
			if (countPredicateList.size() > 0) {
				Predicate predicate = countCriteriaBuilder.and(countPredicateList.toArray(new Predicate[countPredicateList.size()]));
				countCriteriaQuery.where(predicate);
			}
			countTypedQuery = entityManager.createQuery(countCriteriaQuery);
			count = countTypedQuery.getSingleResult();
		}

		return new PageImpl<>(baseEntityList, pageable, count);
	}

	private List<jakarta.persistence.criteria.Order> getOrderByList() {
		return getSortedRequestSortClauseDTOList()
				.stream()
				.map(requestSortClauseDTO -> {
					RequestSortDirectionDTO requestSortDirectionDTO = Optional
							.ofNullable(requestSortClauseDTO.getDirection())
							.orElse(RequestSortDirectionDTO.ASC);

					Path<String> path;
					String name = requestSortClauseDTO.getName();
					try {
						path = baseRoot.get(name);
					} catch (IllegalArgumentException e) {
						throw new StapiRuntimeException(String.format("Could not find field \"%s\" in resource \"%s\".",
								name, baseClass.getSimpleName()));
					}

					return requestSortDirectionDTO.equals(RequestSortDirectionDTO.ASC) ? criteriaBuilder.asc(path) : criteriaBuilder.desc(path);
				})
				.collect(Collectors.toList());
	}

	private List<RequestSortClauseDTO> getSortedRequestSortClauseDTOList() {
		Integer maxOrder = requestSortClauseDTOList
				.stream()
				.map(RequestSortClauseDTO::getClauseOrder)
				.filter(Objects::nonNull)
				.reduce(Integer::max)
				.orElse(0);

		List<RequestSortClauseDTO> requestSortClauseDTOListWithoutClauseSort = requestSortClauseDTOList
				.stream()
				.filter(requestSortClauseDTO -> requestSortClauseDTO.getClauseOrder() == null)
				.collect(Collectors.toList());

		for (RequestSortClauseDTO requestSortClauseDTO : requestSortClauseDTOListWithoutClauseSort) {
			maxOrder++;
			requestSortClauseDTO.setClauseOrder(maxOrder);
		}

		return requestSortClauseDTOList
				.stream()
				.sorted(Comparator.comparing(RequestSortClauseDTO::getClauseOrder))
				.collect(Collectors.toList());
	}

	private void validateAttributeExistenceAndType(String key, Class type) {
		attributeSet.stream()
				.filter(tAttribute -> key.equals(tAttribute.getName()))
				.filter(tAttribute -> type.equals(Boolean.class) ? type.getName().equals("java.lang.Boolean") : type.equals(tAttribute.getJavaType()))
				.findFirst()
				.orElseThrow(() -> new StapiRuntimeException(String.format("No attribute named %s of type %s for entity %s found",
						key, type, baseClass.getName())));
	}

	private String wildcardLike(String subject) {
		return PERCENT_SIGN + subject.replaceAll("\\s", PERCENT_SIGN) + PERCENT_SIGN;
	}

}
