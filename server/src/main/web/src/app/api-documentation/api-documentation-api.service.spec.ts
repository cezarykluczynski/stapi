import { TestBed, inject, async } from '@angular/core/testing';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiDocumentationApi } from './api-documentation-api.service';

class RestClientMock {
	public res: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('ApiDocumentationApi', () => {
	let restClientMock: RestClientMock;
	let restApiServiceMock: RestApiServiceMock;
	let res;

	beforeEach(() => {
		restClientMock = new RestClientMock();
		restApiServiceMock = new RestApiServiceMock();
		spyOn(restApiServiceMock, 'getApi').and.returnValue(restClientMock);
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
		expect(res.calls.argsFor(1)).toEqual(['documentation']);
	}));

	describe('after initialization', () => {
		let documentationPromise;
		const DOCUMENTATION = 'DOCUMENTATION';

		beforeEach(() => {
			documentationPromise = () => {
				return Promise.resolve({
					documentation: DOCUMENTATION
				});
			};

			restClientMock.common = {
				documentation: { get: documentationPromise }
			};
		});

		it('does not throw error', inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			expect(() => {
				apiDocumentationApi.loadDocumentation();
			}).not.toThrow();
		}));

		it('gets documentation', async(inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			apiDocumentationApi.loadDocumentation();

			setTimeout(() => {
				expect(apiDocumentationApi.getDocumentation()).toEqual({
					documentation: DOCUMENTATION
				});
			});
		})));
	});
});
