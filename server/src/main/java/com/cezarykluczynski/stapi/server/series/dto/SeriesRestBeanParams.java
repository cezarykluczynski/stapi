package com.cezarykluczynski.stapi.server.series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class SeriesRestBeanParams extends PageAwareBeanParams {

	@FormParam(value = "guid")
	private String guid;

	@FormParam(value = "title")
	private String title;

	@FormParam(value = "abbreviation")
	private String abbreviation;

	@FormParam(value = "productionStartYearFrom")
	private Integer productionStartYearFrom;

	@FormParam(value = "productionStartYearTo")
	private Integer productionStartYearTo;

	@FormParam(value = "productionEndYearFrom")
	private Integer productionEndYearFrom;

	@FormParam(value = "productionEndYearTo")
	private Integer productionEndYearTo;

	@FormParam(value = "originalRunStartDateFrom")
	private LocalDate originalRunStartDateFrom;

	@FormParam(value = "originalRunStartDateTo")
	private LocalDate originalRunStartDateTo;

	@FormParam(value = "originalRunEndDateFrom")
	private LocalDate originalRunEndDateFrom;

	@FormParam(value = "originalRunEndDateTo")
	private LocalDate originalRunEndDateTo;

	public String getGuid() {
		return guid;
	}

	public String getTitle() {
		return title;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public Integer getProductionStartYearFrom() {
		return productionStartYearFrom;
	}

	public Integer getProductionStartYearTo() {
		return productionStartYearTo;
	}

	public Integer getProductionEndYearFrom() {
		return productionEndYearFrom;
	}

	public Integer getProductionEndYearTo() {
		return productionEndYearTo;
	}

	public LocalDate getOriginalRunStartDateFrom() {
		return originalRunStartDateFrom;
	}

	public LocalDate getOriginalRunStartDateTo() {
		return originalRunStartDateTo;
	}

	public LocalDate getOriginalRunEndDateFrom() {
		return originalRunEndDateFrom;
	}

	public LocalDate getOriginalRunEndDateTo() {
		return originalRunEndDateTo;
	}

	public static SeriesRestBeanParams fromPageAwareBeanParams(PageAwareBeanParams pageAwareBeanParams) {
		if (pageAwareBeanParams == null) {
			return null;
		}

		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams();
		seriesRestBeanParams.setPageNumber(pageAwareBeanParams.getPageNumber());
		seriesRestBeanParams.setPageSize(pageAwareBeanParams.getPageSize());
		return seriesRestBeanParams;
	}

}
