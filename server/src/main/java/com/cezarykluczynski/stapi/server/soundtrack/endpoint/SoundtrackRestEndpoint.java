package com.cezarykluczynski.stapi.server.soundtrack.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.soundtrack.dto.SoundtrackRestBeanParams;
import com.cezarykluczynski.stapi.server.soundtrack.reader.SoundtrackRestReader;
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
public class SoundtrackRestEndpoint {

	public static final String ADDRESS = "/v1/rest/soundtrack";

	private final SoundtrackRestReader soundtrackRestReader;

	public SoundtrackRestEndpoint(SoundtrackRestReader soundtrackRestReader) {
		this.soundtrackRestReader = soundtrackRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public SoundtrackFullResponse getSoundtrack(@QueryParam("uid") String uid) {
		return soundtrackRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public SoundtrackBaseResponse searchSoundtrack(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return soundtrackRestReader.readBase(SoundtrackRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SoundtrackBaseResponse searchSoundtrack(@BeanParam SoundtrackRestBeanParams soundtrackRestBeanParams) {
		return soundtrackRestReader.readBase(soundtrackRestBeanParams);
	}

}
