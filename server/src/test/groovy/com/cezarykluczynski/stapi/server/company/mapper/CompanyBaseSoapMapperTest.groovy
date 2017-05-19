package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBase
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanyBaseSoapMapperTest extends AbstractCompanyMapperTest {

	private CompanyBaseSoapMapper companyBaseSoapMapper

	void setup() {
		companyBaseSoapMapper = Mappers.getMapper(CompanyBaseSoapMapper)
	}

	void "maps SOAP CompanyBaseRequest to CompanyRequestDTO"() {
		given:
		CompanyBaseRequest companyBaseRequest = new CompanyBaseRequest(
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
		CompanyRequestDTO companyRequestDTO = companyBaseSoapMapper.mapBase companyBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyBase companyBase = companyBaseSoapMapper.mapBase(Lists.newArrayList(company))[0]

		then:
		companyBase.uid == UID
		companyBase.name == NAME
		companyBase.broadcaster == BROADCASTER
		companyBase.collectibleCompany == COLLECTIBLE_COMPANY
		companyBase.conglomerate == CONGLOMERATE
		companyBase.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyBase.distributor == DISTRIBUTOR
		companyBase.gameCompany == GAME_COMPANY
		companyBase.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyBase.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyBase.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyBase.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyBase.postProductionCompany == POST_PRODUCTION_COMPANY
		companyBase.productionCompany == PRODUCTION_COMPANY
		companyBase.propCompany == PROP_COMPANY
		companyBase.recordLabel == RECORD_LABEL
		companyBase.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyBase.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyBase.videoGameCompany == VIDEO_GAME_COMPANY
	}

}
