package com.cezarykluczynski.stapi.model.episode.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;

public interface EpisodeRepositoryCustom extends CriteriaMatcher<EpisodeRequestDTO, Episode> {
}
