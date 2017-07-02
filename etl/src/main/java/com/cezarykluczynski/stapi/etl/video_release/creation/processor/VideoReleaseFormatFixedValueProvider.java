package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VideoReleaseFormatFixedValueProvider implements FixedValueProvider<String, VideoReleaseFormat> {

	private static final Map<String,VideoReleaseFormat> TITLES_TO_FORMATS = Maps.newHashMap();

	static {
		TITLES_TO_FORMATS.put("TOS-R Season 1 HD DVD", VideoReleaseFormat.DVD);
		TITLES_TO_FORMATS.put("To Be Takei", VideoReleaseFormat.DVD);
		TITLES_TO_FORMATS.put("William Shatner's Get A Life!", VideoReleaseFormat.DVD);
		TITLES_TO_FORMATS.put("The Captains", VideoReleaseFormat.DVD);
	}

	@Override
	public FixedValueHolder<VideoReleaseFormat> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_FORMATS.containsKey(key), TITLES_TO_FORMATS.get(key));
	}

}
