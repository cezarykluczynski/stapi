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
				'mappings'
			]
		});
		this.api.common.mappings.get().then(response => {
			response.urls.sort((left, right) => {
				return left.symbol > right.symbol ? 1 : -1;
			});

			this.urls = response.urls;
			this.urls.forEach((url) => {
				var res = {};
				res[url.suffix] = ['search'];
				this.api.res(res);
			});
			this.callback();
		}).catch(error => {
			this.errorCallback(error);
		});
	}

	search(symbol, phrase, single) {
		const serviceName = this.findBySymbol(symbol).suffix;
		const searchApi = this.api[serviceName].search;
		var promise = phrase ? searchApi.post({
			title: phrase, name: phrase
		}, 'application/x-www-form-urlencoded') : searchApi.get();
		return promise.then(response => {
			return {
				page: response.page,
				content: response[this.getContentKey(response)]
			}
		});
	}

	get(symbol, uid) {
		const serviceName = this.findBySymbol(symbol).suffix;
		const api = this.api[serviceName];
		return api.get({uid: uid}).then(response => {
			return {
				page: null,
				content: response[this.getContentKey(response)]
			}
		});
	}

	getContentKey(response) {
		return R.filter(key => key !== 'page', Object.keys(response))[0];
	}

	getUrls() {
		return this.urls;
	}

	findBySymbol(symbol) {
		for (let i = 0; i < this.urls.length; i++) {
			const url = this.urls[i];
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

}
