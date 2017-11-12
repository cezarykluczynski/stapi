import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class ApiBrowserApi {

	private api: RestClient;
	private details: any;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	loadDetails() {
		return this.api.common.details.get().then(response => {
			response.details.sort((left, right) => {
				return left.symbol > right.symbol ? 1 : -1;
			});
			this.details = response.details;
			this.details.forEach((url) => {
				const res = {};
				res[url.apiEndpointSuffix] = ['search'];
				this.api.res(res);
			});
		});
	}

	getDetails() {
		return this.details;
	}

	search(symbol, phrase, single) {
		const serviceName = this.findBySymbol(symbol).apiEndpointSuffix;
		const searchApi = this.api[serviceName].search;
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

	// TODO
	get(symbol, uid) {
		const serviceName = this.findBySymbol(symbol).apiEndpointSuffix;
		const api = this.api[serviceName];
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
		for (let key in response) {
			if (key !== 'page' && key !== 'sort') {
				return key;
			}
		}

		return Object.keys(response)[0];
	}

}
