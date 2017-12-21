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
		return this.api.common.panel.accountSettings.delete();
	}

	updateBasicData(basicData) {
		return this.api.common.panel.accountSettings.basicData.post(basicData, 'application/x-www-form-urlencoded');
	}

	updateOwnConsents(consents) {
		return this.api.common.panel.accountSettings.consents.own.post({consents}, 'application/x-www-form-urlencoded');
	}

	getOwnConsents() {
		return this.api.common.panel.accountSettings.consents.own.get();
	}

	getConsents() {
		return this.api.common.panel.accountSettings.consents.get();
	}

	private register() {
		this.api.res('common').res('panel').res('accountSettings');
		this.api.res('common').res('panel').res('accountSettings').res('basicData');
		this.api.res('common').res('panel').res('accountSettings').res('consents').res('own');
	}

}
