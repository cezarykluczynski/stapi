package com.cezarykluczynski.stapi.model.book_collection.repository;

import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long>, PageAwareRepository<BookCollection>,
		BookCollectionRepositoryCustom {
}
