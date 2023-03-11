package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.client.rest.model.CompanyBase
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2Base
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class CompanyBaseRestMapperTest extends AbstractCompanyMapperTest {

	private CompanyBaseRestMapper companyBaseRestMapper

	void setup() {
		companyBaseRestMapper = Mappers.getMapper(CompanyBaseRestMapper)
	}

	void "maps CompanyRestBeanParams to CompanyRequestDTO"() {
		given:
		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams(
				uid: UID,
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
		CompanyRequestDTO companyRequestDTO = companyBaseRestMapper.mapBase companyRestBeanParams

		then:
		companyRequestDTO.uid == UID
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

	void "maps CompanyV2RestBeanParams to CompanyRequestDTO"() {
		given:
		CompanyV2RestBeanParams companyV2RestBeanParams = new CompanyV2RestBeanParams(
				uid: UID,
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

		when:
		CompanyRequestDTO companyRequestDTO = companyBaseRestMapper.mapV2Base companyV2RestBeanParams

		then:
		companyRequestDTO.uid == UID
		companyRequestDTO.name == NAME
		companyRequestDTO.broadcaster == BROADCASTER
		companyRequestDTO.streamingService == STREAMING_SERVICE
		companyRequestDTO.collectibleCompany == COLLECTIBLE_COMPANY
		companyRequestDTO.conglomerate == CONGLOMERATE
		companyRequestDTO.visualEffectsCompany == VISUAL_EFFECTS_COMPANY
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
		companyRequestDTO.publisher == PUBLISHER
		companyRequestDTO.publicationArtStudio == PUBLICATION_ART_STUDIO
	}

	void "maps DB entity to base REST entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyBase companyBase = companyBaseRestMapper.mapBase(Lists.newArrayList(company))[0]

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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Company company = createCompany()

		when:
		CompanyV2Base companyV2Base = companyBaseRestMapper.mapV2Base(Lists.newArrayList(company))[0]

		then:
		companyV2Base.uid == UID
		companyV2Base.name == NAME
		companyV2Base.broadcaster == BROADCASTER
		companyV2Base.streamingService == STREAMING_SERVICE
		companyV2Base.collectibleCompany == COLLECTIBLE_COMPANY
		companyV2Base.conglomerate == CONGLOMERATE
		companyV2Base.visualEffectsCompany == VISUAL_EFFECTS_COMPANY
		companyV2Base.digitalVisualEffectsCompany == DIGITAL_VISUAL_EFFECTS_COMPANY
		companyV2Base.distributor == DISTRIBUTOR
		companyV2Base.gameCompany == GAME_COMPANY
		companyV2Base.filmEquipmentCompany == FILM_EQUIPMENT_COMPANY
		companyV2Base.makeUpEffectsStudio == MAKE_UP_EFFECTS_STUDIO
		companyV2Base.mattePaintingCompany == MATTE_PAINTING_COMPANY
		companyV2Base.modelAndMiniatureEffectsCompany == MODEL_AND_MINIATURE_EFFECTS_COMPANY
		companyV2Base.postProductionCompany == POST_PRODUCTION_COMPANY
		companyV2Base.productionCompany == PRODUCTION_COMPANY
		companyV2Base.propCompany == PROP_COMPANY
		companyV2Base.recordLabel == RECORD_LABEL
		companyV2Base.specialEffectsCompany == SPECIAL_EFFECTS_COMPANY
		companyV2Base.tvAndFilmProductionCompany == TV_AND_FILM_PRODUCTION_COMPANY
		companyV2Base.videoGameCompany == VIDEO_GAME_COMPANY
		companyV2Base.publisher == PUBLISHER
		companyV2Base.publicationArtStudio == PUBLICATION_ART_STUDIO
	}

}
