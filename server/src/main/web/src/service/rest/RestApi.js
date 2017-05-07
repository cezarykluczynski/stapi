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
		this.api.res({
			common: [
				'details'
			]
		});
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
			this.callback();
		}).catch(error => {
			this.errorCallback(error);
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

	getContentKey(response) {
		return R.filter(key => key !== 'page', Object.keys(response))[0];
	}

	getDetails() {
		return this.details;
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

}
