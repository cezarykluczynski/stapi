import { TestBed, inject, async } from '@angular/core/testing';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiBrowserApi } from './api-browser-api.service';

class RestClientMock {
	public res: any;
	public common: any;
	public performer: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('ApiBrowserApi', () => {
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
					provide: ApiBrowserApi,
					useClass: ApiBrowserApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
		expect(apiBrowserApi).toBeTruthy();

		expect(res.calls.count()).toBe(2);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['details']);
	}));

	describe('after initialization', () => {
		let detailsPromise;

		const DETAIL = {
			apiEndpointSuffix: 'performer',
			symbol: 'PE'
		};
		const page = {};
		const content = {};
		const DOCUMENTATION = 'DOCUMENTATION';
		const OAUTH_URL = 'OAUTH_URL';
		const CURRENT_USER_NAME = 'CURRENT_USER_NAME';

		beforeEach(() => {
			detailsPromise = () => {
				return Promise.resolve({
					details: [DETAIL]
				});
			};

			restClientMock.common = {
				details: { get: detailsPromise }
			};
			restClientMock.performer = {
				search: {
					post: () => {
						return Promise.resolve({
							page: page,
							performer: content
						});
					},
					get: () => {
						return Promise.resolve({
							page: page,
							performer: content
						});
					}
				}
			};

			spyOn(restClientMock.performer.search, 'post').and.callThrough();
			spyOn(restClientMock.performer.search, 'get').and.callThrough();
		});

		it('does not throw error', inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			expect(() => {
				apiBrowserApi.loadDetails();
			}).not.toThrow();
		}));

		it('searches for phrase', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.search('PE', 'Patrick', false).then((response) => {
					expect(response.page).toBe(page);
					expect(response.content).toBe(content);
				});

				expect(restClientMock.performer.search.post).toHaveBeenCalled();
				expect(restClientMock.performer.search.get).not.toHaveBeenCalled();
			});
		})));

		it('gets all entities', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.search('PE', '', false).then((response) => {
					expect(response.page).toBe(page);
					expect(response.content).toBe(content);
				});

				expect(restClientMock.performer.search.get).toHaveBeenCalled();
				expect(restClientMock.performer.search.post).not.toHaveBeenCalled();
			});
		})));

		it('gets single entity', async(inject([RestApiService], (service: RestApiService) => {
			// TODO
		})));
	});
});
