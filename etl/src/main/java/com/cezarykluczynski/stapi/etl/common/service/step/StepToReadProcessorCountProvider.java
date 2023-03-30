package com.cezarykluczynski.stapi.etl.common.service.step;

import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalReader;
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectReader;
import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader;
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader;
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionReader;
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesReader;
import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader;
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkReader;
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader;
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesReader;
import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripReader;
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsReader;
import com.cezarykluczynski.stapi.etl.common.processor.SizeAwareItemReader;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader;
import com.cezarykluczynski.stapi.etl.conflict.creation.processor.ConflictReader;
import com.cezarykluczynski.stapi.etl.element.creation.processor.ElementReader;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodReader;
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureReader;
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader;
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineReader;
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesReader;
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialReader;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionReader;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader;
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationReader;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonReader;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader;
import com.cezarykluczynski.stapi.etl.soundtrack.creation.processor.SoundtrackReader;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftReader;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassReader;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader;
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesReader;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader;
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyReader;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleReader;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetReader;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameReader;
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseReader;
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponReader;
import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StepToReadProcessorCountProvider {

	private static final Map<String, Class<? extends ItemReader>> STEP_TO_ITEM_READER_TYPE_MAP = Maps.newLinkedHashMap();

	private final ApplicationContext applicationContext;

	static {
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_COMPANIES, CompanyReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SERIES, SeriesReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SEASONS, SeasonReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_PERFORMERS, PerformerReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_STAFF, StaffReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_ASTRONOMICAL_OBJECTS, AstronomicalObjectReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SPECIES, SpeciesReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_ORGANIZATIONS, OrganizationReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_TITLES, TitleReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_OCCUPATIONS, OccupationReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_CHARACTERS, CharacterReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.LINK_CHARACTERS, CharacterLinkReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_EPISODES, EpisodeReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_MOVIES, MovieReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.LINK_ASTRONOMICAL_OBJECTS, AstronomicalObjectLinkReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_COMIC_SERIES, ComicSeriesReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.LINK_COMIC_SERIES, ComicSeriesLinkReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_COMICS, ComicsReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_COMIC_STRIPS, ComicStripReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_COMIC_COLLECTIONS, ComicCollectionReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_FOODS, FoodReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_LOCATIONS, LocationReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_BOOK_SERIES, BookSeriesReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.LINK_BOOK_SERIES, BookSeriesLinkReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_BOOKS, BookReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_BOOK_COLLECTIONS, BookCollectionReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_MAGAZINES, MagazineReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_MAGAZINE_SERIES, MagazineSeriesReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_LITERATURE, LiteratureReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_VIDEO_RELEASES, VideoReleaseReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_TRADING_CARDS, TradingCardSetReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_VIDEO_GAMES, VideoGameReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SOUNDTRACKS, SoundtrackReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_WEAPONS, WeaponReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SPACECRAFT_TYPES, SpacecraftTypeReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SPACECRAFT_CLASSES, SpacecraftClassReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_SPACECRAFTS, SpacecraftReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_MATERIALS, MaterialReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_CONFLICTS, ConflictReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_ANIMALS, AnimalReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_ELEMENTS, ElementReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_MEDICAL_CONDITIONS, MedicalConditionReader.class);
		STEP_TO_ITEM_READER_TYPE_MAP.put(StepName.CREATE_TECHNOLOGY, TechnologyReader.class);
	}

	public int getReadCountForStep(String stepName) {
		if (!STEP_TO_ITEM_READER_TYPE_MAP.containsKey(stepName)) {
			log.error("No entry for {}, returning read count 0.", stepName);
			return 0;
		}
		final Class<? extends ItemReader> itemReaderClass = STEP_TO_ITEM_READER_TYPE_MAP.get(stepName);
		if (ListItemReader.class.isAssignableFrom(itemReaderClass)) {
			ListItemReader listItemReader = (ListItemReader) applicationContext.getBean(itemReaderClass);
			// list in which items are stored only contains all of them at the very beginning,
			// so we're good as long as this method is called only in stepStarted handler
			try {
				return ReflectionUtil.getFieldValue(ListItemReader.class, listItemReader, "list", List.class).size();
			} catch (NoSuchFieldException | IllegalAccessException e) {
				log.error("List field could not be obtained for step {} and reader {}.", stepName, listItemReader, e);
			}
		} else if (RepositoryItemReader.class.isAssignableFrom(itemReaderClass)) {
			RepositoryItemReader repositoryItemReader = (RepositoryItemReader) applicationContext.getBean(itemReaderClass);
			try {
				PagingAndSortingRepository pagingAndSortingRepository = ReflectionUtil
						.getFieldValue(RepositoryItemReader.class, repositoryItemReader, "repository", PagingAndSortingRepository.class);
				if (pagingAndSortingRepository instanceof JpaRepository) {
					return (int) ((JpaRepository<?, ?>) pagingAndSortingRepository).count();
				}
				log.info("For step {}, repository {} not an instance of JpaRepository, returning 0.", stepName, pagingAndSortingRepository);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				log.error("Repository field could not be obtained for step {} and reader {}.", stepName, repositoryItemReader, e);
			}
		} else if (SizeAwareItemReader.class.isAssignableFrom(itemReaderClass)) {
			SizeAwareItemReader sizeAwareItemReader = (SizeAwareItemReader) applicationContext.getBean(itemReaderClass);
			return sizeAwareItemReader.getSize();
		} else {
			log.error("Entry for step {} was found, but no strategy to read from ItemReader was found, class was {}.", stepName, itemReaderClass);
		}
		return 0;
	}

}
