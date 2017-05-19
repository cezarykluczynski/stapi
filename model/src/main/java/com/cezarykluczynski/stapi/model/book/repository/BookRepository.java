package com.cezarykluczynski.stapi.model.book.repository;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, PageAwareRepository<Book>, BookRepositoryCustom {
}
