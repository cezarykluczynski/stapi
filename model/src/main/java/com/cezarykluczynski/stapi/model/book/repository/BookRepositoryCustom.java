package com.cezarykluczynski.stapi.model.book.repository;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface BookRepositoryCustom extends CriteriaMatcher<BookRequestDTO, Book> {
}
