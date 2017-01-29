package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Company as RESTCompany
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company as DBCompany
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanyRestMapperTest extends AbstractCompanyMapperTest {

	private CompanyRestMapper companyRestMapper

	void setup() {
		companyRestMapper = Mappers.getMapper(CompanyRestMapper)
	}

	void "maps CompanyRestBeanParams to CompanyRequestDTO"() {
		given:
		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams(
				guid: GUID,
				name: NAME,
				broadcaster: BROADCASTER,
				collectibleCompany: COLLECTIBLE_COMPANY,
				conglomerate: CONGLOMERATE,
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
				videoGameCompany: VIDEO_GAME_COMPANY)

		when:
		CompanyRequestDTO companyRequestDTO = companyRestMapper.map companyRestBeanParams

		then:
		companyRequestDTO.guid == GUID
		companyRequestDTO.name == NAME
		companyRequestDTO.broadcaster == BROADCASTER
		companyRequestDTO.collectibleCompany == COLLECTIBLE_COMPANY
		companyRequestDTO.conglomerate == CONGLOMERATE
		companyRequestDTO.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyRequestDTO.distributor == DISTRIBUTOR
		companyRequestDTO.gameCompany == GAME_COMPANY
		companyRequestDTO.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyRequestDTO.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyRequestDTO.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyRequestDTO.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyRequestDTO.postProductionCompany == POST_PRODUCTION_COMPANY
		companyRequestDTO.productionCompany == PRODUCTION_COMPANY
		companyRequestDTO.propCompany == PROP_COMPANY
		companyRequestDTO.recordLabel == RECORD_LABEL
		companyRequestDTO.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyRequestDTO.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyRequestDTO.videoGameCompany == VIDEO_GAME_COMPANY
	}

	void "maps DB entity to REST entity"() {
		given:
		DBCompany dBCompany = createCompany()

		when:
		RESTCompany restCompany = companyRestMapper.map(Lists.newArrayList(dBCompany))[0]

		then:
		restCompany.guid == GUID
		restCompany.name == NAME
		restCompany.broadcaster == BROADCASTER
		restCompany.collectibleCompany == COLLECTIBLE_COMPANY
		restCompany.conglomerate == CONGLOMERATE
		restCompany.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		restCompany.distributor == DISTRIBUTOR
		restCompany.gameCompany == GAME_COMPANY
		restCompany.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		restCompany.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		restCompany.mattePaintingCompany == MATTE_PAINTING_COMPANY
		restCompany.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		restCompany.postProductionCompany == POST_PRODUCTION_COMPANY
		restCompany.productionCompany == PRODUCTION_COMPANY
		restCompany.propCompany == PROP_COMPANY
		restCompany.recordLabel == RECORD_LABEL
		restCompany.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		restCompany.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		restCompany.videoGameCompany == VIDEO_GAME_COMPANY
	}

}
