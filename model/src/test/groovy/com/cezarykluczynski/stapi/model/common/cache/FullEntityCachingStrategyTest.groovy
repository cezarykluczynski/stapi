package com.cezarykluczynski.stapi.model.common.cache

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.series.entity.Series
import org.hibernate.jpa.criteria.CriteriaBuilderImpl
import org.hibernate.jpa.criteria.path.RootImpl
import spock.lang.Specification

import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.metamodel.EntityType

class FullEntityCachingStrategyTest extends Specification {

	QueryBuilder<Series> queryBuilder

	CriteriaQuery<Series> criteriaQuery

	CriteriaBuilderImpl criteriaBuilder

	EntityType<Series> entityType

	RootImpl<Series> root

	Path path

	private FullEntityCachingStrategy fullEntityCachingStrategy

	void setup() {
		queryBuilder = Mock()
		criteriaQuery = Mock()
		criteriaBuilder = Mock()
		entityType = Mock()
		path = Mock()
		root = new RootImpl(criteriaBuilder, entityType)
		fullEntityCachingStrategy = new FullEntityCachingStrategy()
	}

	void "return true when uid is query"() {
		given:
		root.registerAttributePath('name', path)

		when:
		boolean cacheable = fullEntityCachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		!cacheable
	}

	void "returns false when uid is not in query"() {
		given:
		root.registerAttributePath('uid', path)

		when:
		boolean cacheable = fullEntityCachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		cacheable
	}

	void "returns false when attributePathRegistry is null"() {
		when:
		boolean cacheable = fullEntityCachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> root
		0 * _
		cacheable
	}

	void "returns false when attributePathRegistry could not be retrieved"() {
		given:
		RootImpl rootImpl = Mock()

		when:
		boolean cacheable = fullEntityCachingStrategy.isCacheable(queryBuilder)

		then:
		1 * queryBuilder.baseCriteriaQuery >> criteriaQuery
		1 * criteriaQuery.selection >> rootImpl
		0 * _
		!cacheable
	}

}
