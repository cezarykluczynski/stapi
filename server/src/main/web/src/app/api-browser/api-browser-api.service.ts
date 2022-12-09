import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiBrowserApi {

	private api: RestClient;
	private apiV2: RestClient;
	private details: any;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.apiV2 = restApiService.getApiV2();
		this.register();
	}

	loadDetails() {
		return this.api.common.details.get().then(response => {
			response.details.sort((left, right) => {
				return left.symbol > right.symbol ? 1 : -1;
			});
			this.details = response.details;
			this.details.forEach((restEndpointDetails) => {
				const res = {};
				res[restEndpointDetails.apiEndpointSuffix] = ['search'];
				if (restEndpointDetails.version === 'v2') {
					this.apiV2.res(res);
				} else {
					this.api.res(res);
				}
			});
		});
	}

	getDetails() {
		return this.details;
	}

	search(symbol, phrase, single) {
		const restEndpointDetails = this.findBySymbol(symbol);
		const searchApi = this.getApi(restEndpointDetails).search;
		const promise = phrase ? searchApi.post({
			title: phrase, name: phrase
		}, 'application/x-www-form-urlencoded') : searchApi.get();
		return promise.then(response => {
			return {
				page: response.page,
				content: response[this.getContentKey(response)]
			};
		});
	}

	get(symbol, uid) {
		const restEndpointDetails = this.findBySymbol(symbol);
		const api = this.getApi(restEndpointDetails);
		return api.get({uid: uid}).then(response => {
			return {
				page: null,
				content: response[this.getContentKey(response)]
			};
		});
	}

	private register() {
		this.api.res('common').res('details');
	}

	private findBySymbol(symbol) {
		for (let i = 0; i < this.details.length; i++) {
			const url = this.details[i];
			if (url.symbol === symbol) {
				return url;
			}
		}
	}

	private getContentKey(response) {
		for (const key in response) {
			if (key !== 'page' && key !== 'sort') {
				return key;
			}
		}

		return Object.keys(response)[0];
	}

	private getApi(restEndpointDetails: any) {
		const serviceName = restEndpointDetails.apiEndpointSuffix;
		const api = restEndpointDetails.version === 'v2' ? this.apiV2 : this.api;
		return api[serviceName];
	}

}
