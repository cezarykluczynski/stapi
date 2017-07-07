package com.cezarykluczynski.stapi.model.video_release.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;

public interface VideoReleaseRepositoryCustom extends CriteriaMatcher<VideoReleaseRequestDTO, VideoRelease> {
}
