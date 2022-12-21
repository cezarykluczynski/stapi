import { TestBed, inject, async } from '@angular/core/testing';

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

		expect(res.calls.count()).toBe(7);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['documentation']);
		expect(res.calls.argsFor(2)).toEqual(['common']);
		expect(res.calls.argsFor(3)).toEqual(['dataVersion']);
		expect(res.calls.argsFor(4)).toEqual(['common']);
		expect(res.calls.argsFor(5)).toEqual(['github']);
		expect(res.calls.argsFor(6)).toEqual(['projectDetails']);
	}));

	describe('after initialization', () => {
		let documentationPromise;
		let dataVersionPromise;
		let githubProjectDetailsPromise;
		const DOCUMENTATION = 'DOCUMENTATION';
		const DATA_VERSION = 'DATA_VERSION';
		const STARGAZERS_COUNT = 101;

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

			githubProjectDetailsPromise = () => {
				return Promise.resolve({
					stargazersCount: STARGAZERS_COUNT
				});
			};

			restClientMock.common = {
				documentation: { get: documentationPromise },
				dataVersion: { get: dataVersionPromise },
				github: {
					projectDetails: {
						get: githubProjectDetailsPromise
					}
				}
			};
		});

		it('does not throw error', inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			expect(() => {
				apiDocumentationApi.loadDocumentation();
				apiDocumentationApi.loadDataVersion();
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

		it('gets GitHub project details', inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			apiDocumentationApi.loadGitHubProjectDetails();

			setTimeout(() => {
				expect(apiDocumentationApi.getGitHubStargazersCount()).toEqual(STARGAZERS_COUNT);
			});
		}));

		it('gets data version', async(inject([ApiDocumentationApi], (apiDocumentationApi: ApiDocumentationApi) => {
			apiDocumentationApi.loadDataVersion();

			setTimeout(() => {
				expect(apiDocumentationApi.getDataVersion()).toEqual(DATA_VERSION);
			});
		})));
	});
});
