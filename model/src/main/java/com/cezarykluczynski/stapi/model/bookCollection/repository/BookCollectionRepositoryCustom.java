package com.cezarykluczynski.stapi.model.bookCollection.repository;

import com.cezarykluczynski.stapi.model.bookCollection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface BookCollectionRepositoryCustom extends CriteriaMatcher<BookCollectionRequestDTO, BookCollection> {
}
