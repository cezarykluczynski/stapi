package com.cezarykluczynski.stapi.model.episode.repository;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long>, EpisodeRepositoryCustom {
}
