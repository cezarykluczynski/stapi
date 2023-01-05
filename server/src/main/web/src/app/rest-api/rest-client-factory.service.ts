import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client/dist/rest-client';

@Injectable()
export class RestClientFactoryService {

	public createRestClient(url: string): RestClient {
		return new RestClient(url);
	}

}
