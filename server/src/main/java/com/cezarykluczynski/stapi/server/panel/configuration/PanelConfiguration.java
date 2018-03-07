package com.cezarykluczynski.stapi.server.panel.configuration;

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelAccountSettingsEndpoint;
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelAdminEndpoint;
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelApiKeysEndpoint;
import com.cezarykluczynski.stapi.server.panel.endpoint.PanelCommonEndpoint;
import org.apache.cxf.endpoint.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class PanelConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	@ConditionalOnProperty("featureSwitch.adminPanel")
	public Server panelAccountSettingsRestEndpoint() {
		return endpointFactory.createRestEndpoint(PanelAccountSettingsEndpoint.class, PanelAccountSettingsEndpoint.ADDRESS);
	}

	@Bean
	public Server panelAdminRestEndpoint() {
		return endpointFactory.createRestEndpoint(PanelAdminEndpoint.class, PanelAdminEndpoint.ADDRESS);
	}

	@Bean
	@ConditionalOnProperty("featureSwitch.adminPanel")
	public Server panelApiKeysRestEndpoint() {
		return endpointFactory.createRestEndpoint(PanelApiKeysEndpoint.class, PanelApiKeysEndpoint.ADDRESS);
	}

	@Bean
	public Server panelCommonRestEndpoint() {
		return endpointFactory.createRestEndpoint(PanelCommonEndpoint.class, PanelCommonEndpoint.ADDRESS);
	}

}
