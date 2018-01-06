package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Sets
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.metamodel.EntityType
import javax.persistence.metamodel.Metamodel

class AbstractQueryBuilderFactoryTest extends Specification {

	private static class ConcreteQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

		ConcreteQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
			super(jpaContext, cachingStrategy, Series)
		}

	}

	private static class ConcreteWithoutBaseClassQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

		ConcreteWithoutBaseClassQueryBuilderFactory(JpaContext jpaContext) {
			super(jpaContext, null, null)
		}

	}

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private AbstractQueryBuilderFactory abstractQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
		abstractQueryBuilderFactory = new ConcreteQueryBuilderFactory(jpaContextMock, cachingStrategyMock)
	}

	void "QueryBuilderFactory is created"() {
		given:
		CriteriaQuery<Series> criteriaQuery = Mock()
		CriteriaQuery<Long> countCriteriaQuery = Mock()
		CriteriaBuilder criteriaBuilder = Mock()
		criteriaBuilder.createQuery(Series) >> criteriaQuery
		criteriaBuilder.createQuery(Long) >> countCriteriaQuery
		EntityManager entityManager = Mock()
		EntityType entityType = Mock()
		entityType.declaredAttributes >> Sets.newHashSet()
		Metamodel metamodel = Mock()
		metamodel.entity(Series) >> entityType
		entityManager.criteriaBuilder >> criteriaBuilder
		entityManager.metamodel >> metamodel
		Pageable pageable = Mock()

		when:
		QueryBuilder<Series> seriesQueryBuilder = abstractQueryBuilderFactory.createQueryBuilder(pageable)

		then:
		1 * jpaContextMock.getEntityManagerByManagedType(Series) >> entityManager
		seriesQueryBuilder != null
	}

	void "throws exception then entity manager is not set"() {
		given:
		Pageable pageable = Mock()

		when:
		abstractQueryBuilderFactory.createQueryBuilder(pageable)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when base class is not set"() {
		when:
		new ConcreteWithoutBaseClassQueryBuilderFactory(jpaContextMock)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when JPA context is not set"() {
		when:
		new ConcreteQueryBuilderFactory(null, cachingStrategyMock)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when CachingStrategy is not set"() {
		when:
		new ConcreteQueryBuilderFactory(jpaContextMock, null)

		then:
		thrown(NullPointerException)
	}

}
