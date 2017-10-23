package com.cezarykluczynski.stapi.etl.food.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class FoodProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private FoodPageProcessor foodPageProcessorMock

	private FoodProcessor foodProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		foodPageProcessorMock = Mock()
		foodProcessor = new FoodProcessor(pageHeaderProcessorMock, foodPageProcessorMock)
	}

	void "converts PageHeader to Food"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Food food = new Food()

		when:
		Food foodOutput = foodProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * foodPageProcessorMock.process(page) >> food

		then: 'last processor output is returned'
		foodOutput == food
	}

}
