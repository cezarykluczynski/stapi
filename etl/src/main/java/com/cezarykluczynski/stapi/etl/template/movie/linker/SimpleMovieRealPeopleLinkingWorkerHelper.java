package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class SimpleMovieRealPeopleLinkingWorkerHelper {

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
	public SimpleMovieRealPeopleLinkingWorkerHelper(EntityLookupByNameService entityLookupByNameService) {
		this.entityLookupByNameService = entityLookupByNameService;
	}

	public Set<Staff> linkListsToStaff(Set<List<String>> source, MediaWikiSource mediaWikiSource) {
		Set<Staff> staffSet = Sets.newHashSet();

		source.forEach(lineList ->
				lineList.forEach(line -> {
					Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(line, mediaWikiSource);
					if (staffOptional.isPresent()) {
						staffSet.add(staffOptional.get());
					} else {
						log.warn("Director not found by name {}", line);
					}
				})
		);

		return staffSet;
	}

}
