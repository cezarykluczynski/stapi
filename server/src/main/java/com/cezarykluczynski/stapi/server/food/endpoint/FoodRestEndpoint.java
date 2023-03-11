package com.cezarykluczynski.stapi.server.food.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams;
import com.cezarykluczynski.stapi.server.food.reader.FoodRestReader;
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
