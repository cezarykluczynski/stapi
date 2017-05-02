import { RestApi } from './rest/RestApi.js';

export class InitializationService {

	constructor() {
		new RestApi();
	}

}
