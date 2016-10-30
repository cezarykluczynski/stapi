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

		public ConcreteQueryBuilderFactory(JpaContext jpaContext) {
			super(jpaContext, Series)
		}

	}

	private static class ConcreteWithoutBaseClassQueryBuilderFactory extends AbstractQueryBuilderFactory<Series> {

		public ConcreteWithoutBaseClassQueryBuilderFactory(JpaContext jpaContext) {
			super(jpaContext, null)
		}

	}

	private JpaContext jpaContextMock

	private AbstractQueryBuilderFactory abstractQueryBuilerFactory

	def setup() {
		jpaContextMock = Mock(JpaContext)
		abstractQueryBuilerFactory = new ConcreteQueryBuilderFactory(jpaContextMock)
	}

	def "QueryBuilder is created"() {
		given:
		CriteriaQuery<Series> criteriaQuery = Mock(CriteriaQuery)
		CriteriaQuery<Long> countCriteriaQuery = Mock(CriteriaQuery)
		CriteriaBuilder criteriaBuilder = Mock(CriteriaBuilder) {
			createQuery(Series) >> criteriaQuery
			createQuery(Long) >> countCriteriaQuery
		}
		EntityManager entityManager = Mock(EntityManager) {
			getCriteriaBuilder() >> criteriaBuilder
			getMetamodel() >> Mock(Metamodel) {
				entity(Series) >> Mock(EntityType) {
					getDeclaredAttributes() >> Sets.newHashSet()
				}
			}
		}
		Pageable pageable = Mock(Pageable)

		when:
		QueryBuilder<Series> seriesQueryBuilder = abstractQueryBuilerFactory.createQueryBuilder(pageable)

		then:
		1 * jpaContextMock.getEntityManagerByManagedType(Series) >> entityManager
		seriesQueryBuilder != null
	}

	def "throws exception then entity manager is not set"() {
		given:
		Pageable pageable = Mock(Pageable)

		when:
		abstractQueryBuilerFactory.createQueryBuilder(pageable)

		then:
		thrown(NullPointerException)
	}

	def "throws exception when base class is not set"() {
		when:
		new ConcreteWithoutBaseClassQueryBuilderFactory(jpaContextMock)

		then:
		thrown(NullPointerException)
	}

	def "throws exception when JPA context is not set"() {
		when:
		new ConcreteQueryBuilderFactory(null)

		then:
		thrown(NullPointerException)
	}

}
