package com.cezarykluczynski.stapi.etl.episode.creation.service;

import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthNameToMonthProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.hk.lua.Lua;
import com.hk.lua.LuaInterpreter;
import com.hk.lua.LuaObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleEpisodeDataProvider {

	private static final String EPISODE_SUFFIX = " (episode)";
	private static final String SLASH = "/";
	private static final String CLOSE_CURLY_BRACKET = "}";

	private static final List<String> PAGE_TITLES = Lists.newArrayList(
			PageTitle.MODULE_EPISODE_DATA_A,
			PageTitle.MODULE_EPISODE_DATA_D,
			PageTitle.MODULE_EPISODE_DATA_M,
			PageTitle.MODULE_EPISODE_DATA_N,
			PageTitle.MODULE_EPISODE_DATA_P,
			PageTitle.MODULE_EPISODE_DATA_Y
	);

	private final PageApi pageApi;
	private final MonthNameToMonthProcessor monthNameToMonthProcessor;

	private Map<String, Map<String, String>> outputs;

	public void initialize() {
		if (outputs == null) {
			loadPages();
		}
	}

	@SuppressWarnings({"CyclomaticComplexity", "NPathComplexity"})
	public ModuleEpisodeData provideDataFor(String title) {
		if (outputs == null) {
			throw new RuntimeException("`initialize` has to be called first!");
		}
		String secondTitle = title.endsWith(EPISODE_SUFFIX) ? title.replace(EPISODE_SUFFIX, "") : title + EPISODE_SUFFIX;
		ModuleEpisodeData moduleEpisodeData = doGet(title);
		ModuleEpisodeData moduleEpisodeDataSecondTitle = doGet(secondTitle);
		if (moduleEpisodeData == null && moduleEpisodeDataSecondTitle != null) {
			moduleEpisodeDataSecondTitle.setPageTitle(title);
			return moduleEpisodeDataSecondTitle;
		} else if (moduleEpisodeDataSecondTitle == null && moduleEpisodeData != null) {
			moduleEpisodeData.setPageTitle(title);
			return moduleEpisodeData;
		} else if (moduleEpisodeData == null) {
			return null;
		}
		moduleEpisodeData.setPageTitle(title.replace(EPISODE_SUFFIX, ""));
		if (moduleEpisodeDataSecondTitle.getSeries() != null && moduleEpisodeData.getSeries() == null) {
			moduleEpisodeData.setSeries(moduleEpisodeDataSecondTitle.getSeries());
		}
		if (moduleEpisodeDataSecondTitle.getSeasonNumber() != null && moduleEpisodeData.getSeasonNumber() == null) {
			moduleEpisodeData.setSeasonNumber(moduleEpisodeDataSecondTitle.getSeasonNumber());
		}
		if (moduleEpisodeDataSecondTitle.getEpisodeNumber() != null && moduleEpisodeData.getEpisodeNumber() == null) {
			moduleEpisodeData.setEpisodeNumber(moduleEpisodeDataSecondTitle.getEpisodeNumber());
		}
		if (moduleEpisodeDataSecondTitle.getReleaseDay() != null && moduleEpisodeData.getReleaseDay() == null) {
			moduleEpisodeData.setReleaseDay(moduleEpisodeDataSecondTitle.getReleaseDay());
		}
		if (moduleEpisodeDataSecondTitle.getReleaseMonth() != null && moduleEpisodeData.getReleaseMonth() == null) {
			moduleEpisodeData.setReleaseMonth(moduleEpisodeDataSecondTitle.getReleaseMonth());
		}
		if (moduleEpisodeDataSecondTitle.getReleaseYear() != null && moduleEpisodeData.getReleaseYear() == null) {
			moduleEpisodeData.setReleaseYear(moduleEpisodeDataSecondTitle.getReleaseYear());
		}
		if (moduleEpisodeDataSecondTitle.getProductionNumber() != null && moduleEpisodeData.getProductionNumber() == null) {
			moduleEpisodeData.setProductionNumber(moduleEpisodeDataSecondTitle.getProductionNumber());
		}
		return moduleEpisodeData;
	}

	@SneakyThrows
	@SuppressWarnings("ParameterAssignment")
	private ModuleEpisodeData doGet(String title) {
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData();
		title = title.toLowerCase(Locale.US);
		moduleEpisodeData.setSeries(getSafeString(PageTitle.MODULE_EPISODE_DATA_A, title));
		final String seasonAndEpisode = getSafeString(PageTitle.MODULE_EPISODE_DATA_N, title);
		if (seasonAndEpisode != null) {
			final String[] split = StringUtils.split(seasonAndEpisode, "x");
			moduleEpisodeData.setSeasonNumber(Ints.tryParse(split[0]));
			String episodeNumber = split[1];
			if (episodeNumber.contains(SLASH)) {
				episodeNumber = StringUtils.split(episodeNumber, SLASH)[0];
			}
			moduleEpisodeData.setEpisodeNumber(Ints.tryParse(episodeNumber));
		}
		moduleEpisodeData.setReleaseDay(getSafeInteger(PageTitle.MODULE_EPISODE_DATA_D, title));
		String month = getSafeString(PageTitle.MODULE_EPISODE_DATA_M, title);
		if (month != null) {
			final Month monthValue = monthNameToMonthProcessor.process(month);
			if (monthValue != null) {
				moduleEpisodeData.setReleaseMonth(monthValue.getValue());
			}
		}
		if (moduleEpisodeData.getReleaseMonth() == null) {
			log.info("No month value for episode {}, tried parsing {}.", title, month);
		}
		moduleEpisodeData.setReleaseYear(getSafeInteger(PageTitle.MODULE_EPISODE_DATA_Y, title));
		moduleEpisodeData.setProductionNumber(getSafeString(PageTitle.MODULE_EPISODE_DATA_P, title));
		if (moduleEpisodeData.getSeries() == null
				&& moduleEpisodeData.getSeasonNumber() == null
				&& moduleEpisodeData.getEpisodeNumber() == null
				&& moduleEpisodeData.getReleaseYear() == null
				&& moduleEpisodeData.getProductionNumber() == null) {
			return null;
		}
		return moduleEpisodeData;
	}

	private void loadPages() {
		outputs = Maps.newLinkedHashMap();
		for (String pageTitle : PAGE_TITLES) {
			Page page = pageApi.getPage(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN);
			String wikitext = page.getWikitext();
			String wikitextNoNewLines = Arrays.stream(wikitext.split("\n")).collect(Collectors.joining(""));
			String wikitextFixedEnd = wikitextNoNewLines.substring(0, wikitextNoNewLines.length() - 3) + CLOSE_CURLY_BRACKET;
			String wikitextFixedEnd2 = wikitextNoNewLines.substring(0, wikitextNoNewLines.length() - 2) + CLOSE_CURLY_BRACKET;
			final Set<Map.Entry<LuaObject, LuaObject>> entriesNoNewLines = tryGetEntries(wikitextNoNewLines);
			final Set<Map.Entry<LuaObject, LuaObject>> entriesFixedEnd = tryGetEntries(wikitextFixedEnd);
			final Set<Map.Entry<LuaObject, LuaObject>> entriesFixedEnd2 = tryGetEntries(wikitextFixedEnd2);
			outputs.put(pageTitle, Maps.newLinkedHashMap());
			final Map<String, String> singlePageOutput = outputs.get(pageTitle);
			add(singlePageOutput, entriesNoNewLines);
			add(singlePageOutput, entriesFixedEnd);
			add(singlePageOutput, entriesFixedEnd2);
		}
	}

	private String getSafeString(String modulePageTitle, String title) {
		final Map<String, String> output = outputs.get(modulePageTitle);
		if (output != null && output.containsKey(title)) {
			return output.get(title);
		}

		return null;
	}

	private Integer getSafeInteger(String modulePageTitle, String title) {
		final Map<String, String> output = outputs.get(modulePageTitle);
		if (output != null && output.containsKey(title)) {
			return Ints.tryParse(output.get(title));
		}

		return null;
	}

	private void add(Map<String, String> singlePageOutput, Set<Map.Entry<LuaObject, LuaObject>> entries) {
		for (Map.Entry<LuaObject, LuaObject> entry : entries) {
			singlePageOutput.put(entry.getKey().getString().toLowerCase(Locale.ROOT), entry.getValue().getString());
		}
	}

	private Set<Map.Entry<LuaObject, LuaObject>> tryGetEntries(String wikitextNoNewLines) {
		try {
			final LuaInterpreter luaInterpreter = Lua.reader(wikitextNoNewLines);
			luaInterpreter.compile();
			final LuaObject luaObject = luaInterpreter.execute();
			if (luaObject != null) {
				return luaObject.getEntries();
			} else {
				return Sets.newHashSet();
			}
		} catch (Exception e) {
			return Sets.newHashSet();
		}
	}

}
