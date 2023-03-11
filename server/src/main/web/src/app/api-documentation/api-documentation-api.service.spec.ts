import { TestBed, inject, flushMicrotasks, fakeAsync } from '@angular/core/testing';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiDocumentationApi } from './api-documentation-api.service';

class RestClientMock {
	public res: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi(): any {}
}

describe('ApiDocumentationApi', () => {
	let restClientMock: RestClientMock;
	let restApiServiceMock: RestApiServiceMock;
	let res: any;

	beforeEach(() => {
		restClientMock = new RestClientMock();
		restApiServiceMock = new RestApiServiceMock();
		spyOn<RestApiServiceMock>(restApiServiceMock, 'getApi').and.returnValue(restClientMock);
		res = jasmine.createSpy('res').and.callFake(() => {
			return { res };
		});
		restClientMock.res = res;

		TestBed.configureTestingModule({
			providers: [
				{
					provide: ApiDocumentationApi,
					useClass: ApiDocumentationApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
		expect(apiDocumentationApi).toBeTruthy();

		expect(res.calls.count()).toBe(2);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['dataVersion']);
	}));

	describe('after initialization', () => {
		let documentationPromise;
		let dataVersionPromise;
		const DOCUMENTATION = 'DOCUMENTATION';
		const DATA_VERSION = 'DATA_VERSION';

		beforeEach(() => {
			documentationPromise = () => {
				return Promise.resolve({
					documentation: DOCUMENTATION
				});
			};

			dataVersionPromise = () => {
				return Promise.resolve({
					dataVersion: DATA_VERSION
				});
			};

			restClientMock.common = {
				documentation: { get: documentationPromise },
				dataVersion: { get: dataVersionPromise }
			};
		});

		it('does not throw error', inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			expect(() => {
				apiDocumentationApi.loadDataVersion();
			}).not.toThrow();
		}));

		it('gets data version', fakeAsync(inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			apiDocumentationApi.loadDataVersion();

			flushMicrotasks();
			expect(apiDocumentationApi.getDataVersion()).toEqual(DATA_VERSION);
		})));
	});
});
