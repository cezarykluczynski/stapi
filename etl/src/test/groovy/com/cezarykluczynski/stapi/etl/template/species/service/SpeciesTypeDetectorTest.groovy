package com.cezarykluczynski.stapi.etl.template.species.service

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.template.species.processor.SpeciesTypeFixedValueProvider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesTypeDetectorTest extends Specification {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String DELTA_QUADRANT_SPECIES_WIKITEXT = 'DELTA_QUADRANT_SPECIES_WIKITEXT'
	private static final String EXTRA_GALACTIC_SPECIES_WIKITEXT = 'EXTRA_GALACTIC_SPECIES_WIKITEXT'
	private static final String GAMMA_QUADRANT_SPECIES_WIKITEXT = 'GAMMA_QUADRANT_SPECIES_WIKITEXT'
	private static final String HUMANOID_SPECIES_WIKITEXT = 'HUMANOID_SPECIES_WIKITEXT'
	private static final String NON_CORPOREAL_SPECIES_WIKITEXT = 'NON_CORPOREAL_SPECIES_WIKITEXT'
	private static final String SHAPESHIFTING_SPECIES_WIKITEXT = 'SHAPESHIFTING_SPECIES_WIKITEXT'
	private static final String SPACEBORNE_SPECIES_WIKITEXT = 'SPACEBORNE_SPECIES_WIKITEXT'
	private static final String TELEPATHIC_SPECIES_WIKITEXT = 'TELEPATHIC_SPECIES_WIKITEXT'
	private static final String TRANS_DIMENSIONAL_SPECIES_WIKITEXT = 'TRANS_DIMENSIONAL_SPECIES_WIKITEXT'
	private static final String DELTA_QUADRANT_SPECIES_TITLE = 'DELTA_QUADRANT_SPECIES_TITLE'
	private static final String EXTRA_GALACTIC_SPECIES_TITLE = 'EXTRA_GALACTIC_SPECIES_TITLE'
	private static final String GAMMA_QUADRANT_SPECIES_TITLE = 'GAMMA_QUADRANT_SPECIES_TITLE'
	private static final String HUMANOID_SPECIES_TITLE = 'HUMANOID_SPECIES_TITLE'
	private static final String NON_CORPOREAL_SPECIES_TITLE = 'NON_CORPOREAL_SPECIES_TITLE'
	private static final String SHAPESHIFTING_SPECIES_TITLE = 'SHAPESHIFTING_SPECIES_TITLE'
	private static final String SPACEBORNE_SPECIES_TITLE = 'SPACEBORNE_SPECIES_TITLE'
	private static final String TELEPATHIC_SPECIES_TITLE = 'TELEPATHIC_SPECIES_TITLE'
	private static final String TRANS_DIMENSIONAL_SPECIES_TITLE = 'TRANS_DIMENSIONAL_SPECIES_TITLE'
	private static final String SOME_OTHER_TITLE = 'SOME_OTHER_TITLE'
	private static final String WIKITEXT = 'WIKITEXT'
	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT_WITHOUT_TEMPLATES = 'WIKITEXT_WITHOUT_TEMPLATES'
	private static final String FIRST_PARAGRAPH = 'FIRST_PARAGRAPH'

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ParagraphExtractor paragraphExtractorMock

	private SpeciesTypeFixedValueProvider speciesTypeFixedValueProvider

	private SpeciesTypeDetector speciesTypeDetector

	void setup() {
		pageApiMock = Mock()
		wikitextApiMock = Mock()
		stepCompletenessDeciderMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		paragraphExtractorMock = Mock()
		speciesTypeFixedValueProvider = Mock()
		speciesTypeDetector = new SpeciesTypeDetector(pageApiMock, wikitextApiMock, stepCompletenessDeciderMock,
				categoryTitlesExtractingProcessorMock, paragraphExtractorMock, speciesTypeFixedValueProvider)
	}

	void "detector is initialized when CREATE_SPECIES step is not completed"() {
		given:
		Page deltaQuadrantSpeciesPage = new Page(wikitext: DELTA_QUADRANT_SPECIES_WIKITEXT)
		Page extraGalacticSpeciesPage = new Page(wikitext: EXTRA_GALACTIC_SPECIES_WIKITEXT)
		Page gammaQuadrantSpeciesPage = new Page(wikitext: GAMMA_QUADRANT_SPECIES_WIKITEXT)
		Page humanoidSpeciesPage = new Page(wikitext: HUMANOID_SPECIES_WIKITEXT)
		Page nonCorporealSpeciesPage = new Page(wikitext: NON_CORPOREAL_SPECIES_WIKITEXT)
		Page shapeshiftingSpeciesPage = new Page(wikitext: SHAPESHIFTING_SPECIES_WIKITEXT)
		Page spaceborneSpeciesPage = new Page(wikitext: SPACEBORNE_SPECIES_WIKITEXT)
		Page telepathicSpeciesPage = new Page(wikitext: TELEPATHIC_SPECIES_WIKITEXT)
		Page transDimensionalSpeciesPage = new Page(wikitext: TRANS_DIMENSIONAL_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES) >> false
		1 * pageApiMock.getPage(PageTitle.DELTA_QUADRANT_SPECIES, SOURCE) >> deltaQuadrantSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(DELTA_QUADRANT_SPECIES_WIKITEXT) >> Lists.newArrayList(DELTA_QUADRANT_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.EXTRA_GALACTIC_SPECIES, SOURCE) >> extraGalacticSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(EXTRA_GALACTIC_SPECIES_WIKITEXT) >> Lists.newArrayList(EXTRA_GALACTIC_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.GAMMA_QUADRANT_SPECIES, SOURCE) >> gammaQuadrantSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(GAMMA_QUADRANT_SPECIES_WIKITEXT) >> Lists.newArrayList(GAMMA_QUADRANT_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.HUMANOID_SPECIES, SOURCE) >> humanoidSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(HUMANOID_SPECIES_WIKITEXT) >> Lists.newArrayList(HUMANOID_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.NON_CORPOREAL_SPECIES, SOURCE) >> nonCorporealSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(NON_CORPOREAL_SPECIES_WIKITEXT) >> Lists.newArrayList(NON_CORPOREAL_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.SHAPESHIFTING_SPECIES, SOURCE) >> shapeshiftingSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(SHAPESHIFTING_SPECIES_WIKITEXT) >> Lists.newArrayList(SHAPESHIFTING_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.SPACEBORNE_SPECIES, SOURCE) >> spaceborneSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(SPACEBORNE_SPECIES_WIKITEXT) >> Lists.newArrayList(SPACEBORNE_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.TELEPATHIC_SPECIES, SOURCE) >> telepathicSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(TELEPATHIC_SPECIES_WIKITEXT) >> Lists.newArrayList(TELEPATHIC_SPECIES_TITLE)
		1 * pageApiMock.getPage(PageTitle.TRANS_DIMENSIONAL_SPECIES, SOURCE) >> transDimensionalSpeciesPage
		1 * wikitextApiMock.getPageTitlesFromWikitext(TRANS_DIMENSIONAL_SPECIES_WIKITEXT) >> Lists.newArrayList(TRANS_DIMENSIONAL_SPECIES_TITLE)
	}

	void "detector is not initialized when CREATE_SPECIES step is completed"() {
		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPECIES) >> true
		0 * _
	}

	void "Delta Quadrant species pages are recognized"() {
		given:
		Page page = new Page(wikitext: DELTA_QUADRANT_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.DELTA_QUADRANT_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(DELTA_QUADRANT_SPECIES_WIKITEXT) >> Lists.newArrayList(DELTA_QUADRANT_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isDeltaQuadrantSpecies(new Page(title: DELTA_QUADRANT_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isDeltaQuadrantSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "warp-capable species is recognized"() {
		given:
		Page page = new Page(wikitext: WIKITEXT, title: TITLE)

		when:
		boolean positiveResultWarpCapable = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(title: PageTitle.WARP_CAPABLE))
		positiveResultWarpCapable

		when:
		boolean positiveResultWarpDriveWithDesription = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(
				title: PageTitle.WARP_DRIVE,
				description: PageTitle.WARP_CAPABLE))
		positiveResultWarpDriveWithDesription

		when:
		boolean positiveResultTrueFixedValue = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.found(true)
		positiveResultTrueFixedValue

		when:
		boolean negativeResultDifferentLink = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(title: PageTitle.DELTA_QUADRANT))
		!negativeResultDifferentLink

		when:
		boolean negativeResultNotEmptyLinkDescription = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(
				title: PageTitle.WARP_CAPABLE,
				description: 'non-empty'))
		!negativeResultNotEmptyLinkDescription

		when:
		boolean negativeResultWarpDriveWithEmptyDescription = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(
				title: PageTitle.WARP_DRIVE))
		!negativeResultWarpDriveWithEmptyDescription

		when:
		boolean negativeResultNoParagraphs = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('')
		1 * wikitextApiMock.getPageLinksFromWikitext('') >> Lists.newArrayList()
		!negativeResultNoParagraphs

		when:
		boolean negativeResultFalseFixedValue = speciesTypeDetector.isWarpCapableSpecies(page)

		then:
		1 * speciesTypeFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.found(false)
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('')
		1 * wikitextApiMock.getPageLinksFromWikitext('') >> Lists.newArrayList()
		!negativeResultFalseFixedValue
	}

	void "extra-galactic species pages are recognized"() {
		given:
		Page page = new Page(wikitext: EXTRA_GALACTIC_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.EXTRA_GALACTIC_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(EXTRA_GALACTIC_SPECIES_WIKITEXT) >> Lists.newArrayList(EXTRA_GALACTIC_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isExtraGalacticSpecies(new Page(title: EXTRA_GALACTIC_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isExtraGalacticSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "Gamma Quadrant species pages are recognized"() {
		given:
		Page page = new Page(wikitext: GAMMA_QUADRANT_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.GAMMA_QUADRANT_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(GAMMA_QUADRANT_SPECIES_WIKITEXT) >> Lists.newArrayList(GAMMA_QUADRANT_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isGammaQuadrantSpecies(new Page(title: GAMMA_QUADRANT_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isGammaQuadrantSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "humanoid species pages are recognized"() {
		given:
		Page page = new Page(wikitext: HUMANOID_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.HUMANOID_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(HUMANOID_SPECIES_WIKITEXT) >> Lists.newArrayList(HUMANOID_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isHumanoidSpecies(new Page(title: HUMANOID_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isHumanoidSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "reptilian species are recognized"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)

		when:
		boolean positiveResult = speciesTypeDetector.isReptilianSpecies(page)

		then:
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(title: PageTitle.REPTILE))
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isReptilianSpecies(page)

		then:
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('', FIRST_PARAGRAPH, '')
		1 * wikitextApiMock.getPageLinksFromWikitext(FIRST_PARAGRAPH) >> Lists.newArrayList(new PageLink(title: PageTitle.COMICS))
		!negativeResult

		when:
		boolean negativeResultNoParagraphs = speciesTypeDetector.isReptilianSpecies(page)

		then:
		1 * wikitextApiMock.getWikitextWithoutTemplates(WIKITEXT) >> WIKITEXT_WITHOUT_TEMPLATES
		1 * paragraphExtractorMock.extractParagraphs(WIKITEXT_WITHOUT_TEMPLATES) >> Lists.newArrayList('')
		1 * wikitextApiMock.getPageLinksFromWikitext('') >> Lists.newArrayList()
		!negativeResultNoParagraphs
	}

	void "non-corporeal species pages are recognized"() {
		given:
		Page page = new Page(wikitext: NON_CORPOREAL_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.NON_CORPOREAL_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(NON_CORPOREAL_SPECIES_WIKITEXT) >> Lists.newArrayList(NON_CORPOREAL_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isNonCorporealSpecies(new Page(title: NON_CORPOREAL_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isNonCorporealSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "shapeshifting species pages are recognized"() {
		given:
		Page page = new Page(wikitext: SHAPESHIFTING_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.SHAPESHIFTING_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(SHAPESHIFTING_SPECIES_WIKITEXT) >> Lists.newArrayList(SHAPESHIFTING_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isShapeshiftingSpecies(new Page(title: SHAPESHIFTING_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isShapeshiftingSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "spaceborne species pages are recognized"() {
		given:
		Page page = new Page(wikitext: SPACEBORNE_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.SPACEBORNE_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(SPACEBORNE_SPECIES_WIKITEXT) >> Lists.newArrayList(SPACEBORNE_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isSpaceborneSpecies(new Page(title: SPACEBORNE_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isSpaceborneSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "telepathic species pages are recognized"() {
		given:
		Page page = new Page(wikitext: TELEPATHIC_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.TELEPATHIC_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(TELEPATHIC_SPECIES_WIKITEXT) >> Lists.newArrayList(TELEPATHIC_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isTelepathicSpecies(new Page(title: TELEPATHIC_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isTelepathicSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "trans-dimensional species pages are recognized"() {
		given:
		Page page = new Page(wikitext: TRANS_DIMENSIONAL_SPECIES_WIKITEXT)

		when:
		speciesTypeDetector.afterPropertiesSet()

		then:
		1 * pageApiMock.getPage(PageTitle.TRANS_DIMENSIONAL_SPECIES, SOURCE) >> page
		1 * wikitextApiMock.getPageTitlesFromWikitext(TRANS_DIMENSIONAL_SPECIES_WIKITEXT) >> Lists.newArrayList(TRANS_DIMENSIONAL_SPECIES_TITLE)

		when:
		boolean positiveResult = speciesTypeDetector.isTransDimensionalSpecies(new Page(title: TRANS_DIMENSIONAL_SPECIES_TITLE))

		then:
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isTransDimensionalSpecies(new Page(title: SOME_OTHER_TITLE))

		then:
		!negativeResult
	}

	void "unnamed species pages are recognized"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(categories: categoryHeaderList)
		speciesTypeDetector.afterPropertiesSet()

		when:
		boolean positiveResult = speciesTypeDetector.isUnnamedSpecies(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.UNNAMED_SPECIES)
		positiveResult

		when:
		boolean negativeResult = speciesTypeDetector.isUnnamedSpecies(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.SPECIES)
		!negativeResult
	}

}
