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
		return this.api.common.panel.me.get();
	}

	getOAuthAuthorizeUrl() {
		return this.api.oauth.github.oAuthAuthorizeUrl.get();
	}

	getGitHubProjectDetails() {
		return this.api.common.panel.github.projectDetails.get();
	}

	private register() {
		this.api.res('common').res('panel').res('me');
		this.api.res('common').res('panel').res('github').res('projectDetails');
		this.api.res('oauth').res('github').res('oAuthAuthorizeUrl');
	}

}
