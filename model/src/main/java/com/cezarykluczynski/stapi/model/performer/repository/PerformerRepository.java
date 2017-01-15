package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformerRepository extends JpaRepository<Performer, Long>, PerformerRepositoryCustom {

	Optional<Performer> findByPageTitleAndPageMediaWikiSource(String title, MediaWikiSource mediaWikiSource);

	Optional<Performer> findByPagePageIdAndPageMediaWikiSource(Long pageId, MediaWikiSource mediaWikiSource);
}
