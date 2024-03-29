package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.rest.model.CompanyFull
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2Full
import com.cezarykluczynski.stapi.model.company.entity.Company
import org.mapstruct.factory.Mappers

class CompanyFullRestMapperTest extends AbstractCompanyMapperTest {

	private CompanyFullRestMapper companyFullRestMapper

	void setup() {
		companyFullRestMapper = Mappers.getMapper(CompanyFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Company dBCompany = createCompany()

		when:
		CompanyFull companyFull = companyFullRestMapper.mapFull(dBCompany)

		then:
		companyFull.uid == UID
		companyFull.name == NAME
		companyFull.broadcaster == BROADCASTER
		companyFull.collectibleCompany == COLLECTIBLE_COMPANY
		companyFull.conglomerate == CONGLOMERATE
		companyFull.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyFull.distributor == DISTRIBUTOR
		companyFull.gameCompany == GAME_COMPANY
		companyFull.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyFull.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyFull.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyFull.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyFull.postProductionCompany == POST_PRODUCTION_COMPANY
		companyFull.productionCompany == PRODUCTION_COMPANY
		companyFull.propCompany == PROP_COMPANY
		companyFull.recordLabel == RECORD_LABEL
		companyFull.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyFull.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyFull.videoGameCompany == VIDEO_GAME_COMPANY
	}

	void "maps DB entity to full REST V2 entity"() {
		given:
		Company dBCompany = createCompany()

		when:
		CompanyV2Full companyV2Full = companyFullRestMapper.mapV2Full(dBCompany)

		then:
		companyV2Full.uid == UID
		companyV2Full.name == NAME
		companyV2Full.broadcaster == BROADCASTER
		companyV2Full.streamingService == STREAMING_SERVICE
		companyV2Full.collectibleCompany == COLLECTIBLE_COMPANY
		companyV2Full.conglomerate == CONGLOMERATE
		companyV2Full.visualEffectsCompany == VISUAL_EFFECTS_COMPANY
		companyV2Full.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyV2Full.distributor == DISTRIBUTOR
		companyV2Full.gameCompany == GAME_COMPANY
		companyV2Full.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyV2Full.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyV2Full.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyV2Full.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyV2Full.postProductionCompany == POST_PRODUCTION_COMPANY
		companyV2Full.productionCompany == PRODUCTION_COMPANY
		companyV2Full.propCompany == PROP_COMPANY
		companyV2Full.recordLabel == RECORD_LABEL
		companyV2Full.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyV2Full.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyV2Full.videoGameCompany == VIDEO_GAME_COMPANY
		companyV2Full.publisher == PUBLISHER
		companyV2Full.publicationArtStudio == PUBLICATION_ART_STUDIO
	}

}
