package com.cezarykluczynski.stapi.etl.template.individual.processor.species

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.math.Fraction
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class CharacterSpeciesLiteralFractionWikitextEnrichingProcessorTest extends Specification {

	private static final String HUMAN = 'Human'
	private static final String BETAZOID = 'Betazoid'

	private CharacterSpeciesWithSpeciesNameEnrichingProcessor characterSpeciesWithSpeciesNameEnrichingProcessorMock

	private CharacterSpeciesLiteralFractionWikitextEnrichingProcessor characterSpeciesLiteralFractionWikitextEnrichingProcessor

	void setup() {
		characterSpeciesWithSpeciesNameEnrichingProcessorMock = Mock()
		characterSpeciesLiteralFractionWikitextEnrichingProcessor = new CharacterSpeciesLiteralFractionWikitextEnrichingProcessor(
				characterSpeciesWithSpeciesNameEnrichingProcessorMock)
	}

	void "passes found fraction to CharacterSpeciesWithSpeciesNameEnrichingProcessor"() {
		given:
		String wikitext = "&frac34; [[${HUMAN}]]<br>&frac14; [[${BETAZOID}]]"
		List<PageLink> pageLinkList = Lists.newArrayList(
				new PageLink(startPosition: 9, title: HUMAN),
				new PageLink(startPosition: 34, title: BETAZOID))
		Set<CharacterSpecies> characterSpeciesSet = Sets.newHashSet()
		Pair<String, List<PageLink>> pair = Pair.of(wikitext, pageLinkList)
		CharacterSpecies humanCharacterSpecies = Mock()
		CharacterSpecies betazoidCharacterSpecies = Mock()

		when:
		characterSpeciesLiteralFractionWikitextEnrichingProcessor.enrich(EnrichablePair.of(pair, characterSpeciesSet))

		then:
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
				assert enrichablePair.input.left == BETAZOID
				assert enrichablePair.input.right == Fraction.getFraction(1, 4)
				enrichablePair.output.add betazoidCharacterSpecies
		}
		1 * characterSpeciesWithSpeciesNameEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<String, Fraction>, Set<CharacterSpecies>> enrichablePair ->
				assert enrichablePair.input.left == HUMAN
				assert enrichablePair.input.right == Fraction.getFraction(3, 4)
				enrichablePair.output.add humanCharacterSpecies
		}
		0 * _
		characterSpeciesSet.contains humanCharacterSpecies
		characterSpeciesSet.contains betazoidCharacterSpecies
	}

}
