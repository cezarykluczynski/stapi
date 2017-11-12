import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class PanelApi {

	private api: RestClient;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	getMe() {
		return this.api.panel.common.me.get();
	}

	getOAuthAuthorizeUrl() {
		return this.api.oauth.github.oAuthAuthorizeUrl.get();
	}

	private register() {
		this.api.res('panel').res('common').res('me');
		this.api.res('oauth').res('github').res('oAuthAuthorizeUrl');
	}

}
