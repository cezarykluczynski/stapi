package com.cezarykluczynski.stapi.model.video_game.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long>, PageAwareRepository<VideoGame>, VideoGameRepositoryCustom {
}
