package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.CompanyApi
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractCompanyTest

class CompanyTest extends AbstractCompanyTest {

	private CompanyApi companyApiMock

	private Company company

	void setup() {
		companyApiMock = Mock()
		company = new Company(companyApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		CompanyV2FullResponse companyV2FullResponse = Mock()

		when:
		CompanyV2FullResponse companyV2FullResponseOutput = company.getV2(UID)

		then:
		1 * companyApiMock.v2GetCompany(UID) >> companyV2FullResponse
		0 * _
		companyV2FullResponse == companyV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		CompanyV2BaseResponse companyV2BaseResponse = Mock()
		CompanyV2SearchCriteria companyV2SearchCriteria = new CompanyV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				broadcaster: BROADCASTER,
				streamingService: STREAMING_SERVICE,
				collectibleCompany: COLLECTIBLE_COMPANY,
				conglomerate: CONGLOMERATE,
				visualEffectsCompany: VISUAL_EFFECTS_COMPANY,
				digitalVisualEffectsCompany: DIGITAL_VISUAL_EFFECTS_COMPANY,
				distributor: DISTRIBUTOR,
				gameCompany: GAME_COMPANY,
				filmEquipmentCompany: FILM_EQUIPMENT_COMPANY,
				makeUpEffectsStudio: MAKE_UP_EFFECTS_STUDIO,
				mattePaintingCompany: MATTE_PAINTING_COMPANY,
				modelAndMiniatureEffectsCompany: MODEL_AND_MINIATURE_EFFECTS_COMPANY,
				postProductionCompany: POST_PRODUCTION_COMPANY,
				productionCompany: PRODUCTION_COMPANY,
				propCompany: PROP_COMPANY,
				recordLabel: RECORD_LABEL,
				specialEffectsCompany: SPECIAL_EFFECTS_COMPANY,
				tvAndFilmProductionCompany: TV_AND_FILM_PRODUCTION_COMPANY,
				videoGameCompany: VIDEO_GAME_COMPANY,
				publisher: PUBLISHER,
				publicationArtStudio: PUBLICATION_ART_STUDIO)
		companyV2SearchCriteria.sort = SORT

		when:
		CompanyV2BaseResponse companyV2BaseResponseOutput = company.searchV2(companyV2SearchCriteria)

		then:
		1 * companyApiMock.v2SearchCompanies(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, BROADCASTER, STREAMING_SERVICE,
				COLLECTIBLE_COMPANY, CONGLOMERATE, VISUAL_EFFECTS_COMPANY, DIGITAL_VISUAL_EFFECTS_COMPANY, DISTRIBUTOR, GAME_COMPANY,
				FILM_EQUIPMENT_COMPANY, MAKE_UP_EFFECTS_STUDIO, MATTE_PAINTING_COMPANY, MODEL_AND_MINIATURE_EFFECTS_COMPANY, POST_PRODUCTION_COMPANY,
				PRODUCTION_COMPANY, PROP_COMPANY, RECORD_LABEL, SPECIAL_EFFECTS_COMPANY, TV_AND_FILM_PRODUCTION_COMPANY, VIDEO_GAME_COMPANY,
				PUBLISHER, PUBLICATION_ART_STUDIO) >> companyV2BaseResponse
		0 * _
		companyV2BaseResponse == companyV2BaseResponseOutput
	}

}
