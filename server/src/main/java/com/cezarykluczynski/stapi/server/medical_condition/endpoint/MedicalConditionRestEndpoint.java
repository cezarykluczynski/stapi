package com.cezarykluczynski.stapi.server.medical_condition.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams;
import com.cezarykluczynski.stapi.server.medical_condition.reader.MedicalConditionRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class MedicalConditionRestEndpoint {

	public static final String ADDRESS = "/v1/rest/medicalCondition";

	private final MedicalConditionRestReader medicalConditionRestReader;

	public MedicalConditionRestEndpoint(MedicalConditionRestReader medicalConditionRestReader) {
		this.medicalConditionRestReader = medicalConditionRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public MedicalConditionFullResponse getMedicalCondition(@QueryParam("uid") String uid) {
		return medicalConditionRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public MedicalConditionBaseResponse searchMedicalConditions(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return medicalConditionRestReader.readBase(MedicalConditionRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public MedicalConditionBaseResponse searchMedicalConditions(@BeanParam MedicalConditionRestBeanParams medicalConditionRestBeanParams) {
		return medicalConditionRestReader.readBase(medicalConditionRestBeanParams);
	}

}
