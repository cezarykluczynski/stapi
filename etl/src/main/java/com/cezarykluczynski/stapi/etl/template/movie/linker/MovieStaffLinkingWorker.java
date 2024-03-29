package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieStaffLinkingWorker implements MovieRealPeopleLinkingWorker {

	private final EntityLookupByNameService entityLookupByNameService;

	private final MovieExistingEntitiesHelper movieExistingEntitiesHelper;

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		Set<Staff> staffSet = Sets.newHashSet();

		source.forEach(lineList ->
			lineList.forEach(line -> {
				if (movieExistingEntitiesHelper.isKnownCompany(line)) {
					return;
				}

				Optional<Staff> staffOptional = entityLookupByNameService
						.findStaffByName(line, MovieRealPeopleLinkingWorker.SOURCE);
				if (staffOptional.isPresent()) {
					staffSet.add(staffOptional.get());
				} else {
					log.info("Staff not found by name \"{}\"", line);
				}
			})
		);

		baseEntity.getStaff().addAll(staffSet);
	}

}
