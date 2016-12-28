package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MovieDirectorsLinkingWorker implements MovieRealPeopleLinkingWorker {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
	public MovieDirectorsLinkingWorker(EntityLookupByNameService entityLookupByNameService) {
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		Set<Staff> directors = baseEntity.getDirectors();

		source.forEach(lineList ->
			lineList.forEach(line -> {
				Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(line, SOURCE);
				if (staffOptional.isPresent()) {
					directors.add(staffOptional.get());
				} else {
					log.warn("Director not found by name {}", line);
				}
			})
		);
	}

}
