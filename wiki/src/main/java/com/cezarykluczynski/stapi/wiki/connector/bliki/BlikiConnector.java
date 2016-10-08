package com.cezarykluczynski.stapi.wiki.connector.bliki;

import info.bliki.api.Connector;
import info.bliki.api.User;
import info.bliki.api.query.RequestBuilder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Map;

@Service
public class BlikiConnector {

	private User user;

	private Connector connector;

	@Inject
	public BlikiConnector(User user, Connector connector) {
		this.user = user;
		this.connector = connector;
	}

	public String readXML(Map<String, String> params) {
		RequestBuilder requestBuilder = RequestBuilder.create();
		requestBuilder.putAll(params);

		try {
			Method method = connector.getClass().getDeclaredMethod("sendXML", User.class, RequestBuilder.class);
			method.setAccessible(true);
			return (String) method.invoke(connector, user, requestBuilder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
