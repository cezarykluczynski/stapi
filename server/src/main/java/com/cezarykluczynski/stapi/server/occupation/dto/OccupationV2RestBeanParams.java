package com.cezarykluczynski.stapi.server.occupation.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class OccupationV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("artsOccupation")
	private Boolean artsOccupation;

	@FormParam("communicationOccupation")
	private Boolean communicationOccupation;

	@FormParam("economicOccupation")
	private Boolean economicOccupation;

	@FormParam("educationOccupation")
	private Boolean educationOccupation;

	@FormParam("entertainmentOccupation")
	private Boolean entertainmentOccupation;

	@FormParam("illegalOccupation")
	private Boolean illegalOccupation;

	@FormParam("legalOccupation")
	private Boolean legalOccupation;

	@FormParam("medicalOccupation")
	private Boolean medicalOccupation;

	@FormParam("scientificOccupation")
	private Boolean scientificOccupation;

	@FormParam("sportsOccupation")
	private Boolean sportsOccupation;

	@FormParam("victualOccupation")
	private Boolean victualOccupation;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getArtsOccupation() {
		return artsOccupation;
	}

	public Boolean getCommunicationOccupation() {
		return communicationOccupation;
	}

	public Boolean getEconomicOccupation() {
		return economicOccupation;
	}

	public Boolean getEducationOccupation() {
		return educationOccupation;
	}

	public Boolean getEntertainmentOccupation() {
		return entertainmentOccupation;
	}

	public Boolean getIllegalOccupation() {
		return illegalOccupation;
	}

	public Boolean getLegalOccupation() {
		return legalOccupation;
	}

	public Boolean getMedicalOccupation() {
		return medicalOccupation;
	}

	public Boolean getScientificOccupation() {
		return scientificOccupation;
	}

	public Boolean getSportsOccupation() {
		return sportsOccupation;
	}

	public Boolean getVictualOccupation() {
		return victualOccupation;
	}

	public static OccupationV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		OccupationV2RestBeanParams occupationV2RestBeanParams = new OccupationV2RestBeanParams();
		occupationV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		occupationV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		occupationV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return occupationV2RestBeanParams;
	}

}
