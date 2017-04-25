package com.cezarykluczynski.stapi.server.series.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class SeriesRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("title")
	private String title;

	@FormParam("abbreviation")
	private String abbreviation;

	@FormParam("productionStartYearFrom")
	private Integer productionStartYearFrom;

	@FormParam("productionStartYearTo")
	private Integer productionStartYearTo;

	@FormParam("productionEndYearFrom")
	private Integer productionEndYearFrom;

	@FormParam("productionEndYearTo")
	private Integer productionEndYearTo;

	@FormParam("originalRunStartDateFrom")
	private LocalDate originalRunStartDateFrom;

	@FormParam("originalRunStartDateTo")
	private LocalDate originalRunStartDateTo;

	@FormParam("originalRunEndDateFrom")
	private LocalDate originalRunEndDateFrom;

	@FormParam("originalRunEndDateTo")
	private LocalDate originalRunEndDateTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public static SeriesRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams();
		seriesRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		seriesRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		seriesRestBeanParams.setSort(pageSortBeanParams.getSort());
		return seriesRestBeanParams;
	}

}
