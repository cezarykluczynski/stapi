package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFull;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2Full;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_language.mapper.ContentLanguageRestMapper;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingRestMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;

@Mapper(config = MapstructConfiguration.class, uses = {ContentLanguageRestMapper.class, ContentRatingRestMapper.class, DateMapper.class,
		EnumMapper.class, SeriesBaseRestMapper.class, SeriesBaseRestMapper.class})
public interface VideoReleaseFullRestMapper {

	@Mapping(source = "ITunesDigitalRelease", target = "iTunesDigitalRelease")
	@Mapping(target = "season", ignore = true)
	@Mapping(target = "series", ignore = true)
	VideoReleaseFull mapFull(VideoRelease videoRelease);

	@AfterMapping
	default void mapFull(VideoRelease videoRelease, @MappingTarget VideoReleaseFull videoReleaseFull) {
		videoReleaseFull.setSeason(videoRelease.getSeasons().stream()
				.min(Comparator.comparing(Season::getTitle))
				.map(season -> Mappers.getMapper(SeasonBaseRestMapper.class).mapBase(season))
				.orElse(null));

		videoReleaseFull.setSeries(videoRelease.getSeries().stream()
				.min(Comparator.comparing(Series::getTitle))
				.map(series -> Mappers.getMapper(SeriesBaseRestMapper.class).mapBase(series))
				.orElse(null));
	}

	@Mapping(source = "ITunesDigitalRelease", target = "iTunesDigitalRelease")
	VideoReleaseV2Full mapV2Full(VideoRelease videoRelease);

}
