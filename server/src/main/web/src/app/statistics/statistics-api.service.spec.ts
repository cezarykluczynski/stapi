import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { StatisticsApi } from './statistics-api.service';

class RestClientMock {
	public res: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('StatisticsApi', () => {
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
					provide: StatisticsApi,
					useClass: StatisticsApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([StatisticsApi], (statisticsApi: StatisticsApi) => {
		expect(statisticsApi).toBeTruthy();

		expect(res.calls.count()).toBe(6);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['statistics']);
		expect(res.calls.argsFor(2)).toEqual(['entities']);
		expect(res.calls.argsFor(3)).toEqual(['common']);
		expect(res.calls.argsFor(4)).toEqual(['statistics']);
		expect(res.calls.argsFor(5)).toEqual(['hits']);
	}));

	describe('after initialization', () => {
		let statisticsPromise;

		beforeEach(() => {
			statisticsPromise = () => {
				return Promise.resolve({
					statistics: []
				});
			};

			restClientMock.common = {
				statistics: {
					entities: { get: statisticsPromise },
					hits: { get: statisticsPromise }
				}
			};
		});

		it('does not throw error', inject([StatisticsApi], (statistics: StatisticsApi) => {
			expect(() => {
				statistics.loadStatistics();
			}).not.toThrow();
		}));

		it('gets statistics', async(inject([StatisticsApi], (statisticsApi: StatisticsApi) => {
			statisticsApi.loadStatistics();

			setTimeout(() => {
				expect(statisticsApi.getStatistics()).toEqual({
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
	});
});
