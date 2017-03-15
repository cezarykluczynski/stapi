package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFull
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
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
		CompanyRequestDTO companyRequestDTO = companyRestMapper.mapBase companyRestBeanParams

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

	void "maps DB entity to base REST entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyBase companyBase = companyRestMapper.mapBase(Lists.newArrayList(company))[0]

		then:
		companyBase.guid == GUID
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

	void "maps DB entity to full REST entity"() {
		given:
		Company dBCompany = createCompany()

		when:
		CompanyFull companyFull = companyRestMapper.mapFull(dBCompany)

		then:
		companyFull.guid == GUID
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
