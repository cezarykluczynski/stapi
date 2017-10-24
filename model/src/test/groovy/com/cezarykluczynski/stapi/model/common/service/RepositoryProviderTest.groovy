package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository
import com.google.common.collect.Maps
import org.hibernate.metadata.ClassMetadata
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.support.Repositories
import spock.lang.Specification

class RepositoryProviderTest extends Specification {

	private EntityMetadataProvider entityMetadataProviderMock

	private Repositories repositoriesMock

	private RepositoryProvider repositoryProvider

	void setup() {
		entityMetadataProviderMock = Mock()
		repositoriesMock = Mock()
		repositoryProvider = new RepositoryProvider(entityMetadataProviderMock, repositoriesMock)
	}

	void "provides list of repositories that's entities extend PageAwareEntity"() {
		given:
		SeriesRepository seriesRepository = Mock()
		SpeciesRepository speciesRepository = Mock()
		TradingCardRepository tradingCardRepository = Mock()
		Map<String, ClassMetadata> classNameToMetadataMap = Maps.newHashMap()
		ClassMetadata pageClassMetadata = Mock()
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.page.entity.Page', pageClassMetadata)
		ClassMetadata seriesClassMetadata = Mock()
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', seriesClassMetadata)
		ClassMetadata speciesClassMetadata = Mock()
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.species.entity.Species', speciesClassMetadata)
		ClassMetadata tradingCardsClassMetadata = Mock()
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard', tradingCardsClassMetadata)

		when:
		Map<Class<? extends PageAwareEntity>, CrudRepository> map = repositoryProvider.provide()

		then:
		1 * entityMetadataProviderMock.provideClassNameToMetadataMap() >> classNameToMetadataMap
		1 * pageClassMetadata.mappedClass >> Page
		1 * seriesClassMetadata.mappedClass >> Series
		1 * speciesClassMetadata.mappedClass >> Species
		1 * tradingCardsClassMetadata.mappedClass >> TradingCard
		1 * repositoriesMock.getRepositoryFor(Series) >> seriesRepository
		1 * repositoriesMock.getRepositoryFor(Species) >> speciesRepository
		1 * repositoriesMock.getRepositoryFor(TradingCard) >> tradingCardRepository
		0 * _
		map.size() == 3
		map.get(Series) == seriesRepository
		map.get(Species) == speciesRepository
		map.get(TradingCard) == tradingCardRepository

		when: 'another request is made'
		Map<Class<? extends PageAwareEntity>, CrudRepository> anotherMap = repositoryProvider.provide()

		then: 'cached version is returned'
		0 * _
		anotherMap == map
	}

}
