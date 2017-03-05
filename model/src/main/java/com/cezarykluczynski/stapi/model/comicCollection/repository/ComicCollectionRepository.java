package com.cezarykluczynski.stapi.model.comicCollection.repository;

import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicCollectionRepository extends JpaRepository<ComicCollection, Long>, PageAwareRepository<ComicCollection>,
		ComicCollectionRepositoryCustom {
}
