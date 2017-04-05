package com.cezarykluczynski.stapi.model.common.query

import com.cezarykluczynski.stapi.model.series.entity.Series
import org.hibernate.jpa.criteria.CriteriaBuilderImpl
import org.hibernate.jpa.criteria.path.RootImpl
import spock.lang.Specification

import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.metamodel.EntityType

class CachingStrategyTest extends Specification {

	QueryBuilder<Series> queryBuilder

	CriteriaQuery<Series> criteriaQuery

	CriteriaBuilderImpl criteriaBuilder

	EntityType<Series> entityType

	RootImpl<Series> root

	Path path

	private CachingStrategy cachingStrategy

	void setup() {
		queryBuilder = Mock(QueryBuilder)
		criteriaQuery = Mock(CriteriaQuery)
		criteriaBuilder = Mock(CriteriaBuilderImpl)
		entityType = Mock(EntityType)
		path = Mock(Path)
		root = new RootImpl(criteriaBuilder, entityType)
		cachingStrategy = new CachingStrategy()
	}

	void "return true when guid is query"() {
		given:
		root.registerAttributePath('name', path)

		when:
		boolean cacheable = cachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		!cacheable
	}

	void "returns false when guid is not in query"() {
		given:
		root.registerAttributePath('guid', path)

		when:
		boolean cacheable = cachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		cacheable
	}

	void "returns false when attributePathRegistry is null"() {
		when:
		boolean cacheable = cachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		cacheable
	}

	void "returns false when attributePathRegistry could not be retrieved"() {
		when:
		boolean cacheable = cachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> Mock(RootImpl)
		0 * _
		!cacheable
	}

}
