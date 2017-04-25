package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFull
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

}
