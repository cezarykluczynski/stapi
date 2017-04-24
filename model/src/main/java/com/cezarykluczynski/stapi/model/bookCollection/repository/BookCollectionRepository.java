package com.cezarykluczynski.stapi.model.bookCollection.repository;

import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long>, PageAwareRepository<BookCollection> {
}
