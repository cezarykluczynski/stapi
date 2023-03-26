package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseBase;
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2Base;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonHeaderRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface VideoReleaseBaseRestMapper {

	@Mapping(source = "ITunesDigitalRelease", target = "itunesDigitalRelease")
	@Mapping(target = "season", ignore = true)
	@Mapping(target = "series", ignore = true)
	VideoReleaseBase mapBase(VideoRelease videoRelease);

	@Mapping(target = "documentary", ignore = true)
	@Mapping(target = "specialFeatures", ignore = true)
	VideoReleaseRequestDTO mapBase(VideoReleaseRestBeanParams videoReleaseRestBeanParams);

	List<VideoReleaseBase> mapBase(List<VideoRelease> videoReleaseList);

	@AfterMapping
	default void mapBase(VideoRelease videoRelease, @MappingTarget VideoReleaseBase videoReleaseBase) {
		videoReleaseBase.setSeason(videoRelease.getSeasons().stream()
				.min(Comparator.comparing(Season::getTitle))
				.map(season -> Mappers.getMapper(SeasonHeaderRestMapper.class).map(season))
				.orElse(null));

		videoReleaseBase.setSeries(videoRelease.getSeries().stream()
				.min(Comparator.comparing(Series::getTitle))
				.map(series -> Mappers.getMapper(SeriesHeaderRestMapper.class).map(series))
				.orElse(null));
	}

	@Mapping(source = "ITunesDigitalRelease", target = "itunesDigitalRelease")
	VideoReleaseV2Base mapV2Base(VideoRelease videoRelease);

	VideoReleaseRequestDTO mapV2Base(VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams);

	List<VideoReleaseV2Base> mapV2Base(List<VideoRelease> videoReleaseList);

}
