package com.cezarykluczynski.stapi.model.comic_strip.repository;

import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicStripRepository extends JpaRepository<ComicStrip, Long>, PageAwareRepository<ComicStrip>, ComicStripRepositoryCustom {
}
