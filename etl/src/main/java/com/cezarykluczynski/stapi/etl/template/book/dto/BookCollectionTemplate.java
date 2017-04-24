package com.cezarykluczynski.stapi.etl.template.book.dto;

import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookCollectionTemplate extends BookTemplate {

	private Set<Book> books = Sets.newHashSet();

}
