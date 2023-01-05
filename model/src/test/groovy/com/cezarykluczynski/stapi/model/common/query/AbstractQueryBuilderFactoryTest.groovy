package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Sets
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.metamodel.EntityType
import jakarta.persistence.metamodel.Metamodel

class AbstractQueryBuilderFactoryTest extends Specification {

	private static class ConcreteQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

		ConcreteQueryBuilderFactory(JpaContext jpaContext) {
			super(jpaContext, Series)
		}

	}

	private static class ConcreteWithoutBaseClassQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

		ConcreteWithoutBaseClassQueryBuilderFactory(JpaContext jpaContext) {
			super(jpaContext, null)
		}

	}

	private JpaContext jpaContextMock

	private AbstractQueryBuilderFactory abstractQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		abstractQueryBuilderFactory = new ConcreteQueryBuilderFactory(jpaContextMock)
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
		new ConcreteQueryBuilderFactory(null)

		then:
		thrown(NullPointerException)
	}

}
