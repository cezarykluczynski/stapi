package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Maps
import spock.lang.Specification

class BlikiUserDecoratorBeanMapProviderTest extends Specification {

	private BlikiUserDecoratorBeanMapProvider blikiUserDecoratorBeanMapProvider

	void "map is set"() {
		given:
		UserDecorator memoryAlphaEnUserDecorator = Mock()
		UserDecorator memoryBetaEnUserDecorator = Mock()
		UserDecorator technicalHelperUserDecorator = Mock()
		Map<String, UserDecorator> stringUserDecoratorMap = Maps.newHashMap()
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.MEMORY_ALPHA_EN_USER_DECORATOR, memoryAlphaEnUserDecorator)
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.MEMORY_BETA_EN_USER_DECORATOR, memoryBetaEnUserDecorator)
		stringUserDecoratorMap.put(BlikiConnectorConfiguration.TECHNICAL_HELPER_USER_DECORATOR, technicalHelperUserDecorator)

		when:
		blikiUserDecoratorBeanMapProvider = new BlikiUserDecoratorBeanMapProvider(stringUserDecoratorMap)

		then:
		blikiUserDecoratorBeanMapProvider.userEnumMap.get(MediaWikiSource.MEMORY_ALPHA_EN) == memoryAlphaEnUserDecorator
		blikiUserDecoratorBeanMapProvider.userEnumMap.get(MediaWikiSource.MEMORY_BETA_EN) == memoryBetaEnUserDecorator
		blikiUserDecoratorBeanMapProvider.userEnumMap.get(MediaWikiSource.TECHNICAL_HELPER) == technicalHelperUserDecorator
	}

}
