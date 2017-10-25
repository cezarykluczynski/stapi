import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

@Injectable()
export class RestClientFactoryService {

	constructor() { }

	public createRestClient(url: string): RestClient {
		return new RestClient(url);
	}

}
