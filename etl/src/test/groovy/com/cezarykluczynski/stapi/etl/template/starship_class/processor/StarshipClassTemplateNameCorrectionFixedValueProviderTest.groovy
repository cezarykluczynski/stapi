package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import spock.lang.Specification

class StarshipClassTemplateNameCorrectionFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Federation attack fighter'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private StarshipClassTemplateNameCorrectionFixedValueProvider starshipClassTemplateNameCorrectionFixedValueProvider

	void setup() {
		starshipClassTemplateNameCorrectionFixedValueProvider = new StarshipClassTemplateNameCorrectionFixedValueProvider()
	}

	void "provides correct name correction"() {
		expect:
		starshipClassTemplateNameCorrectionFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		starshipClassTemplateNameCorrectionFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'Attack fighter'
	}

	void "provides missing name correction"() {
		expect:
		!starshipClassTemplateNameCorrectionFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		starshipClassTemplateNameCorrectionFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
