package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.util.PageDefault;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PageMapper {

	public com.cezarykluczynski.stapi.client.rest.model.ResponsePage fromPageToRestResponsePage(Page pageRequest) {
		com.cezarykluczynski.stapi.client.rest.model.ResponsePage responsePage
				= new com.cezarykluczynski.stapi.client.rest.model.ResponsePage();
		responsePage.setPageNumber(pageRequest.getNumber());
		responsePage.setPageSize(pageRequest.getSize());
		responsePage.setNumberOfElements(pageRequest.getNumberOfElements());
		responsePage.setTotalElements(Long.valueOf(pageRequest.getTotalElements()).intValue());
		responsePage.setTotalPages(pageRequest.getTotalPages());
		responsePage.setFirstPage(pageRequest.isFirst());
		responsePage.setLastPage(pageRequest.isLast());
		return responsePage;
	}

	public PageRequest fromPageSortBeanParamsToPageRequest(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return PageDefault.PAGE_REQUEST;
		}
		return fromPageNumberAndPageSize(pageSortBeanParams.getPageNumber(), pageSortBeanParams.getPageSize());
	}

	public PageRequest getDefaultPageRequest() {
		return PageDefault.PAGE_REQUEST;
	}

	private static PageRequest fromPageNumberAndPageSize(Integer pageNumber, Integer pageSize) {
		int actualPageNumber = ObjectUtils.defaultIfNull(pageNumber, PageDefault.PAGE_NUMBER);
		actualPageNumber = actualPageNumber < 0 ? PageDefault.PAGE_NUMBER : actualPageNumber;
		int actualPageSize = ObjectUtils.defaultIfNull(pageSize, PageDefault.PAGE_SIZE);
		actualPageSize = actualPageSize <= 0 ? PageDefault.PAGE_SIZE : actualPageSize;
		actualPageSize = NumberUtil.ensureWithinRangeInclusive(actualPageSize, PageDefault.PAGE_SIZE_MIN, PageDefault.PAGE_SIZE_MAX);
		return PageRequest.of(actualPageNumber, actualPageSize);
	}

}
