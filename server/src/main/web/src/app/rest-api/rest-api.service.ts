import { Injectable } from '@angular/core';
import RestClient from 'another-rest-client';

import { RestClientFactoryService } from './rest-client-factory.service';

@Injectable()
export class RestApiService {

	private api: RestClient;
	private apiV2: RestClient;

	constructor(restClientFactoryService: RestClientFactoryService) {
		const prefix = location.href.includes('localhost:4200') ? 'http://localhost:8686' : '';
		this.api = restClientFactoryService.createRestClient(prefix + '/api/v1/rest');
		this.apiV2 = restClientFactoryService.createRestClient(prefix + '/api/v2/rest');
	}

	public getApi() {
		return this.api;
	}

	public getApiV2() {
		return this.apiV2;
	}

}
