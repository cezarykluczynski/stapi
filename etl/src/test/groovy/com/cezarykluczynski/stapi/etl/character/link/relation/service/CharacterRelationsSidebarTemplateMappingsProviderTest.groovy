package com.cezarykluczynski.stapi.etl.character.link.relation.service

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class CharacterRelationsSidebarTemplateMappingsProviderTest extends Specification {

	private CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProvider

	void setup() {
		characterRelationsSidebarTemplateMappingsProvider = new CharacterRelationsSidebarTemplateMappingsProvider()
	}

	void "provides value for existing key"() {
		expect:
		characterRelationsSidebarTemplateMappingsProvider.provideFor(CharacterRelationCacheKey
				.of(TemplateTitle.SIDEBAR_HOLOGRAM, HologramTemplateParameter.CHILDREN)) == CharacterRelationName.CHILD
	}

	void "provides value for non-existing key"() {
		expect:
		characterRelationsSidebarTemplateMappingsProvider.provideFor(CharacterRelationCacheKey
				.of(TemplateTitle.SIDEBAR_STARSHIP_CLASS, StarshipClassTemplateParameter.SPEED)) == null
	}

}
