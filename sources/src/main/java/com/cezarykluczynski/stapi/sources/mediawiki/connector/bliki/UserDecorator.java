package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import info.bliki.api.Connector;
import info.bliki.api.User;

public class UserDecorator extends User {

	public UserDecorator(String name, String password, String mediawikiApiUrl) {
		super(name, password, mediawikiApiUrl);
	}

	public Connector getConnector() {
		return connector;
	}

}
