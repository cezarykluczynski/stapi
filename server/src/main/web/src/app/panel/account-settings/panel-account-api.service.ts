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

	updateBasicData(basicData) {
		return this.api.panel.basicData.post(basicData, 'application/x-www-form-urlencoded');
	}

	updateOwnConsents(consents) {
		return this.api.panel.consents.own.post({consents}, 'application/x-www-form-urlencoded');
	}

	getOwnConsents() {
		return this.api.panel.consents.own.get();
	}

	getConsents() {
		return this.api.panel.consents.get();
	}

	private register() {
		this.api.res('panel').res('accountSettings');
		this.api.res('panel').res('basicData');
		this.api.res('panel').res('consents').res('own');
	}

}
