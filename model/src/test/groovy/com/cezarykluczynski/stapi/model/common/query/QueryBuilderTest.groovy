package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.*
import javax.persistence.metamodel.*
import java.time.LocalDate

class QueryBuilderTest extends Specification {

	private static final String VALID_VALUE_STRING = 'VALID_VALUE_STRING'
	private static final Boolean VALID_VALUE_BOOLEAN = LogicUtil.nextBoolean()
	private static final LocalDate VALID_VALUE_LOCAL_DATE_FROM = LocalDate.of(2000, 1, 2)
	private static final LocalDate VALID_VALUE_LOCAL_DATE_TO = LocalDate.of(2010, 3, 4)
	private static final Integer VALID_VALUE_INTEGER_FROM = 1970
	private static final Integer VALID_VALUE_INTEGER_TO = 2000
	private static final Float VALID_VALUE_FLOAT_FROM = (Float) 7.3
	private static final Float VALID_VALUE_FLOAT_TO = (Float) 8.1
	private static final String VALID_KEY_GENDER_STRING = 'VALID_KEY_GENDER'
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
	private final SingularAttribute<?, String> VALID_KEY_STRING = Mock(SingularAttribute)
	private final SingularAttribute<?, Boolean> VALID_KEY_BOOLEAN = Mock(SingularAttribute)
	private final SingularAttribute<?, Long> VALID_KEY_LONG = Mock(SingularAttribute)
	private final SetAttribute<?, ?> FETCH_SET_NAME = Mock(SetAttribute)
	private final SetAttribute<?, ?> FETCH_SINGULAR_NAME = Mock(SetAttribute)
	private final SingularAttribute<?, LocalDate> VALID_KEY_LOCAL_DATE = Mock(SingularAttribute)
	private final SingularAttribute<?, Integer> VALID_KEY_INTEGER = Mock(SingularAttribute)
	private final SingularAttribute<?, Float> VALID_KEY_FLOAT = Mock(SingularAttribute)
	private final SingularAttribute<?, Gender> VALID_KEY_GENDER = Mock(SingularAttribute)
	private final SingularAttribute<?, ?> KEY_WITH_INVALID_TYPE = Mock(SingularAttribute)
	private final String VALID_KEY_PAGE = 'page'
	private final String INVALID_KEY_PAGE = 'notPage'
	private final Path REQUEST_ORDER_CLAUSE_PATH_1 = Mock(Path)
	private final Path REQUEST_ORDER_CLAUSE_PATH_2 = Mock(Path)
	private final Path REQUEST_ORDER_CLAUSE_PATH_3 = Mock(Path)
	private final Order REQUEST_ORDER_CLAUSE_ORDER_3 = Mock(Order)
	private final Order REQUEST_ORDER_CLAUSE_ORDER_2 = Mock(Order)
	private final Order REQUEST_ORDER_CLAUSE_ORDER_1 = Mock(Order)

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

	private Root<Series> baseRoot

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

	def setup() {
		baseCriteriaQuery = Mock(CriteriaQuery)
		countCriteriaQuery = Mock(CriteriaQuery)
		criteriaBuilder = Mock(CriteriaBuilder)
		entityManager = Mock(EntityManager)
		baseRoot = Mock(Root)
		countExpression = Mock(Expression)
		metamodel = Mock(Metamodel)
		entityType = Mock(EntityType)
		attributeSet = Sets.newHashSet(
				Mock(Attribute) {
					getJavaType() >> String
					getName() >> VALID_KEY_STRING
				},
				Mock(Attribute) {
					getJavaType() >> Boolean
					getName() >> VALID_KEY_BOOLEAN
				},
				Mock(Attribute) {
					getJavaType() >> Long
					getName() >> VALID_KEY_LONG
				},
				Mock(Attribute) {
					getJavaType() >> LocalDate
					getName() >> VALID_KEY_LOCAL_DATE
				},
				Mock(Attribute) {
					getJavaType() >> Gender
					getName() >> VALID_KEY_GENDER
				},
				Mock(Attribute) {
					getJavaType() >> Long
					getName() >> KEY_WITH_INVALID_TYPE
				},
				Mock(Attribute) {
					getJavaType() >> com.cezarykluczynski.stapi.model.page.entity.Page
					getName() >> VALID_KEY_PAGE
				},
				Mock(Attribute) {
					getJavaType() >> Integer
					getName() >> VALID_KEY_INTEGER
				}
		)
		baseClass = Series
		pageable = Mock(Pageable)
		predicate = Mock(Predicate)
		baseTypedQuery = Mock(TypedQuery)
		countTypedQuery = Mock(TypedQuery)
		baseEntityList = Lists.newArrayList()
		path = Mock(Path)
		count = 7L
	}

	def "query builder is created, preconditions are added, then search is performed"() {
		when: 'query builder is create'
		queryBuilder = new QueryBuilder<>(entityManager, baseClass, pageable)

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
		1 * entityType.getAttributes() >> attributeSet

		then: 'no other interactions are expected'
		0 * _

		then: 'query builder is returned'
		queryBuilder != null

		when: 'valid string key is added for like comparison'
		queryBuilder.like(VALID_KEY_STRING, VALID_VALUE_STRING)

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_STRING) >> path
		1 * criteriaBuilder.like(path, "%${VALID_VALUE_STRING}%")

		when: 'valid string key is added to equal comparison'
		queryBuilder.equal(VALID_KEY_STRING, VALID_VALUE_STRING)

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_STRING) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_STRING)

		when: 'valid boolean key is added'
		queryBuilder.equal(VALID_KEY_BOOLEAN, VALID_VALUE_BOOLEAN)

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_BOOLEAN) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_BOOLEAN)

		when: 'valid long key is added'
		queryBuilder.equal(VALID_KEY_LONG, VALID_VALUE_LONG)

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_LONG) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_LONG)

		when: 'valid LocalDate range key is added'
		queryBuilder.between(VALID_KEY_LOCAL_DATE, VALID_VALUE_LOCAL_DATE_FROM, VALID_VALUE_LOCAL_DATE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_LOCAL_DATE_FROM, VALID_VALUE_LOCAL_DATE_TO)

		when: 'only start LocalDate is specified'
		queryBuilder.between(VALID_KEY_LOCAL_DATE, VALID_VALUE_LOCAL_DATE_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_LOCAL_DATE_FROM)

		when: 'only end LocalDate is specified'
		queryBuilder.between(VALID_KEY_LOCAL_DATE, null, VALID_VALUE_LOCAL_DATE_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_LOCAL_DATE_TO)

		when: 'valid Integer range key is added'
		queryBuilder.between(VALID_KEY_INTEGER, VALID_VALUE_INTEGER_FROM, VALID_VALUE_INTEGER_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_INTEGER_FROM, VALID_VALUE_INTEGER_TO)

		when: 'only start Integer is specified'
		queryBuilder.between(VALID_KEY_INTEGER, VALID_VALUE_INTEGER_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_INTEGER_FROM)

		when: 'only end Integer is specified'
		queryBuilder.between(VALID_KEY_INTEGER, null, VALID_VALUE_INTEGER_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_INTEGER_TO)

		when: 'valid Float range key is added'
		queryBuilder.between(VALID_KEY_FLOAT, VALID_VALUE_FLOAT_FROM, VALID_VALUE_FLOAT_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.between(_, VALID_VALUE_FLOAT_FROM, VALID_VALUE_FLOAT_TO)

		when: 'only start Float is specified'
		queryBuilder.between(VALID_KEY_FLOAT, VALID_VALUE_FLOAT_FROM, null)

		then: 'right methods are called'
		1 * criteriaBuilder.greaterThanOrEqualTo(_, VALID_VALUE_FLOAT_FROM)

		when: 'only end Float is specified'
		queryBuilder.between(VALID_KEY_FLOAT, null, VALID_VALUE_FLOAT_TO)

		then: 'right methods are called'
		1 * criteriaBuilder.lessThanOrEqualTo(_, VALID_VALUE_FLOAT_TO)

		when: 'valid gender key is added'
		queryBuilder.equal(VALID_KEY_GENDER, VALID_VALUE_GENDER)

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_GENDER) >> path
		1 * criteriaBuilder.equal(path, VALID_VALUE_GENDER)

		when: 'join equals key is added'
		queryBuilder.joinPageIdsIn(Sets.newHashSet(1L))

		then: 'right methods are called'
		1 * baseRoot.get(VALID_KEY_PAGE) >> path
		1 * path.get(VALID_JOIN_PAGE_ID) >> path
		1 * path.in(_)

		when: 'join equals key is added'
		queryBuilder.joinEquals(VALID_KEY_PAGE, VALID_KEY_GENDER_STRING, VALID_VALUE_GENDER, com.cezarykluczynski.stapi.model.page.entity.Page)

		then:
		1 * baseRoot.get(VALID_KEY_PAGE) >> path
		1 * path.get(VALID_KEY_GENDER_STRING) >> path
		1 * path.in(Lists.newArrayList(VALID_VALUE_GENDER))

		when: 'key with invalid type is added'
		queryBuilder.joinEquals(INVALID_KEY_PAGE, VALID_VALUE_STRING, VALID_VALUE_GENDER, com.cezarykluczynski.stapi.model.page.entity.Page)

		then: 'exception is thrown'
		thrown(RuntimeException)

		when: 'fetch is performed'
		queryBuilder.fetch(FETCH_SET_NAME)

		then: 'right methods are called'
		1 * baseRoot.fetch(FETCH_SET_NAME, JoinType.LEFT)

		when: 'fetch is performed with boolean flag set to true'
		queryBuilder.fetch(FETCH_SET_NAME, true)

		then: 'right methods are called'
		1 * baseRoot.fetch(FETCH_SET_NAME, JoinType.LEFT)

		when: 'fetch is performed with boolean flag set to false'
		queryBuilder.fetch(FETCH_SET_NAME, false)

		then: 'no fetch methods are called'
		0 * baseRoot.fetch(*_)

		when: 'singular fetch is performed'
		queryBuilder.fetch(FETCH_SINGULAR_NAME)

		then: 'singular set right methods are called'
		1 * baseRoot.fetch(FETCH_SINGULAR_NAME, JoinType.LEFT)


		when: 'order is added and search is performer'
		queryBuilder.setSort(ORDER_REQUEST)
		Page<Series> seriesPage = queryBuilder.findPage()

		then: 'queries are built'
		1 * criteriaBuilder.and(_) >> predicate
		1 * baseCriteriaQuery.where(predicate)
		1 * countCriteriaQuery.where(predicate)
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * pageable.getPageSize() >> PAGE_SIZE
		1 * baseTypedQuery.setMaxResults(PAGE_SIZE)
		1 * pageable.getPageSize() >> PAGE_SIZE
		1 * pageable.getPageNumber() >> PAGE_NUMBER
		1 * baseTypedQuery.setFirstResult(FIRST_RESULT)
		1 * entityManager.createQuery(countCriteriaQuery) >> countTypedQuery

		then: 'queries are executed'
		1 * baseTypedQuery.getResultList() >> baseEntityList
		1 * countTypedQuery.getSingleResult() >> count

		then: 'page is returned'
		seriesPage.content == baseEntityList
		seriesPage.totalElements == count
		((PageImpl) seriesPage).pageable == pageable

		when: 'all entities are to be found'
		List<Series> seriesList = queryBuilder.findAll()

		then: 'queries are build'
		1 * criteriaBuilder.and(_) >> predicate
		1 * baseCriteriaQuery.where(predicate)
		1 * countCriteriaQuery.where(predicate)
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_3) >> REQUEST_ORDER_CLAUSE_PATH_3
		1 * criteriaBuilder.desc(REQUEST_ORDER_CLAUSE_PATH_3) >> REQUEST_ORDER_CLAUSE_ORDER_3
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_2) >> REQUEST_ORDER_CLAUSE_PATH_2
		1 * criteriaBuilder.asc(REQUEST_ORDER_CLAUSE_PATH_2) >> REQUEST_ORDER_CLAUSE_ORDER_2
		1 * baseRoot.get(REQUEST_ORDER_CLAUSE_NAME_1) >> REQUEST_ORDER_CLAUSE_PATH_1
		1 * criteriaBuilder.asc(REQUEST_ORDER_CLAUSE_PATH_1) >> REQUEST_ORDER_CLAUSE_ORDER_1
		1 * baseCriteriaQuery.orderBy(_) >> { args ->
			List<Order> orderList = args[0]
			assert orderList[0] == REQUEST_ORDER_CLAUSE_ORDER_1
			assert orderList[1] == REQUEST_ORDER_CLAUSE_ORDER_2
			assert orderList[2] == REQUEST_ORDER_CLAUSE_ORDER_3
		}
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * pageable.getPageSize() >> PAGE_SIZE
		1 * baseTypedQuery.setMaxResults(PAGE_SIZE)
		1 * pageable.getPageSize() >> PAGE_SIZE
		1 * pageable.getPageNumber() >> PAGE_NUMBER
		1 * baseTypedQuery.setFirstResult(FIRST_RESULT)
		1 * baseTypedQuery.getResultList() >> baseEntityList

		then: 'all entities are found'
		seriesList == baseEntityList

		then: 'no other interactions are expected'
		0 * _
	}

}
