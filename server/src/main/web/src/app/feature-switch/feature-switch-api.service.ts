import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class FeatureSwitchApi {

	private api: RestClient;
	private featureSwitches: any = {};

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	loadFeatureSwitches() {
		return this.api.common.panel.featureSwitch.get().then((response) => {
			response.featureSwitches.forEach((value) => {
				this.featureSwitches[value.type] = value.enabled;
			});
		});
	}

	isEnabled(type: any) {
		return !!this.featureSwitches[type];
	}

	private register() {
		this.api.res('common').res('panel').res('featureSwitch');
	}

}
