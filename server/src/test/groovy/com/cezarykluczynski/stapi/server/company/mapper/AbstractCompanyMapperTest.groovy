package com.cezarykluczynski.stapi.server.company.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.util.AbstractCompanyTest

abstract class AbstractCompanyMapperTest extends AbstractCompanyTest {

	protected static Company createCompany() {
		new Company(
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
	}

}
