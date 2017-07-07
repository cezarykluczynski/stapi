package com.cezarykluczynski.stapi.model.video_release.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoReleaseRepository extends JpaRepository<VideoRelease, Long>, PageAwareRepository<VideoRelease>, VideoReleaseRepositoryCustom {
}
