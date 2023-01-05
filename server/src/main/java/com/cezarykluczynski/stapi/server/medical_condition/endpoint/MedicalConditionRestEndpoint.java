package com.cezarykluczynski.stapi.server.medical_condition.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams;
import com.cezarykluczynski.stapi.server.medical_condition.reader.MedicalConditionRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

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
