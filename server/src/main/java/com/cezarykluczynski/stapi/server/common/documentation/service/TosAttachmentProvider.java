package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.server.common.feature_switch.api.FeatureSwitchApi;
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchType;
import jakarta.ws.rs.core.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class TosAttachmentProvider {

	private final FeatureSwitchApi featureSwitchApi;

	private final DocumentationProvider documentationProvider;

	public TosAttachmentProvider(FeatureSwitchApi featureSwitchApi, DocumentationProvider documentationProvider) {
		this.featureSwitchApi = featureSwitchApi;
		this.documentationProvider = documentationProvider;
	}

	public Response provide(String name) {
		if (!featureSwitchApi.isEnabled(FeatureSwitchType.TOS_AND_PP)) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		if (featureSwitchApi.isEnabled(FeatureSwitchType.DOCKER)) {
			return documentationProvider.provideFile("/" + name, name);
		}
		ClassPathResource classPathResource = new ClassPathResource(name);
		return documentationProvider.provideFile(classPathResource, name);
	}

}
