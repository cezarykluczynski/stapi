package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AllStaffFindingMovieRealPeopleLinkingWorkerHelper {

	private final EntityLookupByNameService entityLookupByNameService;

	public AllStaffFindingMovieRealPeopleLinkingWorkerHelper(EntityLookupByNameService entityLookupByNameService) {
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
						log.info("Staff not found by name {}", line);
					}
				})
		);

		return staffSet;
	}

}
