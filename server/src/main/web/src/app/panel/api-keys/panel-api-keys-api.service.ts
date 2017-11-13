import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';

@Injectable()
export class PanelApiKeysApi {

	private api: RestClient;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	createApiKey() {
		return this.api.panel.apiKeys.post({}, 'application/x-www-form-urlencoded');
	}

	getApiKeys() {
		return this.api.panel.apiKeys.get();
	}

	removeApiKey(apiKeyId: number) {
		return this.api.panel.apiKeys(apiKeyId).delete();
	}

	private register() {
		this.api.res('panel').res('apiKeys');
	}

}
