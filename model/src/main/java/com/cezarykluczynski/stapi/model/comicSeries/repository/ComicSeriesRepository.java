package com.cezarykluczynski.stapi.model.comicSeries.repository;

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicSeriesRepository extends JpaRepository<ComicSeries, Long>, ComicSeriesRepositoryCustom {

	Optional<ComicSeries> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

}
