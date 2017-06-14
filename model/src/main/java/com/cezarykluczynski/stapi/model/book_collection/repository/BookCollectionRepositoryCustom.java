package com.cezarykluczynski.stapi.model.book_collection.repository;

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface BookCollectionRepositoryCustom extends CriteriaMatcher<BookCollectionRequestDTO, BookCollection> {
}
