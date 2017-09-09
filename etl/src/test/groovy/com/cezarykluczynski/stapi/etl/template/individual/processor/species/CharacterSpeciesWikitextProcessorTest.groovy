package com.cezarykluczynski.stapi.etl.template.individual.processor.species

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.ImmutableMap
import com.google.common.collect.Lists
import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class CharacterSpeciesWikitextProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String WIKITEXT_WITH_FRACTIONS = '&frac34; [[Human]]<br>&frac14; [[Betazoid]]'
	private static final String WIKITEXT_HYBRID = 'WIKITEXT_HYBRID'
	private static final String INDIVIDUAL_NAME = 'INDIVIDUAL_NAME'
	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String WIKITEXT_FORMER = "${TITLE_1} former ${TITLE_2}"

	private WikitextApi wikitextApiMock

	private CharacterSpeciesFixedValueProvider characterSpeciesFixedValueProviderMock

	private CharacterSpeciesLiteralFractionWikitextEnrichingProcessor characterSpeciesLiteralFractionWikitextEnrichingProcessorMock

	private CharacterSpeciesWithSpeciesNameEnrichingProcessor characterSpeciesWithSpeciesNameEnrichingProcessorMock

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessor

	void setup() {
		characterSpeciesFixedValueProviderMock = Mock()
		wikitextApiMock = Mock()
		characterSpeciesLiteralFractionWikitextEnrichingProcessorMock = Mock()
		characterSpeciesWithSpeciesNameEnrichingProcessorMock = Mock()
		characterSpeciesWikitextProcessor = new CharacterSpeciesWikitextProcessor(characterSpeciesFixedValueProviderMock, wikitextApiMock,
				characterSpeciesLiteralFractionWikitextEnrichingProcessorMock, characterSpeciesWithSpeciesNameEnrichingProcessorMock)
	}

	void "returns empty set when page links list is empty"() {
		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT_FORMER, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_FORMER) >> Lists.newArrayList()
		0 * _
		characterSpeciesSet.empty
	}

	void "when fixed value is found, it is used"() {
		given:
		CharacterSpecies characterSpeciesFirst = Mock()
		CharacterSpecies characterSpeciesSecond = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.found(ImmutableMap.of(
				TITLE_1, Fraction.getFraction(1, 4),
				TITLE_2, Fraction.getFraction(3, 4)
		))
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_1
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 4
			enrichablePair.output.add characterSpeciesFirst
		}
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_2
			assert enrichablePair.input.right.numerator == 3
			assert enrichablePair.input.right.denominator == 4
			enrichablePair.output.add characterSpeciesSecond
		}
		0 * _
		characterSpeciesSet.contains characterSpeciesFirst
		characterSpeciesSet.contains characterSpeciesSecond
	}

	void "when single page link is used, it is passed to CharacterSpeciesWithSpeciesNameEnrichingProcessor"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE_1)
		CharacterSpecies characterSpecies = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_1
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 1
			enrichablePair.output.add characterSpecies
		}
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "when two page links are found, second one being gender, the first one is passed to CharacterSpeciesWithSpeciesNameEnrichingProcessor"() {
		given:
		PageLink pageLink = new PageLink(title: TITLE_1)
		PageLink pageLinkGender = new PageLink(title: RandomUtil.randomItem(Lists.newArrayList(CharacterSpeciesWikitextProcessor.GENDERS)))
		CharacterSpecies characterSpecies = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink, pageLinkGender)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
				assert enrichablePair.input.left == TITLE_1
				assert enrichablePair.input.right.numerator == 1
				assert enrichablePair.input.right.denominator == 1
				enrichablePair.output.add characterSpecies
		}
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "when fractions are found, CharacterSpeciesLiteralFractionWikitextEnrichingProcessor is used"() {
		given:
		PageLink pageLink1 = new PageLink(title: TITLE_1)
		PageLink pageLink2 = new PageLink(title: TITLE_2)
		CharacterSpecies characterSpecies = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT_WITH_FRACTIONS, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_WITH_FRACTIONS) >> Lists.newArrayList(pageLink1, pageLink2)
		1 * characterSpeciesLiteralFractionWikitextEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, List<PageLink>>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == WIKITEXT_WITH_FRACTIONS
			assert enrichablePair.input.right[0] == pageLink1
			assert enrichablePair.input.right[1] == pageLink2
			assert enrichablePair.output.add(characterSpecies)
		}
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "threats human augment as human"() {
		given:
		PageLink humanPageLink = new PageLink(title: CharacterSpeciesWikitextProcessor.HUMAN)
		PageLink augmentPageLink = new PageLink(title: CharacterSpeciesWikitextProcessor.AUGMENT)
		CharacterSpecies characterSpecies = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(humanPageLink, augmentPageLink)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == CharacterSpeciesWikitextProcessor.HUMAN
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 1
			enrichablePair.output.add characterSpecies
		}
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "when 'hybrid' string is found in wikitext, and but not in any of two links, both links are used to construct hybrid"() {
		given:
		PageLink humanPageLink = new PageLink(title: TITLE_1)
		PageLink augmentPageLink = new PageLink(title: TITLE_2)
		CharacterSpecies characterSpeciesOneHalf = Mock()
		CharacterSpecies characterSpeciesOtherHalf = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT_HYBRID, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_HYBRID) >> Lists.newArrayList(humanPageLink, augmentPageLink)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_1
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 2
			enrichablePair.output.add characterSpeciesOneHalf
		}
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_2
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 2
			enrichablePair.output.add characterSpeciesOtherHalf
		}
		0 * _
		characterSpeciesSet.contains characterSpeciesOneHalf
		characterSpeciesSet.contains characterSpeciesOtherHalf
	}

	void "when 'hybrid' string is found in one of the links, and there are three links, remaining two are used to construct hybrid"() {
		given:
		PageLink pageLink1 = new PageLink(title: TITLE_1)
		PageLink pageLink2 = new PageLink(title: TITLE_2)
		PageLink pageLinkHybrid = new PageLink(title: CharacterSpeciesWikitextProcessor.HYBRID)
		CharacterSpecies characterSpeciesOneHalf = Mock()
		CharacterSpecies characterSpeciesOtherHalf = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT_HYBRID, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_HYBRID) >> Lists.newArrayList(pageLink1,  pageLink2, pageLinkHybrid)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_1
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 2
			enrichablePair.output.add characterSpeciesOneHalf
		}
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
				assert enrichablePair.input.left == TITLE_2
				assert enrichablePair.input.right.numerator == 1
				assert enrichablePair.input.right.denominator == 2
				enrichablePair.output.add characterSpeciesOtherHalf
		}
		0 * _
		characterSpeciesSet.contains characterSpeciesOneHalf
		characterSpeciesSet.contains characterSpeciesOtherHalf
	}

	void "when 'Ardanan' is found among links, it is used ignoring other links"() {
		given:
		PageLink argananPageLink = new PageLink(title: CharacterSpeciesWikitextProcessor.ARDANAN)
		PageLink otherPageLink = new PageLink(title: TITLE_1)
		CharacterSpecies characterSpecies = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(argananPageLink, otherPageLink)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == CharacterSpeciesWikitextProcessor.ARDANAN
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 1
			enrichablePair.output.add characterSpecies
		}
		0 * _
		characterSpeciesSet.contains characterSpecies
	}

	void "former species is recognized, but ignored"() {
		given:
		PageLink pageLink1 = new PageLink(title: TITLE_1, startPosition: 10)
		PageLink pageLink2 = new PageLink(title: TITLE_2, startPosition: 50)
		CharacterSpecies characterSpeciesCurrent = Mock()

		when:
		Set<CharacterSpecies> characterSpeciesSet = characterSpeciesWikitextProcessor
				.process(Pair.of(WIKITEXT_FORMER, new CharacterTemplate(name: INDIVIDUAL_NAME)))

		then:
		1 * characterSpeciesFixedValueProviderMock.getSearchedValue(INDIVIDUAL_NAME) >> FixedValueHolder.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT_FORMER) >> Lists.newArrayList(pageLink1, pageLink2)
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
			assert enrichablePair.input.left == TITLE_1
			assert enrichablePair.input.right.numerator == 1
			assert enrichablePair.input.right.denominator == 1
			enrichablePair.output.add characterSpeciesCurrent
		}
		0 * _
		characterSpeciesSet.contains characterSpeciesCurrent
	}

}
