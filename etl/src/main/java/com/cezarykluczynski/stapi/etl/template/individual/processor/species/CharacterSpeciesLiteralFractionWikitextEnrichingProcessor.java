package com.cezarykluczynski.stapi.etl.template.individual.processor.species;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterSpeciesLiteralFractionWikitextEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Pair<String, List<PageLink>>, Set<CharacterSpecies>>> {

	static final Map<String, Fraction> FRACTIONS = Maps.newHashMap();

	static {
		FRACTIONS.put("&frac14;", Fraction.getFraction(1, 4));
		FRACTIONS.put("&frac12;", Fraction.getFraction(1, 2));
		FRACTIONS.put("&frac34;", Fraction.getFraction(3, 4));
	}

	private final CharacterSpeciesWithSpeciesNameEnrichingProcessor characterSpeciesWithSpeciesNameEnrichingProcessor;

	public CharacterSpeciesLiteralFractionWikitextEnrichingProcessor(
			CharacterSpeciesWithSpeciesNameEnrichingProcessor characterSpeciesWithSpeciesNameEnrichingProcessor) {
		this.characterSpeciesWithSpeciesNameEnrichingProcessor = characterSpeciesWithSpeciesNameEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Pair<String, List<PageLink>>, Set<CharacterSpecies>> enrichablePair) throws Exception {
		String wikitext = enrichablePair.getInput().getLeft();
		List<PageLink> pageLinkList = enrichablePair.getInput().getRight();
		Set<CharacterSpecies> characterSpeciesSet = enrichablePair.getOutput();

		FRACTIONS.forEach((key, fraction) -> {
			List<Integer> positionsList = StringUtil.getAllSubstringPositions(wikitext, key);

			positionsList.forEach(position -> {
				Optional<PageLink> firstPageLink = pageLinkList.stream()
						.filter(pageLink -> pageLink.getStartPosition() > position)
						.sorted(Comparator.comparing(PageLink::getStartPosition))
						.findFirst();

				firstPageLink.ifPresent(pageLink -> {
					try {
						characterSpeciesWithSpeciesNameEnrichingProcessor
								.enrich(EnrichablePair.of(Pair.of(firstPageLink.get().getTitle(), fraction), characterSpeciesSet));
					} catch (Exception e) {
						// do nothing
					}
				});
			});
		});

	}
}
