import { TestBed, inject, async } from '@angular/core/testing';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { ApiBrowserApi } from './api-browser-api.service';

class RestClientMock {
	public res: any;
	public common: any;
	public performer: any;
}

class RestClientV2Mock {
	public res: any;
	public book: any;
}

class RestApiServiceMock {
	public getApi() {}
	public getApiV2() {}
}

describe('ApiBrowserApi', () => {
	let restClientMock: RestClientMock;
	let restClientV2Mock: RestClientV2Mock;
	let restApiServiceMock: RestApiServiceMock;
	let res;
	let resV2;

	beforeEach(() => {
		restClientMock = new RestClientMock();
		restClientV2Mock = new RestClientV2Mock();
		restApiServiceMock = new RestApiServiceMock();
		spyOn(restApiServiceMock, 'getApi').and.returnValue(restClientMock);
		spyOn(restApiServiceMock, 'getApiV2').and.returnValue(restClientV2Mock);
		res = jasmine.createSpy('res').and.callFake(() => {
			return { res };
		});
		resV2 = jasmine.createSpy('resV2').and.callFake(() => {
			return { resV2 };
		});
		restClientMock.res = res;
		restClientV2Mock.res = resV2;

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
		const performerPage = {};
		const performerContent = {};
		const bookPage = {};
		const bookContent = {};
		const DOCUMENTATION = 'DOCUMENTATION';
		const OAUTH_URL = 'OAUTH_URL';
		const CURRENT_USER_NAME = 'CURRENT_USER_NAME';

		beforeEach(() => {
			detailsPromise = () => {
				return Promise.resolve({
					details: [{
						apiEndpointSuffix: 'performer',
						symbol: 'PE',
						version: 'v1'
					}, {
						apiEndpointSuffix: 'book',
						symbol: 'BO',
						version: 'v2'
					}]
				});
			};

			restClientMock.common = {
				details: { get: detailsPromise }
			};
			restClientMock.performer = {
				search: {
					post: () => {
						return Promise.resolve({
							page: performerPage,
							performer: performerContent
						});
					},
					get: () => {
						return Promise.resolve({
							page: performerPage,
							performer: performerContent
						});
					}
				},
				get: ({}) => {
					return Promise.resolve({
						performer: performerContent
					});
				}
			};
			restClientV2Mock.book = {
				search: {
					post: () => {
						return Promise.resolve({
							page: bookPage,
							book: bookContent
						});
					},
					get: () => {
						return Promise.resolve({
							page: bookPage,
							book: bookContent
						});
					}
				},
				get: () => {
					return Promise.resolve({
						book: bookContent
					});
				}
			};

			spyOn(restClientMock.performer.search, 'post').and.callThrough();
			spyOn(restClientMock.performer.search, 'get').and.callThrough();
			spyOn(restClientMock.performer, 'get').and.callThrough();
			spyOn(restClientV2Mock.book.search, 'post').and.callThrough();
			spyOn(restClientV2Mock.book.search, 'get').and.callThrough();
			spyOn(restClientV2Mock.book, 'get').and.callThrough();
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
					expect(response.page).toBe(performerPage);
					expect(response.content).toBe(performerContent);
				});

				expect(restClientMock.performer.search.post).toHaveBeenCalled();
				expect(restClientMock.performer.search.get).not.toHaveBeenCalled();
			});
		})));

		it('searches for phrase (V2)', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.search('BO', 'Star Trek Encyclopedia', false).then((response) => {
					expect(response.page).toBe(bookPage);
					expect(response.content).toBe(bookContent);
				});

				expect(restClientV2Mock.book.search.post).toHaveBeenCalled();
				expect(restClientV2Mock.book.search.get).not.toHaveBeenCalled();
			});
		})));

		it('gets all entities', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.search('PE', '', false).then((response) => {
					expect(response.page).toBe(performerPage);
					expect(response.content).toBe(performerContent);
				});

				expect(restClientMock.performer.search.get).toHaveBeenCalled();
				expect(restClientMock.performer.search.post).not.toHaveBeenCalled();
			});
		})));

		it('gets all entities (V2)', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.search('BO', '', false).then((response) => {
					expect(response.page).toBe(bookPage);
					expect(response.content).toBe(bookContent);
				});

				expect(restClientV2Mock.book.search.get).toHaveBeenCalled();
				expect(restClientV2Mock.book.search.post).not.toHaveBeenCalled();
			});
		})));

		it('gets single entity', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.get('PE', 'PEMA0000004852').then((response) => {
					expect(response.content).toBe(performerContent);
				});

				expect(restClientMock.performer.get).toHaveBeenCalled();
			});
		})));

		it('gets single entity (V2)', async(inject([ApiBrowserApi], (apiBrowserApi: ApiBrowserApi) => {
			apiBrowserApi.loadDetails();

			setTimeout(() => {
				apiBrowserApi.get('BO', 'PEMA0000004852').then((response) => {
					expect(response.content).toBe(bookContent);
				});

				expect(restClientV2Mock.book.get).toHaveBeenCalled();
			});
		})));
	});
});
