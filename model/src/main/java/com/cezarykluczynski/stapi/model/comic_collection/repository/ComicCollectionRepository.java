package com.cezarykluczynski.stapi.model.comic_collection.repository;

import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicCollectionRepository extends JpaRepository<ComicCollection, Long>, PageAwareRepository<ComicCollection>,
		ComicCollectionRepositoryCustom {
}
