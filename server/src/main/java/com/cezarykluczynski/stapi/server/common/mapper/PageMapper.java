package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage;
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.util.PageDefault;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Mapper(config = MapstructConfiguration.class)
public interface PageMapper {

	default ResponsePage fromPageToSoapResponsePage(Page pageRequest) {
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

	default com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage fromPageToRestResponsePage(Page pageRequest) {
		com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage responsePage =
				new com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage();
		responsePage.setPageNumber(pageRequest.getNumber());
		responsePage.setPageSize(pageRequest.getSize());
		responsePage.setNumberOfElements(pageRequest.getNumberOfElements());
		responsePage.setTotalElements(Long.valueOf(pageRequest.getTotalElements()).intValue());
		responsePage.setTotalPages(pageRequest.getTotalPages());
		responsePage.setFirstPage(pageRequest.isFirst());
		responsePage.setLastPage(pageRequest.isLast());
		return responsePage;
	}

	default PageRequest fromRequestPageToPageRequest(RequestPage requestPage) {
		if (requestPage == null) {
			return PageDefault.PAGE_REQUEST;
		}
		return fromPageNumberAndPageSize(requestPage.getPageNumber(), requestPage.getPageSize());
	}

	default PageRequest fromPageSortBeanParamsToPageRequest(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return PageDefault.PAGE_REQUEST;
		}
		return fromPageNumberAndPageSize(pageSortBeanParams.getPageNumber(), pageSortBeanParams.getPageSize());
	}

	static PageRequest fromPageNumberAndPageSize(Integer pageNumber, Integer pageSize) {
		pageNumber = ObjectUtils.defaultIfNull(pageNumber, PageDefault.PAGE_NUMBER);
		pageNumber = pageNumber < 0 ? PageDefault.PAGE_NUMBER : pageNumber;
		pageSize = ObjectUtils.defaultIfNull(pageSize, PageDefault.PAGE_SIZE);
		pageSize = pageSize <= 0 ? PageDefault.PAGE_SIZE : pageSize;
		pageSize = NumberUtil.ensureWithinRangeInclusive(PageDefault.PAGE_SIZE_MIN, pageSize, PageDefault.PAGE_SIZE_MAX);
		return new PageRequest(pageNumber, pageSize);
	}

}
