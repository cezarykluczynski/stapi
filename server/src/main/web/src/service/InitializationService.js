import { RestApi } from './rest/RestApi.js';

export class InitializationService {

	constructor() {
		this.restApi = new RestApi();
	}

	getRestApi() {
		return this.restApi;
	}

}
