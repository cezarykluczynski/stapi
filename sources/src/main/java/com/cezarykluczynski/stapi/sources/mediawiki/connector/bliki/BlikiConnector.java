package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams;
import com.google.common.collect.Maps;
import info.bliki.api.Connector;
import info.bliki.api.User;
import info.bliki.api.query.RequestBuilder;
import org.springframework.cache.annotation.Cacheable;
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

		// TODO: remove private method call once this PR is merged and published in Maven Central
		// https://bitbucket.org/axelclk/info.bliki.wiki/pull-requests/10/connector-sendxml-should-be-public/diff
		try {
			Method method = connector.getClass().getDeclaredMethod("sendXML", User.class, RequestBuilder.class);
			method.setAccessible(true);
			return (String) method.invoke(connector, user, requestBuilder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Cacheable(cacheNames = "pagesCache", condition = "@pageCacheService.isCacheable(#title)",
			key = "@pageCacheService.resolveKey(#title)")
	public String getPage(String title) {
		Map<String, String> params = Maps.newHashMap();
		params.put(ApiParams.KEY_ACTION, ApiParams.KEY_ACTION_VALUE_PARSE);
		params.put(ApiParams.KEY_PAGE, title);
		params.put(ApiParams.KEY_PROP, ApiParams.KEY_PROP_VALUE);
		return readXML(params);
	}

}
