package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityRefreshingLookupByNameService;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper {

	private final EntityRefreshingLookupByNameService entityRefreshingLookupByNameService;

	public Set<Performer> linkListsToPerformers(Set<List<String>> source, MediaWikiSource mediaWikiSource) {
		Set<Performer> performerSet = Sets.newHashSet();

		source.forEach(lineList -> {
			if (CollectionUtils.isEmpty(lineList)) {
				return;
			}

			String line = lineList.get(0);

			Optional<Performer> performerOptional = entityRefreshingLookupByNameService.findPerformerByName(line, mediaWikiSource);
			if (performerOptional.isPresent()) {
				performerSet.add(performerOptional.get());
			} else {
				log.info("Performer not found by name \"{}\"", line);
			}
		});

		return performerSet;
	}

}
