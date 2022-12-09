import { Injectable } from '@angular/core';
import RestClient from 'another-rest-client';

import { CookieService } from 'ngx-cookie-service';

import { RestClientFactoryService } from './rest-client-factory.service';

@Injectable()
export class RestApiService {

	private api: RestClient;
	private apiV2: RestClient;
	private cookieService: CookieService;
	private onLimitUpdateCallback: any;
	private limits: any;

	constructor(restClientFactoryService: RestClientFactoryService, cookieService: CookieService) {
		this.cookieService = cookieService;
		const prefix = location.href.includes('localhost:4200') ? 'http://localhost:8686' : '';
		this.api = this.wrap(restClientFactoryService.createRestClient(prefix + '/api/v1/rest'));
		this.apiV2 = this.wrap(restClientFactoryService.createRestClient(prefix + '/api/v2/rest'));
	}

	public getApi() {
		return this.api;
	}

	public getApiV2() {
		return this.apiV2;
	}

	onLimitUpdate(onLimitUpdateCallback) {
		this.onLimitUpdateCallback = onLimitUpdateCallback;
		this.onLimitUpdateCallback(this.limits);
	}

	private wrap(restClient: RestClient) {
		restClient.on('request', (xhr) => {
			const xsrfToken = this.cookieService.get('XSRF-TOKEN');
			if (xsrfToken) {
				xhr.setRequestHeader('X-XSRF-TOKEN', xsrfToken);
			}
		});
		restClient.on('response', (xhr) => {
			try {
				this.limits = {
					total: xhr.getResponseHeader('X-Throttle-Limit-Total'),
					remaining: xhr.getResponseHeader('X-Throttle-Limit-Remaining')
				};
				if (this.limits.total != null && this.limits.remaining != null) {
					this.onLimitUpdateCallback(this.limits);
				} else {
					this.limits = {};
					this.onLimitUpdateCallback(this.limits);
				}
			} catch (e) {
			}
		});
		return restClient;
	}
}
