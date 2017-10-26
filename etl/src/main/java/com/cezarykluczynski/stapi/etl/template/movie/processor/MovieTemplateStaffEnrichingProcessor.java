package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplateParameter;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieTemplateStaffEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<MovieTemplate> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	public MovieTemplateStaffEnrichingProcessor(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService) {
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<Template, MovieTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		MovieTemplate movieTemplate = enrichablePair.getOutput();
		Movie movieStub = movieTemplate.getMovieStub();

		StaffParsingState writersStaffParsingState = new StaffParsingState();
		StaffParsingState screenplayAuthorsStaffParsingState = new StaffParsingState();
		StaffParsingState storyAuthorsStaffParsingState = new StaffParsingState();
		StaffParsingState directorsStaffParsingState = new StaffParsingState();
		StaffParsingState producersStaffParsingState = new StaffParsingState();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case MovieTemplateParameter.WS_WRITTEN_BY:
					addAllStaff(writersStaffParsingState, value);
					break;
				case MovieTemplateParameter.WS_SCREENPLAY_BY:
					addAllStaff(screenplayAuthorsStaffParsingState, value);
					break;
				case MovieTemplateParameter.WS_STORY_BY:
					addAllStaff(storyAuthorsStaffParsingState, value);
					break;
				case MovieTemplateParameter.WS_DIRECTED_BY:
					addAllStaff(directorsStaffParsingState, value);
					break;
				case MovieTemplateParameter.WS_PRODUCED_BY:
					addAllStaff(producersStaffParsingState, value);
					break;
				default:
					break;
			}
		}

		propagateUnlinkedStaff(movieStub, writersStaffParsingState, screenplayAuthorsStaffParsingState,
				storyAuthorsStaffParsingState, directorsStaffParsingState, producersStaffParsingState);
		chooseMainDirector(directorsStaffParsingState, movieStub);
	}

	private void chooseMainDirector(StaffParsingState directorsStaffParsingState, Movie movieStub) {
		Set<Staff> directorSet = directorsStaffParsingState.getSupplementedStaffSet();
		if (directorSet.size() == 1) {
			movieStub.setMainDirector(directorSet.iterator().next());
		} else {
			log.error("Could not select main director, because directors set was {}", directorSet);
		}
	}

	private void addAllStaff(MovieTemplateStaffEnrichingProcessor.StaffParsingState staffParsingState, String value) {
		List<PageLink> linkTitleList = wikitextApi.getPageLinksFromWikitext(value);
		staffParsingState.setRawValue(value);

		staffParsingState.getInitialStaffPageLinkPair().addAll(linkTitleList
				.stream()
				.map(pageLink -> {
					Optional<Staff> staffOptional = entityLookupByNameService
							.findStaffByName(pageLink.getTitle(), SOURCE);
					if (staffOptional.isPresent()) {
						return Pair.of(staffOptional.get(), pageLink);
					}

					log.warn("Staff {} not found by name", pageLink);
					return null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet()));

		staffParsingState.setSupplementedStaffSet(staffParsingState.getInitialStaffPageLinkPair()
				.stream()
				.map(Pair::getLeft)
				.collect(Collectors.toSet()));
	}

	private void propagateUnlinkedStaff(Movie movieStub, StaffParsingState writersStaffParsingState,
			StaffParsingState screenplayAuthorsStaffParsingState, StaffParsingState storyAuthorsStaffParsingState,
			StaffParsingState directorsStaffParsingState, StaffParsingState producersStaffParsingState) {
		doPropagateUnlinedStaff(writersStaffParsingState, screenplayAuthorsStaffParsingState,
				storyAuthorsStaffParsingState, directorsStaffParsingState, producersStaffParsingState);
		doPropagateUnlinedStaff(screenplayAuthorsStaffParsingState, writersStaffParsingState,
				storyAuthorsStaffParsingState, directorsStaffParsingState, producersStaffParsingState);
		doPropagateUnlinedStaff(storyAuthorsStaffParsingState, writersStaffParsingState,
				screenplayAuthorsStaffParsingState, directorsStaffParsingState, producersStaffParsingState);
		doPropagateUnlinedStaff(directorsStaffParsingState, writersStaffParsingState,
				screenplayAuthorsStaffParsingState, storyAuthorsStaffParsingState, producersStaffParsingState);
		doPropagateUnlinedStaff(producersStaffParsingState, writersStaffParsingState,
				screenplayAuthorsStaffParsingState, storyAuthorsStaffParsingState, directorsStaffParsingState);

		movieStub.getWriters().addAll(writersStaffParsingState.getSupplementedStaffSet());
		movieStub.getScreenplayAuthors().addAll(screenplayAuthorsStaffParsingState.getSupplementedStaffSet());
		movieStub.getStoryAuthors().addAll(storyAuthorsStaffParsingState.getSupplementedStaffSet());
		movieStub.getDirectors().addAll(directorsStaffParsingState.getSupplementedStaffSet());
		movieStub.getProducers().addAll(producersStaffParsingState.getSupplementedStaffSet());
	}

	private void doPropagateUnlinedStaff(StaffParsingState staffParsingState, StaffParsingState... others) {
		if (staffParsingState.getRawValue() == null) {
			return;
		}

		for (MovieTemplateStaffEnrichingProcessor.StaffParsingState otherStaffParsingState : others) {
			for (Pair<Staff, PageLink> otherPair : otherStaffParsingState.getInitialStaffPageLinkPair()) {
				Staff otherStaff = otherPair.getLeft();
				PageLink otherPageLink = otherPair.getRight();
				Set<Staff> baseStaffSet = staffParsingState.getSupplementedStaffSet();
				String baseRawValue = staffParsingState.getRawValue();

				if (StringUtils.contains(baseRawValue, otherStaff.getName())) {
					log.info("Adding unlinked staff \"{}\" to set of {}, because raw value was \"{}\", name was: \"{}\"",
							otherStaff.getName(), toNameList(baseStaffSet), baseRawValue, otherStaff.getName());
					baseStaffSet.add(otherStaff);
				} else if (StringUtils.contains(baseRawValue, otherStaff.getBirthName())) {
					log.info("Adding unlinked staff \"{}\" to set of {}, because raw value was \"{}\", birth name was: \"{}\"",
							otherStaff.getName(), toNameList(baseStaffSet), baseRawValue, otherStaff.getBirthName());
					baseStaffSet.add(otherStaff);
				} else if (StringUtils.contains(baseRawValue, otherPageLink.getTitle())) {
					log.info("Adding unlinked staff \"{}\" to set of {}, because raw value was \"{}\", link title was: \"{}\"",
							otherStaff.getName(), toNameList(baseStaffSet), baseRawValue, otherPageLink.getTitle());
					baseStaffSet.add(otherStaff);
				} else if (StringUtils.contains(baseRawValue, otherPageLink.getDescription())) {
					log.info("Adding unlinked staff \"{}\" to set of {}, because raw value was \"{}\", link description was: \"{}\"",
							otherStaff.getName(), toNameList(baseStaffSet), baseRawValue, otherPageLink.getDescription());
					baseStaffSet.add(otherStaff);
				}
			}
		}
	}

	private List<String> toNameList(Set<Staff> staffSet) {
		return staffSet
				.stream()
				.map(Staff::getName)
				.collect(Collectors.toList());
	}

	@Data
	private static class StaffParsingState {

		private String rawValue;

		private Set<Pair<Staff, PageLink>> initialStaffPageLinkPair = Sets.newHashSet();

		private Set<Staff> supplementedStaffSet = Sets.newHashSet();

	}

}
