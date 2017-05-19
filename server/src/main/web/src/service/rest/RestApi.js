import RestClient from 'another-rest-client';
import R from 'ramda';

let instance;

export class RestApi {

	static getInstance() {
		if (!instance) {
			instance = new RestApi();
		}
		return instance;
	}

	constructor() {
		const prefix = location.href.includes('localhost:3000') ? 'http://localhost:8686' : '';
		this.api = new RestClient(prefix + '/api/v1/rest');
		this.api.res('common').res('details');
		this.api.res('common').res('documentation');
		this.api.res('common').res('statistics').res('entities');
		this.api.res('common').res('statistics').res('hits');
		this.api.on('response', (xhr) => {
			try {
				const limits = {
					total: xhr.getResponseHeader('X-Throttle-Limit-Total'),
					remaining: xhr.getResponseHeader('X-Throttle-Limit-Remaining')
				};
				if (this.onLimitUpdateCallback) {
					this.onLimitUpdateCallback(limits);
				}
			} catch(e) {}
		});
		this.api.common.details.get().then(response => {
			response.details.sort((left, right) => {
				return left.symbol > right.symbol ? 1 : -1;
			});
			this.details = response.details;
			this.details.forEach((url) => {
				var res = {};
				res[url.apiEndpointSuffix] = ['search'];
				this.api.res(res);
			});
			if (this.callback) {
				this.callback();
			}
			this.loadStatistics();
		}).catch(error => {
			if (this.errorCallback) {
				this.errorCallback(error);
			}
		});
	}

	search(symbol, phrase, single) {
		const serviceName = this.findBySymbol(symbol).apiEndpointSuffix;
		const searchApi = this.api[serviceName].search;
		var promise = phrase ? searchApi.post({
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
		const serviceName = this.findBySymbol(symbol).apiEndpointSuffix;
		const api = this.api[serviceName];
		return api.get({uid: uid}).then(response => {
			return {
				page: null,
				content: response[this.getContentKey(response)]
			};
		});
	}

	loadStatistics() {
		const entitiesStatisticsPromise = this.api.common.statistics.entities.get().then(response => {
			response.statistics.sort((left, right) => {
				return left > right ? 1 : left === right ? 0 : -1;
			});
			this.statistics = this.statistics || {};
			this.statistics.entitiesStatistics = response;
		});
		const hitsStatisticsPromise = this.api.common.statistics.hits.get().then(response => {
			response.statistics.sort((left, right) => {
				return left > right ? 1 : left === right ? 0 : -1;
			});
			this.statistics = this.statistics || {};
			this.statistics.hitsStatistics = response;
		});
		Promise.all([entitiesStatisticsPromise, hitsStatisticsPromise]).then(() => {
			this.statistics.loaded = true;
			if (this.onStatisticsReadyCallbackList && this.onStatisticsReadyCallbackList.length) {
				this.onStatisticsReadyCallbackList.forEach(onStatisticsReadyCallback => {
					onStatisticsReadyCallback();
				});
			}
		});
	}

	loadDocumentation() {
		if (!this.documentation && !this.documentationPromise) {
			this.documentationPromise = this.api.common.documentation.get().then(response => {
				this.documentation = response;
				return this.documentation;
			});
			return this.documentationPromise;
		} else if (this.documentationPromise) {
			return this.documentationPromise;
		} else {
			return Promise.all([this.documentationPromise]);
		}
	}

	getStatistics() {
		return this.statistics;
	}

	hasStatistics() {
		return this.statistics && this.statistics.loaded;
	}

	getContentKey(response) {
		return R.filter(key => key !== 'page', Object.keys(response))[0];
	}

	getDetails() {
		return this.details;
	}

	hasDetails() {
		return !!this.details;
	}

	findBySymbol(symbol) {
		for (let i = 0; i < this.details.length; i++) {
			const url = this.details[i];
			if (url.symbol === symbol) {
				return url;
			}
		}
	}

	whenReady(callback) {
		this.callback = callback;
	}

	onError(errorCallback) {
		this.errorCallback = errorCallback;
	}

	onLimitUpdate(onLimitUpdateCallback) {
		this.onLimitUpdateCallback = onLimitUpdateCallback;
	}

	onStatisticsReady(onStatisticsReadyCallback) {
		this.onStatisticsReadyCallbackList = this.onStatisticsReadyCallbackList || [];
		this.onStatisticsReadyCallbackList.push(onStatisticsReadyCallback);
	}

}
