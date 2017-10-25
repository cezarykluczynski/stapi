import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

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

		expect(res.calls.count()).toBe(10);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['details']);
		expect(res.calls.argsFor(2)).toEqual(['common']);
		expect(res.calls.argsFor(3)).toEqual(['documentation']);
		expect(res.calls.argsFor(4)).toEqual(['common']);
		expect(res.calls.argsFor(5)).toEqual(['statistics']);
		expect(res.calls.argsFor(6)).toEqual(['entities']);
		expect(res.calls.argsFor(7)).toEqual(['common']);
		expect(res.calls.argsFor(8)).toEqual(['statistics']);
		expect(res.calls.argsFor(9)).toEqual(['hits']);

		expect(on.calls.count()).toBe(1);
		expect(on.calls.argsFor(0)).toEqual(['response', jasmine.any(Function)]);
	}));

	it('updates limits when request is received', inject([RestApiService], (service: RestApiService) => {
		const LIMIT_TOTAL = 250;
		const LIMIT_REMAINING = 154;
		const xhr = {
			getResponseHeader: (key: string) => {
				return key === 'X-Throttle-Limit-Total' ? LIMIT_TOTAL : LIMIT_REMAINING;
			}
		}
		let callback = on.calls.argsFor(0)[1];
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
			}
		}
		let callback = on.calls.argsFor(0)[1];
		let limits;

		service.onLimitUpdate((_limits: any) => {
				limits = _limits;
		});

		callback(xhr);

		expect(limits).toEqual({});
	}));

	describe('after initialization', () => {
		let statisticsPromise;
		let detailsPromise;
		let documentationPromise;

		const DETAIL = {
			apiEndpointSuffix: 'performer',
			symbol: 'PE'
		};
		const page = {};
		const content = {};
		const DOCUMENTATION = 'DOCUMENTATION';

		beforeEach(() => {
			statisticsPromise = () => {
				return Promise.resolve({
					statistics: []
				});
			};
			detailsPromise = () => {
				return Promise.resolve({
					details: [DETAIL]
				});
			};
			documentationPromise = () => {
				return Promise.resolve({
					documentation: DOCUMENTATION
				});
			};

			restClientMock.common = {
				details: { get: detailsPromise },
				statistics: {
					entities: { get: statisticsPromise },
					hits: { get: statisticsPromise }
				},
				documentation: { get: documentationPromise }
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

		it('does not throw error', inject([RestApiService], (service: RestApiService) => {
			expect(() => {
				service.init();
			}).not.toThrow();
		}));

		it('gets statistics', async(inject([RestApiService], (service: RestApiService) => {
			service.init();

			setTimeout(() => {
				expect(service.getStatistics()).toEqual({
					entitiesStatistics: {
						statistics: []
					},
					hitsStatistics: {
						statistics: []
					},
					loaded: true
				});
			})
		})));

		it('gets details', async(inject([RestApiService], (service: RestApiService) => {
			service.init();

			setTimeout(() => {
				expect(service.getDetails()).toEqual([DETAIL]);
				expect(res.calls.argsFor(res.calls.count() - 1)).toEqual([{
					performer: ['search']
				}]);
			});
		})));

		it('gets documentation', async(inject([RestApiService], (service: RestApiService) => {
			service.init();

			setTimeout(() => {
				expect(service.getDocumentation()).toEqual({
					documentation: DOCUMENTATION
				});
			});
		})));

		it('searches for phrase', async(inject([RestApiService], (service: RestApiService) => {
			service.init();

			setTimeout(() => {
				service.search('PE', 'Patrick', false).then((response) => {
					expect(response.page).toBe(page);
					expect(response.content).toBe(content);
				});

				expect(restClientMock.performer.search.post).toHaveBeenCalled();
				expect(restClientMock.performer.search.get).not.toHaveBeenCalled();
			});
		})));

		it('gets all entities', async(inject([RestApiService], (service: RestApiService) => {
			service.init();

			setTimeout(() => {
				service.search('PE', '', false).then((response) => {
					expect(response.page).toBe(page);
					expect(response.content).toBe(content);
				});

				expect(restClientMock.performer.search.get).toHaveBeenCalled();
				expect(restClientMock.performer.search.post).not.toHaveBeenCalled();
			});
		})));

		it('gets single entity', async(inject([RestApiService], (service: RestApiService) => {
			//
		})));
	});


	it('gets content key', inject([RestApiService], (service: RestApiService) => {
		//
	}));
});
