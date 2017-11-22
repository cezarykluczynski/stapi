import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';

@Injectable()
export class PanelAccountApi {

	private api: RestClient;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	removeAccount() {
		return this.api.panel.accountSettings.delete();
	}

	private register() {
		this.api.res('panel').res('accountSettings');
	}

}
