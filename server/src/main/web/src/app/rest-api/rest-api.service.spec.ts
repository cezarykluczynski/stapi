import { TestBed, inject } from '@angular/core/testing';

import { RestClientFactoryService } from './rest-client-factory.service';
import { RestApiService } from './rest-api.service';
import RestClient from "another-rest-client/dist/rest-client";

class RestClientFactoryServiceMock {
	createRestClient(): any {}
}

describe('RestApiService', () => {
	let restClientFactoryServiceMock: RestClientFactoryServiceMock;
	let restClientMock: jasmine.SpyObj<RestClient>;
	let res: any;
	let on: any;
	let get: any;

	beforeEach(() => {
		restClientFactoryServiceMock = new RestClientFactoryServiceMock();
		res = jasmine.createSpy('res').and.callFake(() => {
			return { res };
		});
		on = jasmine.createSpy('on');
		get = jasmine.createSpy('get').and.returnValue(Promise.resolve(true));
		restClientMock = jasmine.createSpyObj('RestClient', ['on'], ['res', 'common', 'performer']);
		restClientMock.res = res;
		restClientMock.on = on;
		spyOn(restClientFactoryServiceMock, 'createRestClient').and.returnValue(restClientMock);

		TestBed.configureTestingModule({
			providers: [
				{
					provide: RestApiService,
					useClass: RestApiService
				},
				{
					provide: RestClientFactoryService,
					useValue: restClientFactoryServiceMock
				}
			]
		});
	});

	it('is created', inject([RestApiService], (service: RestApiService) => {
		expect(service).toBeTruthy();
	}));

	it('is has API V1 RestClient', inject([RestApiService], (service: RestApiService) => {
		expect(service.getApi()).toBe(restClientMock);
	}));

	it('is has API V2 RestClient', inject([RestApiService], (service: RestApiService) => {
		expect(service.getApiV2()).toBe(restClientMock);
	}));
});
