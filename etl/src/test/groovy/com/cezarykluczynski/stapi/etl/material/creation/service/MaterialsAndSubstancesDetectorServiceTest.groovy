package com.cezarykluczynski.stapi.etl.material.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class MaterialsAndSubstancesDetectorServiceTest extends Specification {

	private static final String ALLOYS_AND_COMPOSITES_WIKITEXT = 'bla bla [[alloy|one]] bla'
	private static final String FUELS_WIKITEXT = 'some [[Fuel]] here'
	private static final String EXPLOSIVES_WIKITEXT = 'a [[explosive|boom boom]] thing'
	private static final String PRECIOUS_MATERIALS_WIKITEXT = 'such a [[Gold|great]] investment'

	private PageApi pageApiMock

	private MaterialsAndSubstancesDetectorService materialsAndSubstancesDetectorService

	void setup() {
		pageApiMock = Mock()
		materialsAndSubstancesDetectorService = new MaterialsAndSubstancesDetectorService(pageApiMock, new WikitextApiImpl())
	}

	void "parses page, then tells which page titles were found and which were not"() {
		given:
		PageSection alloysAndCompositesPageSection = of(MaterialsAndSubstancesDetectorService.ALLOYS_AND_COMPOSITES, ALLOYS_AND_COMPOSITES_WIKITEXT)
		PageSection fuelsPageSection = of(MaterialsAndSubstancesDetectorService.FUELS, FUELS_WIKITEXT)
		PageSection explosivesPageSection = of(MaterialsAndSubstancesDetectorService.EXPLOSIVES, EXPLOSIVES_WIKITEXT)
		PageSection preciousMaterialsPageSection = of(MaterialsAndSubstancesDetectorService.PRECIOUS_MATERIALS, PRECIOUS_MATERIALS_WIKITEXT)

		Page page = new Page(sections: Lists.newArrayList(new PageSection(), alloysAndCompositesPageSection, fuelsPageSection, explosivesPageSection,
				preciousMaterialsPageSection, new PageSection()))

		when:
		materialsAndSubstancesDetectorService.postConstruct()

		then:
		pageApiMock.getPage(PageTitle.MATERIALS_AND_SUBSTANCES, MediaWikiSource.MEMORY_ALPHA_EN) >> page

		then:
		materialsAndSubstancesDetectorService.isAlloyOrComposite('Alloy')
		materialsAndSubstancesDetectorService.isFuel('fuel')
		materialsAndSubstancesDetectorService.isExplosive('Explosive')
		materialsAndSubstancesDetectorService.isPreciousMaterial('gold')

		then:
		!materialsAndSubstancesDetectorService.isAlloyOrComposite('fuel')
		!materialsAndSubstancesDetectorService.isFuel('Alloy')
		!materialsAndSubstancesDetectorService.isExplosive('gold')
		!materialsAndSubstancesDetectorService.isPreciousMaterial('Explosive')
	}

	private static PageSection of(String text, String wikitext) {
		new PageSection(
				text: text,
				wikitext: wikitext)
	}

}
