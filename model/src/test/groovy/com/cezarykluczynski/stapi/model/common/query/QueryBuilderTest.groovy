package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.*
import javax.persistence.metamodel.Attribute
import javax.persistence.metamodel.EntityType
import javax.persistence.metamodel.Metamodel

class QueryBuilderTest extends Specification {

	private static final String VALID_KEY = 'VALID_KEY'
	private static final String VALID_VALUE = 'VALID_VALUE'
	private static final String VALID_KEY_2 = 'VALID_KEY_2'
	private static final String VALID_VALUE_2 = 'VALID_VALUE_2'
	private static final String KEY_WITH_INVALID_TYPE = 'INVALID_KEY'
	private static final String KEY_NOT_IN_ATTRIBUTE_SET = 'KEY_NOT_IN_ATTRIBUTE_SET'
	private static final Integer PAGE_SIZE = 50
	private static final Integer PAGE_NUMBER = 5
	private static final Integer FIRST_RESULT = PAGE_SIZE * PAGE_NUMBER

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
					getJavaType() >> String.class
					getName() >> VALID_KEY
				},
				Mock(Attribute) {
					getJavaType() >> String.class
					getName() >> VALID_KEY_2
				},
				Mock(Attribute) {
					getJavaType() >> Long.class
					getName() >> KEY_WITH_INVALID_TYPE
				}
		)
		baseClass = Series
		pageable = Mock(Pageable)
		predicate = Mock(Predicate)
		baseTypedQuery = Mock(TypedQuery)
		countTypedQuery = Mock(TypedQuery)
		baseEntityList = Lists.newArrayList()
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
		1 * entityType.getDeclaredAttributes() >> attributeSet

		then: 'no other interactions are expected'
		0 * _

		then: 'query builder is returned'
		queryBuilder != null

		when: 'valid keys are added'
		queryBuilder.like(VALID_KEY, VALID_VALUE)
		queryBuilder.like(VALID_KEY_2, VALID_VALUE_2)

		then: 'no exception is thrown'
		notThrown(RuntimeException)

		when: 'key with invalid type is added'
		queryBuilder.like(KEY_WITH_INVALID_TYPE, VALID_VALUE)

		then: 'exception is thrown'
		thrown(RuntimeException)

		when: 'key that does not exists is added'
		queryBuilder.like(KEY_NOT_IN_ATTRIBUTE_SET, VALID_VALUE)

		then: 'exception is thrown'
		thrown(RuntimeException)

		when: 'search is performer'
		Page<Series> seriesPage = queryBuilder.search()

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
	}

}
