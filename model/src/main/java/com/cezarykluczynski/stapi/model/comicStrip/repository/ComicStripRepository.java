package com.cezarykluczynski.stapi.model.comicStrip.repository;

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicStripRepository extends JpaRepository<ComicStrip, Long> {

	Optional<ComicSeries> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

}
