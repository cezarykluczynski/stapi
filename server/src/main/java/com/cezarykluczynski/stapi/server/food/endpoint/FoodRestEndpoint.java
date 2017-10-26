package com.cezarykluczynski.stapi.server.food.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import com.cezarykluczynski.stapi.server.food.reader.FoodRestReader;
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
public class FoodRestEndpoint {

	public static final String ADDRESS = "/v1/rest/food";

	private final FoodRestReader foodRestReader;

	public FoodRestEndpoint(FoodRestReader foodRestReader) {
		this.foodRestReader = foodRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public FoodFullResponse getFood(@QueryParam("uid") String uid) {
		return foodRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public FoodBaseResponse searchFoods(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return foodRestReader.readBase(FoodRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public FoodBaseResponse searchFoods(@BeanParam FoodRestBeanParams foodRestBeanParams) {
		return foodRestReader.readBase(foodRestBeanParams);
	}

}
