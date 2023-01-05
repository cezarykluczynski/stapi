import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client/dist/rest-client';

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
		return (<any> this.api).common.featureSwitch.get().then((response: any) => {
			response.featureSwitches.forEach((value: any) => {
				this.featureSwitches[value.type] = value.enabled;
			});
		});
	}

	isEnabled(type: any) {
		return !!this.featureSwitches[type];
	}

	private register() {
		this.api.res('common').res('featureSwitch');
	}

}
