import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { CookieService } from 'ngx-cookie-service';

import { RestClientFactoryService } from './rest-client-factory.service';
import { RestApiService } from './rest-api.service';

class RestClientMock {
	public res: any;
	public on: any;
	public common: any;
	public performer: any;
	public panel: any;
	public oauth: any;
}

class RestClientFactoryServiceMock {
	createRestClient() {}
}

class CookieServiceMock {
	get() {}
}

describe('RestApiService', () => {
	const XSRF_TOKEN_VALUE = 'XSRF_TOKEN_VALUE';
	let restClientFactoryServiceMock: RestClientFactoryServiceMock;
	let restClientMock: RestClientMock;
	let cookieServiceMock: CookieServiceMock;
	let restApiService: RestApiService;
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
		cookieServiceMock = new CookieServiceMock();

		TestBed.configureTestingModule({
			providers: [
				{
					provide: RestApiService,
					useClass: RestApiService
				},
				{
					provide: RestClientFactoryService,
					useValue: restClientFactoryServiceMock
				},
				{
					provide: CookieService,
					useValue: cookieServiceMock
				}
			]
		});
	});

	it('is created', inject([RestApiService], (service: RestApiService) => {
		expect(service).toBeTruthy();
	}));

	it('sets XSRF token when request is about to be send', inject([RestApiService], (service: RestApiService) => {
		const LIMIT_TOTAL = 250;
		const LIMIT_REMAINING = 154;
		const xhr = {
			setRequestHeader: jasmine.createSpy('setRequestHeader')
		}
		let callback = on.calls.argsFor(0)[1];
		spyOn(cookieServiceMock, 'get').and.returnValue(XSRF_TOKEN_VALUE);

		callback(xhr);

		expect(xhr.setRequestHeader).toHaveBeenCalledWith('X-XSRF-TOKEN', XSRF_TOKEN_VALUE);
		expect(cookieServiceMock.get).toHaveBeenCalledWith('XSRF-TOKEN');
	}));

	it('updates limits when request is received', inject([RestApiService], (service: RestApiService) => {
		const LIMIT_TOTAL = 250;
		const LIMIT_REMAINING = 154;
		const xhr = {
			getResponseHeader: (key: string) => {
				return key === 'X-Throttle-Limit-Total' ? LIMIT_TOTAL : LIMIT_REMAINING;
			},
			setRequestHeader: () => {}
		}
		let callback = on.calls.argsFor(1)[1];
		let limits;

		service.onLimitUpdate((_limits: any) => {
				limits = _limits;
		});

		callback(xhr);

		expect(limits).toEqual({
			total: LIMIT_TOTAL,
			remaining: LIMIT_REMAINING
		});
	}));

	it('updates limits with empty object when no limits could be extracted', inject([RestApiService], (service: RestApiService) => {
		const xhr = {
			getResponseHeader: (key: string) => {
				return null;
			},
			setRequestHeader: () => {}
		}
		let callback = on.calls.argsFor(1)[1];
		let limits;

		service.onLimitUpdate((_limits: any) => {
				limits = _limits;
		});

		callback(xhr);

		expect(limits).toEqual({});
	}));
});
