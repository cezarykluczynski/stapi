package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.CompanyApi
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse
import com.cezarykluczynski.stapi.util.AbstractCompanyTest

class CompanyTest extends AbstractCompanyTest {

	private CompanyApi companyApiMock

	private Company company

	void setup() {
		companyApiMock = Mock()
		company = new Company(companyApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		CompanyFullResponse companyFullResponse = Mock()

		when:
		CompanyFullResponse companyFullResponseOutput = company.get(UID)

		then:
		1 * companyApiMock.companyGet(UID, API_KEY) >> companyFullResponse
		0 * _
		companyFullResponse == companyFullResponseOutput
	}

	void "searches entities"() {
		given:
		CompanyBaseResponse companyBaseResponse = Mock()

		when:
		CompanyBaseResponse companyBaseResponseOutput = company.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, BROADCASTER, COLLECTIBLE_COMPANY,
				CONGLOMERATE, DIGITAL_VISUAL_EFFECTS_COMPANY, DISTRIBUTOR, GAME_COMPANY, FILM_EQUIPMENT_COMPANY, MAKE_UP_EFFECTS_STUDIO,
				MATTE_PAINTING_COMPANY, MODEL_AND_MINIATURE_EFFECTS_COMPANY, POST_PRODUCTION_COMPANY, PRODUCTION_COMPANY, PROP_COMPANY, RECORD_LABEL,
				SPECIAL_EFFECTS_COMPANY, TV_AND_FILM_PRODUCTION_COMPANY, VIDEO_GAME_COMPANY)

		then:
		1 * companyApiMock.companySearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, BROADCASTER, COLLECTIBLE_COMPANY, CONGLOMERATE,
				DIGITAL_VISUAL_EFFECTS_COMPANY, DISTRIBUTOR, GAME_COMPANY, FILM_EQUIPMENT_COMPANY, MAKE_UP_EFFECTS_STUDIO, MATTE_PAINTING_COMPANY,
				MODEL_AND_MINIATURE_EFFECTS_COMPANY, POST_PRODUCTION_COMPANY, PRODUCTION_COMPANY, PROP_COMPANY, RECORD_LABEL, SPECIAL_EFFECTS_COMPANY,
				TV_AND_FILM_PRODUCTION_COMPANY, VIDEO_GAME_COMPANY) >> companyBaseResponse
		0 * _
		companyBaseResponse == companyBaseResponseOutput
	}

}
