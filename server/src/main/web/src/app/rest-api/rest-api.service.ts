import { Injectable } from '@angular/core';
import RestClient from 'another-rest-client/dist/rest-client';

import { RestClientFactoryService } from './rest-client-factory.service';

@Injectable()
export class RestApiService {

	private api: RestClient;
	private apiV2: RestClient;

	constructor(restClientFactoryService: RestClientFactoryService) {
		const prefix = location.href.includes('localhost:4200') ? 'http://localhost:8686' : '';
		this.api = this.wrap(restClientFactoryService.createRestClient(prefix + '/api/v1/rest'));
		this.apiV2 = this.wrap(restClientFactoryService.createRestClient(prefix + '/api/v2/rest'));
	}

	public getApi(): RestClient {
		return this.api;
	}

	public getApiV2(): RestClient {
		return this.apiV2;
	}

	private wrap(restClient: RestClient): RestClient {
		console.log(restClient);
		restClient.on('request', (xhr: any) => {
			xhr.setRequestHeader('X-Api-Browser', 'true')
		}, null);
		return restClient;
	}

}
