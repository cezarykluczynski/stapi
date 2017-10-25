import { Injectable } from '@angular/core';
import RestClient from 'another-rest-client';

import { RestClientFactoryService } from './rest-client-factory.service';

@Injectable()
export class RestApiService {

	private api: RestClient;
	private statistics: any;
	private documentation: any;
	private onLimitUpdateCallback: any;
	private details: any;
	private callback: any;
	private errorCallback: any;
	private documentationPromise: any;
	private limits: any;

	constructor(restClientFactoryService: RestClientFactoryService) {
		const prefix = location.href.includes('localhost:4200') ? 'http://localhost:8686' : '';
		this.api = restClientFactoryService.createRestClient(prefix + '/api/v1/rest');
		this.api.res('common').res('details');
		this.api.res('common').res('documentation');
		this.api.res('common').res('statistics').res('entities');
		this.api.res('common').res('statistics').res('hits');
		this.api.on('response', (xhr) => {
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
	}

	init() {
		return Promise.all([this.loadDetails(), this.loadStatistics(), this.loadDocumentation()]);
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

	getStatistics() {
		return this.statistics;
	}

	getContentKey(response) {
		for (let key in response) {
			if (key !== 'page' && key !== 'sort') {
				return key;
			}
		}

		return Object.keys(response)[0];
	}

	getDetails() {
		return this.details;
	}

	getDocumentation() {
		return this.documentation;
	}

	findBySymbol(symbol) {
		for (let i = 0; i < this.details.length; i++) {
			const url = this.details[i];
			if (url.symbol === symbol) {
				return url;
			}
		}
	}

	private loadDetails() {
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
		}).catch(error => {
			if (this.errorCallback) {
				this.errorCallback(error);
			}
		});
	}

	private loadStatistics() {
		const sorter = (left, right) => {
			return left > right ? 1 : left === right ? 0 : -1;
		};
		const entitiesStatisticsPromise = this.api.common.statistics.entities.get().then(response => {
			response.statistics.sort(sorter);
			this.statistics = this.statistics || {};
			this.statistics.entitiesStatistics = response;
		});
		const hitsStatisticsPromise = this.api.common.statistics.hits.get().then(response => {
			response.statistics.sort(sorter);
			this.statistics = this.statistics || {};
			this.statistics.hitsStatistics = response;
		});
		return Promise.all([entitiesStatisticsPromise, hitsStatisticsPromise]).then(() => {
			this.statistics.loaded = true;
		});
	}

	private loadDocumentation() {
		return this.documentationPromise = this.api.common.documentation.get().then(response => {
			this.documentation = response;
			return this.documentation;
		});
	}

	onLimitUpdate(onLimitUpdateCallback) {
		this.onLimitUpdateCallback = onLimitUpdateCallback;
		this.onLimitUpdateCallback(this.limits);
	}

}
