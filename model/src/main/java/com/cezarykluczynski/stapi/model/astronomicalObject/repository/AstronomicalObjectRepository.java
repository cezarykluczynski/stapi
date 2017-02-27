package com.cezarykluczynski.stapi.model.astronomicalObject.repository;

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AstronomicalObjectRepository extends JpaRepository<AstronomicalObject, Long>, AstronomicalObjectRepositoryCustom {

	Optional<AstronomicalObject> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<AstronomicalObject> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);

}
