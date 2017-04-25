package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.v1.soap.CompanyFull
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
import org.mapstruct.factory.Mappers

class CompanyFullSoapMapperTest extends AbstractCompanyMapperTest {

	private CompanyFullSoapMapper companyFullSoapMapper

	void setup() {
		companyFullSoapMapper = Mappers.getMapper(CompanyFullSoapMapper)
	}

	void "maps SOAP CompanyFullRequest to CompanyBaseRequestDTO"() {
		given:
		CompanyFullRequest companyFullRequest = new CompanyFullRequest(uid: UID)

		when:
		CompanyRequestDTO companyRequestDTO = companyFullSoapMapper.mapFull companyFullRequest

		then:
		companyRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyFull companyFull = companyFullSoapMapper.mapFull(company)

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
