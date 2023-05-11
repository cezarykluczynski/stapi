package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.service.EntityMetadataProvider
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.google.common.collect.Maps
import spock.lang.Specification

class CacheableClassForFullEntitiesSearchProviderTest extends Specification {

	EntityMetadataProvider entityMetadataProviderMock

	CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProvider

	void setup() {
		entityMetadataProviderMock = Mock()
		Map<String, String> classNameToMetadataMap = Maps.newHashMap()
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', 'SE')
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.species.entity.Species', 'SP')
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.staff.entity.Staff', 'ST')
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard', 'TC')
		entityMetadataProviderMock.provideClassNameToSymbolMap() >> classNameToMetadataMap
		cacheableClassForFullEntitiesSearchProvider = new CacheableClassForFullEntitiesSearchProvider(entityMetadataProviderMock)
	}

	void "list of entities is provided"() {
		when:
		List<Class> listOfEntities = cacheableClassForFullEntitiesSearchProvider.listOfEntities

		then:
		listOfEntities == [Species, Movie, AstronomicalObject, Character, Performer, Organization, Series, Staff]
	}

	void "cacheable entity is found"() {
		expect:
		cacheableClassForFullEntitiesSearchProvider.isClassCacheable(Character)
	}

	void "non-cacheable entity is found"() {
		expect:
		!cacheableClassForFullEntitiesSearchProvider.isClassCacheable(Title)
	}

}
