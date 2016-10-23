package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.soap.RequestPage;
import com.cezarykluczynski.stapi.client.soap.ResponsePage;
import com.cezarykluczynski.stapi.server.util.PageDefault;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Mapper(componentModel = "spring")
public interface PageMapper {

	default ResponsePage toResponsePage(Page pageRequest) {
		ResponsePage responsePage = new ResponsePage();
		responsePage.setPageNumber(pageRequest.getNumber());
		responsePage.setPageSize(pageRequest.getSize());
		responsePage.setNumberOfElements(pageRequest.getNumberOfElements());
		responsePage.setTotalPages(pageRequest.getTotalPages());
		responsePage.setTotalElements(Long.valueOf(pageRequest.getTotalElements()).intValue());
		responsePage.setFirstPage(pageRequest.isFirst());
		responsePage.setLastPage(pageRequest.isLast());
		return responsePage;
	}

	default PageRequest toPageRequest(RequestPage requestPage) {
		if (requestPage == null) {
			return PageDefault.PAGE_REQUEST;
		}
		return new PageRequest(requestPage.getPageNumber(), requestPage.getPageSize());
	}

}
