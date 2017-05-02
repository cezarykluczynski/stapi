import RestClient from 'another-rest-client';
import R from 'ramda';

export class RestApi {

	constructor() {
		this.api = new RestClient('http://localhost:8686/api/v1/rest');
		this.api.res({
			common: [
				'mappings'
			]
		});
		this.api.common.mappings.get().then((response) => {
			this.urls = response.urls;
			this.urls.forEach((url) => {
				var res = {};
				res[url.suffix] = ['search'];
				this.api.res(res);
			});
		});
	}

	search(symbol, phrase) {
		const serviceName = this.findBySymbol(symbol).suffix;
		const searchApi = this.api[serviceName].search;
		var promise = phrase ? searchApi.post({title: phrase, name: phrase}, 'application/x-www-form-urlencoded') : searchApi.get();
		return promise.then((response) => {
			var contentKey = R.filter(key => key !== 'page', Object.keys(response))[0];
			return {
				page: response.page,
				content: response[contentKey]
			}
		});
	}

	findBySymbol(symbol) {
		for (let i = 0; i < this.urls.length; i++) {
			const url = this.urls[i];
			if (url.symbol === symbol) {
				return url;
			}
		}
	}

}
