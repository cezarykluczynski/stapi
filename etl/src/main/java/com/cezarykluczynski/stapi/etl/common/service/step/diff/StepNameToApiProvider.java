package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import com.cezarykluczynski.stapi.client.rest.StapiRestClient;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StepNameToApiProvider {

	private final Map<String, Field> stepPropertiesMap = Maps.newLinkedHashMap();

	public StepNameToApiProvider() {
		StapiRestClient stapiRestClient = new StapiRestClient();
		final Field[] declaredAccessibleFields = ReflectionUtil.getDeclaredAccessibleFields(stapiRestClient.getClass());
		Map<String, Field> fields = Arrays.stream(declaredAccessibleFields).collect(Collectors.toMap(Field::getName, Function.identity()));
		stepPropertiesMap.put(StepName.CREATE_COMPANIES, fields.get("company"));
		stepPropertiesMap.put(StepName.CREATE_SERIES, fields.get("series"));
		stepPropertiesMap.put(StepName.CREATE_SEASONS, fields.get("season"));
		stepPropertiesMap.put(StepName.CREATE_PERFORMERS, fields.get("performer"));
		stepPropertiesMap.put(StepName.CREATE_STAFF, fields.get("staff"));
		stepPropertiesMap.put(StepName.CREATE_ASTRONOMICAL_OBJECTS, fields.get("astronomicalObject"));
		stepPropertiesMap.put(StepName.LINK_ASTRONOMICAL_OBJECTS, null);
		stepPropertiesMap.put(StepName.CREATE_SPECIES, fields.get("species"));
		stepPropertiesMap.put(StepName.CREATE_ORGANIZATIONS, fields.get("organization"));
		stepPropertiesMap.put(StepName.CREATE_TITLES, fields.get("title"));
		stepPropertiesMap.put(StepName.CREATE_OCCUPATIONS, fields.get("occupation"));
		stepPropertiesMap.put(StepName.CREATE_CHARACTERS, fields.get("character"));
		stepPropertiesMap.put(StepName.LINK_CHARACTERS, null);
		stepPropertiesMap.put(StepName.CREATE_EPISODES, fields.get("episode"));
		stepPropertiesMap.put(StepName.CREATE_MOVIES, fields.get("movie"));
		stepPropertiesMap.put(StepName.CREATE_COMIC_SERIES, fields.get("comicSeries"));
		stepPropertiesMap.put(StepName.LINK_COMIC_SERIES, null);
		stepPropertiesMap.put(StepName.CREATE_COMICS, fields.get("comics"));
		stepPropertiesMap.put(StepName.CREATE_COMIC_STRIPS, fields.get("comicStrip"));
		stepPropertiesMap.put(StepName.CREATE_COMIC_COLLECTIONS, fields.get("comicCollection"));
		stepPropertiesMap.put(StepName.CREATE_FOODS, fields.get("food"));
		stepPropertiesMap.put(StepName.CREATE_LOCATIONS, fields.get("location"));
		stepPropertiesMap.put(StepName.CREATE_BOOK_SERIES, fields.get("bookSeries"));
		stepPropertiesMap.put(StepName.LINK_BOOK_SERIES, null);
		stepPropertiesMap.put(StepName.CREATE_BOOKS, fields.get("book"));
		stepPropertiesMap.put(StepName.CREATE_BOOK_COLLECTIONS, fields.get("bookCollection"));
		stepPropertiesMap.put(StepName.CREATE_MAGAZINE_SERIES, fields.get("magazineSeries"));
		stepPropertiesMap.put(StepName.CREATE_MAGAZINES, fields.get("magazine"));
		stepPropertiesMap.put(StepName.CREATE_LITERATURE, fields.get("literature"));
		stepPropertiesMap.put(StepName.CREATE_VIDEO_RELEASES, fields.get("videoRelease"));
		stepPropertiesMap.put(StepName.CREATE_TRADING_CARDS, fields.get("tradingCard"));
		stepPropertiesMap.put(StepName.CREATE_VIDEO_GAMES, fields.get("videoGame"));
		stepPropertiesMap.put(StepName.CREATE_SOUNDTRACKS, fields.get("soundtrack"));
		stepPropertiesMap.put(StepName.CREATE_WEAPONS, fields.get("weapon"));
		stepPropertiesMap.put(StepName.CREATE_TECHNOLOGY, fields.get("technology"));
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFT_TYPES, null);
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFT_CLASSES, fields.get("spacecraftClass"));
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFTS, fields.get("spacecraft"));
		stepPropertiesMap.put(StepName.CREATE_MATERIALS, fields.get("material"));
		stepPropertiesMap.put(StepName.CREATE_CONFLICTS, fields.get("conflict"));
		stepPropertiesMap.put(StepName.CREATE_ANIMALS, fields.get("animal"));
		stepPropertiesMap.put(StepName.CREATE_ELEMENTS, fields.get("element"));
		stepPropertiesMap.put(StepName.CREATE_MEDICAL_CONDITIONS, fields.get("medicalCondition"));
	}

	Field provide(String stepName) {
		return stepPropertiesMap.get(stepName);
	}

}
