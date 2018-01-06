package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Fetch
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Order
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.persistence.metamodel.Attribute
import javax.persistence.metamodel.EntityType
import javax.persistence.metamodel.Metamodel
import javax.persistence.metamodel.SetAttribute
import javax.persistence.metamodel.SingularAttribute
import java.time.LocalDate

class QueryBuilderTest extends Specification {

	private static final String VALID_VALUE_STRING = 'valid_value_string'
	private static final String VALID_VALUE_STRING_UPPER_CASE = 'VALID_VALUE_STRING'
	private static final Boolean VALID_VALUE_BOOLEAN = RandomUtil.nextBoolean()
	private static final LocalDate VALID_VALUE_LOCAL_DATE_FROM = LocalDate.of(2000, 1, 2)
	private static final LocalDate VALID_VALUE_LOCAL_DATE_TO = LocalDate.of(2010, 3, 4)
	private static final Integer VALID_VALUE_INTEGER_FROM = 1970
	private static final Integer VALID_VALUE_INTEGER_TO = 2000
	private static final Float VALID_VALUE_FLOAT_FROM = 7.3f
	private static final Float VALID_VALUE_FLOAT_TO = 8.1f
	private static final Double VALID_VALUE_DOUBLE_FROM = 7.2d
	private static final Double VALID_VALUE_DOUBLE_TO = 8.5d
	private static final String VALID_KEY_GENDER_STRING = 'VALID_KEY_GENDER_STRING'
	private static final Long VALID_VALUE_LONG = 5L
	private static final String VALID_JOIN_PAGE_ID = 'pageId'
	private static final Gender VALID_VALUE_GENDER = Gender.F
	private static final Integer PAGE_SIZE = 50
	private static final Integer PAGE_NUMBER = 5
	private static final Integer FIRST_RESULT = PAGE_SIZE * PAGE_NUMBER
	private static final String REQUEST_ORDER_CLAUSE_NAME_1 = 'REQUEST_ORDER_CLAUSE_NAME_1'
	private static final String REQUEST_ORDER_CLAUSE_NAME_2 = 'REQUEST_ORDER_CLAUSE_NAME_2'
	private static final String REQUEST_ORDER_CLAUSE_NAME_3 = 'REQUEST_ORDER_CLAUSE_NAME_3'
	private static final RequestSortDirectionDTO REQUEST_SORT_DIRECTION_1 = RequestSortDirectionDTO.ASC
	private static final RequestSortDirectionDTO REQUEST_SORT_DIRECTION_3 = RequestSortDirectionDTO.DESC
	private static final Integer REQUEST_ORDER_CLAUSE_CLAUSE_ORDER_1 = 1
	private static final Integer REQUEST_ORDER_CLAUSE_CLAUSE_ORDER_2 = 2
	private static final Boolean CACHEABLE = RandomUtil.nextBoolean()
	private final SingularAttribute<?, String> validKeyString = Mock()
	private final SingularAttribute<?, String> validKeyString2 = Mock()
	private final SingularAttribute<?, Boolean> validKeyBoolean = Mock()
	private final SingularAttribute<?, Long> validKeyLong = Mock()
	private final SingularAttribute<?, Long> validKeyLong2 = Mock()
	private final SetAttribute<?, ?> fetchSetName = Mock()
	private final SetAttribute<?, ?> fetchSingularName = Mock()
	private final SingularAttribute<?, LocalDate> validKeyLocalDate = Mock()
	private final SingularAttribute<?, Integer> validKeyInteger = Mock()
	private final SingularAttribute<?, Float> validKeyFloat = Mock()
	private final SingularAttribute<?, Double> validKeyDouble = Mock()
	private final SingularAttribute<?, Gender> validKeyGender = Mock()
	private final SingularAttribute<?, ?> keyWithUnknownType = Mock()
	private final String validKeyPage = 'page'
	private final String invalidKeyPage = 'notPage'
	private final Path requestOrderClausePath1 = Mock()
	private final Path requestOrderClausePath2 = Mock()
	private final Path requestOrderClausePath3 = Mock()
	private final Order requestOrderClauseOrder3 = Mock()
	private final Order requestOrderClauseOrder2 = Mock()
	private final Order requestOrderClauseOrder1 = Mock()

	private static final RequestSortDTO ORDER_REQUEST = new RequestSortDTO(
			clauses: Lists.newArrayList(
					new RequestSortClauseDTO(
							name: REQUEST_ORDER_CLAUSE_NAME_3,
							direction: REQUEST_SORT_DIRECTION_3
					),
					new RequestSortClauseDTO(
							name: REQUEST_ORDER_CLAUSE_NAME_2,
							clauseOrder: REQUEST_ORDER_CLAUSE_CLAUSE_ORDER_2
					),
					new RequestSortClauseDTO(
							name: REQUEST_ORDER_CLAUSE_NAME_1,
							direction: REQUEST_SORT_DIRECTION_1,
							clauseOrder: REQUEST_ORDER_CLAUSE_CLAUSE_ORDER_1
					)
			)
	)

	private QueryBuilder<Series> queryBuilder

	private CriteriaQuery<Series> baseCriteriaQuery

	private CriteriaQuery<Long> countCriteriaQuery

	private CriteriaBuilder criteriaBuilder

	private EntityManager entityManager

	private CachingStrategy cachingStrategy

	private Root<Series> baseRoot

	private Fetch<?, ?> fetch

	private Expression<Long> countExpression

	private Metamodel metamodel

	private EntityType<Series> entityType

	private Set<Attribute<Series, ?>> attributeSet

	private Class baseClass

	private Pageable pageable

	private Predicate predicate

	private TypedQuery<Series> baseTypedQuery

	private TypedQuery<Long> countTypedQuery

	private List<Series> baseEntityList

	private Path path

	private Long count

	void setup() {
		baseCriteriaQuery = Mock()
		countCriteriaQuery = Mock()
		criteriaBuilder = Mock()
		entityManager = Mock()
		cachingStrategy = Mock()
		baseRoot = Mock()
		fetch = Mock()
		countExpression = Mock()
		metamodel = Mock()
		entityType = Mock()
		Attribute validKeyStringAttribute = Mock()
		validKeyStringAttribute.javaType >> String
		validKeyStringAttribute.name >> validKeyString
		Attribute validKeyBooleanAttribute = Mock()
		validKeyBooleanAttribute.javaType >> Boolean
		validKeyBooleanAttribute.name >> validKeyBoolean
		Attribute validKeyLongAttribute = Mock()
		validKeyLongAttribute.javaType >> Long
		validKeyLongAttribute.name >> validKeyLong
		Attribute validKeyLocalDateAttribute = Mock()
		validKeyLocalDateAttribute.javaType >> LocalDate
		validKeyLocalDateAttribute.name >> validKeyLocalDate
		Attribute validKeyGenderAttribute = Mock()
		validKeyGenderAttribute.javaType >> Gender
		validKeyGenderAttribute.name >> validKeyGender
		Attribute validKeyInvalidTypeAttribute = Mock()
		validKeyInvalidTypeAttribute.javaType >> Long
		validKeyInvalidTypeAttribute.name >> keyWithUnknownType
		Attribute validKeyPageAttribute = Mock()
		validKeyPageAttribute.javaType >> com.cezarykluczynski.stapi.model.page.entity.Page
		validKeyPageAttribute.name >> validKeyPage
		Attribute validKeyIntegerAttribute = Mock()
		validKeyIntegerAttribute.javaType >> Integer
		validKeyIntegerAttribute.name >> validKeyInteger
		attributeSet = Sets.newHashSet(
				validKeyStringAttribute,
				validKeyBooleanAttribute,
				validKeyLongAttribute,
				validKeyLocalDateAttribute,
				validKeyGenderAttribute,
				validKeyInvalidTypeAttribute,
				validKeyPageAttribute,
				validKeyIntegerAttribute
		)
		baseClass = Series
		pageable = Mock()
		predicate = Mock()
		baseTypedQuery = Mock()
		countTypedQuery = Mock()
		baseEntityList = Lists.newArrayList()
		path = Mock()
		count = 7L
	}

	@SuppressWarnings('ExplicitCallToAndMethod')
	void "query builder is created, preconditions are added, then search is performed"() {
		when: 'query builder is create'
		queryBuilder = new QueryBuilder<>(entityManager, cachingStrategy, baseClass, pageable)

		then: 'query builder is configured'
		1 * entityManager.getCriteriaBuilder() >> criteriaBuilder
		1 * criteriaBuilder.createQuery(Series) >> baseCriteriaQuery
		1 * baseCriteriaQuery.from(Series) >> baseRoot
		1 * baseCriteriaQuery.select(baseRoot)
		1 * criteriaBuilder.createQuery(Long) >> countCriteriaQuery
		1 * countCriteriaQuery.from(baseClass)
		1 * criteriaBuilder.count(baseRoot) >> countExpression
		1 * countCriteriaQuery.select(countExpression)
		1 * entityManager.getMetamodel() >> metamodel
		1 * metamodel.entity(baseClass) >> entityType
		1 * entityType.attributes >> attributeSet

		then: 'no other interactions are expected'
		0 * _

		then: 'query builder is returned'
		queryBuilder != null

		when: 'valid string key is added for like comparison'
		queryBuilder.like(validKeyString, VALID_VALUE_STRING)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyString) >> path
		1 * criteriaBuilder.upper(path) >> path
		1 * criteriaBuilder.like(path, "%${VALID_VALUE_STRING_UPPER_CASE}%")

		when: 'valid string key with secondary key is added for like comparison'
		queryBuilder.like(validKeyString, validKeyString2, VALID_VALUE_STRING)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyString) >> path
		1 * path.get(validKeyString2) >> path
		1 * criteriaBuilder.upper(path) >> path
		1 * criteriaBuilder.like(path, "%${VALID_VALUE_STRING_UPPER_CASE}%")

		when: 'valid string key is added to equal comparison'
		queryBuilder.equal(validKeyString, VALID_VALUE_STRING)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyString) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_STRING)

		when: 'valid boolean key is added'
		queryBuilder.equal(validKeyBoolean, VALID_VALUE_BOOLEAN)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyBoolean) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_BOOLEAN)

		when: 'valid long key is added'
		queryBuilder.equal(validKeyLong, validKeyLong2, VALID_VALUE_LONG)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyLong) >> path
		1 * path.get(validKeyLong2) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_LONG)

		when: 'valid long key with secondary key is added'
		queryBuilder.equal(validKeyLong, VALID_VALUE_LONG)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyLong) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_LONG)

		when: 'valid LocalDate range key is added'
		queryBuilder.between(validKeyLocalDate, VALID_VALUE_LOCAL_DATE_FROM, VALID_VALUE_LOCAL_DATE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_LOCAL_DATE_FROM, VALID_VALUE_LOCAL_DATE_TO)

		when: 'only start LocalDate is specified'
		queryBuilder.between(validKeyLocalDate, VALID_VALUE_LOCAL_DATE_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_LOCAL_DATE_FROM)

		when: 'only end LocalDate is specified'
		queryBuilder.between(validKeyLocalDate, null, VALID_VALUE_LOCAL_DATE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_LOCAL_DATE_TO)

		when: 'valid Integer range key is added'
		queryBuilder.between(validKeyInteger, VALID_VALUE_INTEGER_FROM, VALID_VALUE_INTEGER_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_INTEGER_FROM, VALID_VALUE_INTEGER_TO)

		when: 'only start Integer is specified'
		queryBuilder.between(validKeyInteger, VALID_VALUE_INTEGER_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_INTEGER_FROM)

		when: 'only end Integer is specified'
		queryBuilder.between(validKeyInteger, null, VALID_VALUE_INTEGER_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_INTEGER_TO)

		when: 'valid Float range key is added'
		queryBuilder.between(validKeyFloat, VALID_VALUE_FLOAT_FROM, VALID_VALUE_FLOAT_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_FLOAT_FROM, VALID_VALUE_FLOAT_TO)

		when: 'only start Float is specified'
		queryBuilder.between(validKeyFloat, VALID_VALUE_FLOAT_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_FLOAT_FROM)

		when: 'only end Float is specified'
		queryBuilder.between(validKeyFloat, null, VALID_VALUE_FLOAT_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_FLOAT_TO)

		when: 'valid Double range key is added'
		queryBuilder.between(validKeyDouble, VALID_VALUE_DOUBLE_FROM, VALID_VALUE_DOUBLE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_DOUBLE_FROM, VALID_VALUE_DOUBLE_TO)

		when: 'only start Double is specified'
		queryBuilder.between(validKeyDouble, VALID_VALUE_DOUBLE_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_DOUBLE_FROM)

		when: 'only end Double is specified'
		queryBuilder.between(validKeyDouble, null, VALID_VALUE_DOUBLE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_DOUBLE_TO)

		when: 'valid gender key is added'
		queryBuilder.equal(validKeyGender, VALID_VALUE_GENDER)

		then: 'right methods are called'
		1 * baseRoot.get(validKeyGender) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_GENDER)

		when: 'join equals key is added'
		queryBuilder.joinPageIdsIn(Sets.newHashSet(1L))

		then: 'right methods are called'
		1 * baseRoot.get(validKeyPage) >> path
		1 * path.get(VALID_JOIN_PAGE_ID) >> path
		1 * path.in(_)

		when: 'join property equals is performed'
		queryBuilder.joinPropertyEqual(validKeyString, VALID_KEY_GENDER_STRING, Sets.newHashSet(VALID_VALUE_GENDER))

		then:
		1 * baseRoot.get(validKeyString) >> path
		1 * path.get(VALID_KEY_GENDER_STRING) >> path
		1 * path.in(_)

		when: 'join equals key is added'
		queryBuilder.joinEquals(validKeyPage, VALID_KEY_GENDER_STRING, VALID_VALUE_GENDER, com.cezarykluczynski.stapi.model.page.entity.Page)

		then:
		1 * baseRoot.get(validKeyPage) >> path
		1 * path.get(VALID_KEY_GENDER_STRING) >> path
		1 * path.in(Lists.newArrayList(VALID_VALUE_GENDER))

		when: 'key with invalid type is added'
		queryBuilder.joinEquals(invalidKeyPage, VALID_VALUE_STRING, VALID_VALUE_GENDER, com.cezarykluczynski.stapi.model.page.entity.Page)

		then: 'exception is thrown'
		thrown(StapiRuntimeException)

		when: 'fetch is performed'
		queryBuilder.fetch(fetchSetName)

		then: 'right methods are called'
		1 * baseRoot.fetch(fetchSetName, JoinType.LEFT)

		when: 'fetch is performed with boolean flag set to true'
		queryBuilder.fetch(fetchSetName, true)

		then: 'right methods are called'
		1 * baseRoot.fetch(fetchSetName, JoinType.LEFT)

		when: 'fetch is performed with boolean flag set to false'
		queryBuilder.fetch(fetchSetName, false)

		then: 'no fetch methods are called'
		0 * baseRoot.fetch(*_)

		when: 'singular fetch is performed'
		queryBuilder.fetch(fetchSingularName)

		then: 'singular set right methods are called'
		1 * baseRoot.fetch(fetchSingularName, JoinType.LEFT)

		when: 'singular fetch is performed with boolean flag set to true'
		queryBuilder.fetch(fetchSingularName, true)

		then: 'right methods are called'
		1 * baseRoot.fetch(fetchSingularName, JoinType.LEFT)

		when: 'singular fetch is performed with boolean flag set to false'
		queryBuilder.fetch(fetchSingularName, false)

		then: 'no fetch methods are called'
		0 * baseRoot.fetch(*_)

		when: 'two singular attributes fetch is performed'
		queryBuilder.fetch(validKeyLocalDate, keyWithUnknownType)

		then: 'right methods are called'
		1 * baseRoot.fetch(validKeyLocalDate, JoinType.LEFT) >> fetch
		1 * fetch.fetch(keyWithUnknownType, JoinType.LEFT)

		when: 'two singular attributes fetch is performed with boolean flag set to true'
		queryBuilder.fetch(validKeyLocalDate, keyWithUnknownType, true)

		then: 'right methods are called'
		1 * baseRoot.fetch(validKeyLocalDate, JoinType.LEFT) >> fetch
		1 * fetch.fetch(keyWithUnknownType, JoinType.LEFT)

		when: 'two singular attributes fetch is performed with boolean flag set to false'
		queryBuilder.fetch(validKeyLocalDate, keyWithUnknownType, false)

		then: 'no fetch methods are called'
		0 * baseRoot.fetch(*_)
		0 * fetch.fetch(*_)

		when: 'singular and set attributes fetch is performed'
		queryBuilder.fetch(fetchSetName, keyWithUnknownType)

		then: 'right methods are called'
		1 * baseRoot.fetch(fetchSetName, JoinType.LEFT) >> fetch
		1 * fetch.fetch(keyWithUnknownType, JoinType.LEFT)

		when: 'singular and set attributes fetch is performed with boolean flag set to true'
		queryBuilder.fetch(fetchSetName, keyWithUnknownType, true)

		then: 'right methods are called'
		1 * baseRoot.fetch(fetchSetName, JoinType.LEFT) >> fetch
		1 * fetch.fetch(keyWithUnknownType, JoinType.LEFT)

		when: 'singular and set attributes fetch is performed with boolean flag set to false'
		queryBuilder.fetch(fetchSetName, keyWithUnknownType, false)

		then: 'no fetch methods are called'
		0 * baseRoot.fetch(*_)
		0 * fetch.fetch(*_)

		when: 'order is added and search is performer'
		queryBuilder.setSort(ORDER_REQUEST)
		Page<Series> seriesPage = queryBuilder.findPage()

		then: 'queries are built'
		1 * criteriaBuilder.and(_) >> predicate
		1 * baseCriteriaQuery.where(predicate)
		1 * countCriteriaQuery.where(predicate)
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * pageable.pageSize >> PAGE_SIZE
		1 * baseTypedQuery.setMaxResults(PAGE_SIZE)
		1 * pageable.pageSize >> PAGE_SIZE
		1 * pageable.pageNumber >> PAGE_NUMBER
		1 * baseTypedQuery.setFirstResult(FIRST_RESULT)
		1 * entityManager.createQuery(countCriteriaQuery) >> countTypedQuery

		then: 'cacheable hint is set'
		1 * cachingStrategy.isCacheable(_) >> CACHEABLE
		1 * baseTypedQuery.setHint(QueryBuilder.HIBERNATE_CACHEABLE, CACHEABLE)
		1 * countTypedQuery.setHint(QueryBuilder.HIBERNATE_CACHEABLE, CACHEABLE)

		then: 'queries are executed'
		1 * baseTypedQuery.resultList >> baseEntityList
		1 * countTypedQuery.singleResult >> count

		then: 'page is returned'
		seriesPage.content == baseEntityList
		seriesPage.totalElements == count
		((PageImpl) seriesPage).pageable == pageable
	}

	void "all results are found"() {
		when: 'query builder is create'
		queryBuilder = new QueryBuilder<>(entityManager, cachingStrategy, baseClass, pageable)

		then: 'query builder is configured'
		1 * entityManager.getCriteriaBuilder() >> criteriaBuilder
		1 * criteriaBuilder.createQuery(Series) >> baseCriteriaQuery
		1 * baseCriteriaQuery.from(Series) >> baseRoot
		1 * baseCriteriaQuery.select(baseRoot)
		1 * criteriaBuilder.createQuery(Long) >> countCriteriaQuery
		1 * countCriteriaQuery.from(baseClass)
		1 * criteriaBuilder.count(baseRoot) >> countExpression
		1 * countCriteriaQuery.select(countExpression)
		1 * entityManager.getMetamodel() >> metamodel
		1 * metamodel.entity(baseClass) >> entityType
		1 * entityType.attributes >> attributeSet

		then: 'no other interactions are expected'
		0 * _

		then: 'query builder is returned'
		queryBuilder != null

		when: 'order is added and search is performed'
		queryBuilder.setSort(ORDER_REQUEST)
		List<Series> seriesList = queryBuilder.findAll()

		then: 'queries are build'
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_3) >> requestOrderClausePath3
		1 * criteriaBuilder.desc(requestOrderClausePath3) >> requestOrderClauseOrder3
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_2) >> requestOrderClausePath2
		1 * criteriaBuilder.asc(requestOrderClausePath2) >> requestOrderClauseOrder2
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_1) >> requestOrderClausePath1
		1 * criteriaBuilder.asc(requestOrderClausePath1) >> requestOrderClauseOrder1
		1 * baseCriteriaQuery.orderBy(_) >> { args ->
			List<Order> orderList = args[0]
			assert orderList[0] == requestOrderClauseOrder1
			assert orderList[1] == requestOrderClauseOrder2
			assert orderList[2] == requestOrderClauseOrder3
		}
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * pageable.pageSize >> PAGE_SIZE
		1 * baseTypedQuery.setMaxResults(PAGE_SIZE)
		1 * pageable.pageSize >> PAGE_SIZE
		1 * pageable.pageNumber >> PAGE_NUMBER
		1 * baseTypedQuery.setFirstResult(FIRST_RESULT)

		then: 'cacheable hint is set'
		1 * cachingStrategy.isCacheable(_) >> CACHEABLE
		1 * baseTypedQuery.setHint(QueryBuilder.HIBERNATE_CACHEABLE, CACHEABLE)

		then: 'query is executed'
		1 * baseTypedQuery.resultList >> baseEntityList

		then: 'all entities are found'
		seriesList == baseEntityList

		then: 'no other interactions are expected'
		0 * _
	}

}
