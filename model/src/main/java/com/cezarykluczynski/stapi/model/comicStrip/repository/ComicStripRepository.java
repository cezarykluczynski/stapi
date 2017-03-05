package com.cezarykluczynski.stapi.model.comicStrip.repository;

import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicStripRepository extends JpaRepository<ComicStrip, Long>, PageAwareRepository<ComicStrip>, ComicStripRepositoryCustom {
}
