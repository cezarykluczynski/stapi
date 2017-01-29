package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Company as SOAPCompany
import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company as DBCompany
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanySoapMapperTest extends AbstractCompanyMapperTest {

	private CompanySoapMapper companySoapMapper

	void setup() {
		companySoapMapper = Mappers.getMapper(CompanySoapMapper)
	}

	void "maps SOAP CompanyRequest to CompanyRequestDTO"() {
		given:
		CompanyRequest companyRequest = new CompanyRequest(
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
		CompanyRequestDTO companyRequestDTO = companySoapMapper.map companyRequest

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

	void "maps DB entity to SOAP entity"() {
		given:
		DBCompany dBCompany = createCompany()

		when:
		SOAPCompany soapCompany = companySoapMapper.map(Lists.newArrayList(dBCompany))[0]

		then:
		soapCompany.guid == GUID
		soapCompany.name == NAME
		soapCompany.broadcaster == BROADCASTER
		soapCompany.collectibleCompany == COLLECTIBLE_COMPANY
		soapCompany.conglomerate == CONGLOMERATE
		soapCompany.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		soapCompany.distributor == DISTRIBUTOR
		soapCompany.gameCompany == GAME_COMPANY
		soapCompany.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		soapCompany.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		soapCompany.mattePaintingCompany == MATTE_PAINTING_COMPANY
		soapCompany.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		soapCompany.postProductionCompany == POST_PRODUCTION_COMPANY
		soapCompany.productionCompany == PRODUCTION_COMPANY
		soapCompany.propCompany == PROP_COMPANY
		soapCompany.recordLabel == RECORD_LABEL
		soapCompany.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		soapCompany.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		soapCompany.videoGameCompany == VIDEO_GAME_COMPANY
	}

}
