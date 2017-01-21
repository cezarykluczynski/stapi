package com.cezarykluczynski.stapi.model.common.query

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

	private AbstractQueryBuilderFactory abstractQueryBuilerFactory

	void setup() {
		jpaContextMock = Mock(JpaContext)
		abstractQueryBuilerFactory = new ConcreteQueryBuilderFactory(jpaContextMock)
	}

	void "QueryBuilder is created"() {
		given:
		CriteriaQuery<Series> criteriaQuery = Mock(CriteriaQuery)
		CriteriaQuery<Long> countCriteriaQuery = Mock(CriteriaQuery)
		CriteriaBuilder criteriaBuilder = Mock(CriteriaBuilder) {
			createQuery(Series) >> criteriaQuery
			createQuery(Long) >> countCriteriaQuery
		}
		EntityManager entityManager = Mock(EntityManager)
		EntityType entityType = Mock(EntityType)
		entityType.declaredAttributes >> Sets.newHashSet()
		Metamodel metamodel = Mock(Metamodel)
		metamodel.entity(Series) >> entityType
		entityManager.criteriaBuilder >> criteriaBuilder
		entityManager.metamodel >> metamodel
		Pageable pageable = Mock(Pageable)

		when:
		QueryBuilder<Series> seriesQueryBuilder = abstractQueryBuilerFactory.createQueryBuilder(pageable)

		then:
		1 * jpaContextMock.getEntityManagerByManagedType(Series) >> entityManager
		seriesQueryBuilder != null
	}

	void "throws exception then entity manager is not set"() {
		given:
		Pageable pageable = Mock(Pageable)

		when:
		abstractQueryBuilerFactory.createQueryBuilder(pageable)

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
