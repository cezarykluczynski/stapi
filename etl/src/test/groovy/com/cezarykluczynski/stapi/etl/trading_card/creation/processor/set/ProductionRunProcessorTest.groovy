package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetValueWithName
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit
import spock.lang.Specification
import spock.lang.Unroll

class ProductionRunProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProviderMock

	private ProductionRunProcessor productionRunProcessor

	void setup() {
		boxesPerCaseFixedValueProviderMock = Mock()
		productionRunProcessor = new ProductionRunProcessor(boxesPerCaseFixedValueProviderMock)
	}

	void "when null is passed, null is returned"() {
		when:
		ProductionRunDTO productionRunDTO = productionRunProcessor.process(null)

		then:
		0 * _
		productionRunDTO == null
	}

	void "when fixed value is found, it is returned"() {
		given:
		ProductionRunDTO productionRunDTO = Mock()

		when:
		ProductionRunDTO productionRunDTOOutput = productionRunProcessor.process(TradingCardSetValueWithName.of('', TITLE))

		then:
		1 * boxesPerCaseFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(productionRunDTO)
		0 * _
		productionRunDTOOutput == productionRunDTO
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, ProductionRunDTO is returned"() {
		given:
		boxesPerCaseFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.notFound()

		expect:
		productionRunProcessor.process(input) == output

		where:
		input                                                                               | output
		null                                                                                | null
		TradingCardSetValueWithName.of('Unknown Unknown', TITLE)                            | null
		TradingCardSetValueWithName.of('Various', TITLE)                                    | null
		TradingCardSetValueWithName.of('Unlimited', TITLE)                                  | null
		TradingCardSetValueWithName.of('Unknown', TITLE)                                    | null
		TradingCardSetValueWithName.of('3,613 / 10,000', TITLE)                             | ProductionRunDTO.of(13613, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('5,000', TITLE)                                      | ProductionRunDTO.of(5000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('20,000', TITLE)                                     | ProductionRunDTO.of(20000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('50,000', TITLE)                                     | ProductionRunDTO.of(50000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('250 Sets', TITLE)                                   | ProductionRunDTO.of(250, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('999 Sets', TITLE)                                   | ProductionRunDTO.of(999, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('50,000 Sets', TITLE)                                | ProductionRunDTO.of(50000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('150,000 Sets', TITLE)                               | ProductionRunDTO.of(150000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('12,000 Sets', TITLE)                                | ProductionRunDTO.of(12000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('730,000 Sets', TITLE)                               | ProductionRunDTO.of(730000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('5,000 Sets', TITLE)                                 | ProductionRunDTO.of(5000, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('200 Sets?', TITLE)                                  | ProductionRunDTO.of(200, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('6,000 Boxes', TITLE)                                | ProductionRunDTO.of(6000, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('16,000 Boxes', TITLE)                               | ProductionRunDTO.of(16000, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('23,000 Boxes', TITLE)                               | ProductionRunDTO.of(23000, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('2,151 Sets', TITLE)                                 | ProductionRunDTO.of(2151, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('< 350 Boxes', TITLE)                                | ProductionRunDTO.of(350, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('375 Boxes', TITLE)                                  | ProductionRunDTO.of(375, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('7,500 Boxes', TITLE)                                | ProductionRunDTO.of(7500, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('7,000 Boxes', TITLE)                                | ProductionRunDTO.of(7000, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('1,701 Packs', TITLE)                                | ProductionRunDTO.of(1701, ProductionRunUnit.SET)
		TradingCardSetValueWithName.of('6,500 US Boxes 2,500 International Boxes', TITLE)   | ProductionRunDTO.of(9000, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('6,000 U.S. Boxes 3,996 International Boxes', TITLE) | ProductionRunDTO.of(9996, ProductionRunUnit.BOX)
		TradingCardSetValueWithName.of('6,000 Boxes US 3,000 Boxes Non-US', TITLE)          | ProductionRunDTO.of(9000, ProductionRunUnit.BOX)
	}

}
