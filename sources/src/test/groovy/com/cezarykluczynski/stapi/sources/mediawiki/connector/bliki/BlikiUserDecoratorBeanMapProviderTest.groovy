package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Maps
import spock.lang.Specification

class BlikiUserDecoratorBeanMapProviderTest extends Specification {

	private BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider

	def "map is set"() {
		given:
		UserDecorator memoryAlphaEnUserDecorator = Mock(UserDecorator)
		UserDecorator memoryBetaEnUserDecorator = Mock(UserDecorator)
		UserDecorator technicalHelperUserDecorator = Mock(UserDecorator)
		Map<String, UserDecorator> stringUserDecoratorMap = Maps.newHashMap()
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.MEMORY_ALPHA_EN_USER_DECORATOR, memoryAlphaEnUserDecorator)
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.MEMORY_BETA_EN_USER_DECORATOR, memoryBetaEnUserDecorator)
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.TECHNICAL_HELPER_USER_DECORATOR, technicalHelperUserDecorator)

		when:
		blikiUserDecoratorBeanMapProvider = new BlikiUserDecoratorBeanMapProvider(stringUserDecoratorMap)

		then:
		blikiUserDecoratorBeanMapProvider.getUserEnumMap().get(MediaWikiSource.MEMORY_ALPHA_EN) == memoryAlphaEnUserDecorator
		blikiUserDecoratorBeanMapProvider.getUserEnumMap().get(MediaWikiSource.MEMORY_BETA_EN) == memoryBetaEnUserDecorator
		blikiUserDecoratorBeanMapProvider.getUserEnumMap().get(MediaWikiSource.TECHNICAL_HELPER) == technicalHelperUserDecorator
	}

}
