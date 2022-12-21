import { TestBed, inject } from '@angular/core/testing';

import { RestClientFactoryService } from './rest-client-factory.service';
import { RestApiService } from './rest-api.service';

class RestClientMock {
	public res: any;
	public on: any;
	public common: any;
	public performer: any;
}

class RestClientFactoryServiceMock {
	createRestClient() {}
}

describe('RestApiService', () => {
	let restClientFactoryServiceMock: RestClientFactoryServiceMock;
	let restClientMock: RestClientMock;
	let res;
	let on;
	let get;

	beforeEach(() => {
		restClientFactoryServiceMock = new RestClientFactoryServiceMock();
		res = jasmine.createSpy('res').and.callFake(() => {
			return { res };
		});
		on = jasmine.createSpy('on');
		get = jasmine.createSpy('get').and.returnValue(Promise.resolve(true));
		restClientMock = new RestClientMock();
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
