package com.cezarykluczynski.stapi.model.video_game.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;

public interface VideoGameRepositoryCustom extends CriteriaMatcher<VideoGameRequestDTO, VideoGame> {
}
