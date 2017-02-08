package com.cezarykluczynski.stapi.model.comics.repository;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComicsRepository extends JpaRepository<Comics, Long> {

	Optional<Comics> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<Comics> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
