package com.cezarykluczynski.stapi.auth.api_key.mapper;

import com.cezarykluczynski.stapi.util.wrapper.Pager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PagerMapper {

	public <T> Pager map(Page<T> page) {
		return Pager.builder()
				.totalPages(page.getTotalPages())
				.totalElements((int) page.getTotalElements())
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.numberOfElementsInPage(page.getNumberOfElements())
				.isFirst(page.isFirst())
				.isLast(page.isLast())
				.hasNext(page.hasNext())
				.hasPrevious(page.hasPrevious())
				.build();
	}

}
