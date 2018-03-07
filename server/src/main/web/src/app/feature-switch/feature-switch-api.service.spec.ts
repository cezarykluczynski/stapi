import { TestBed, inject, async } from '@angular/core/testing';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';
import { FeatureSwitchApi } from './feature-switch-api.service';

class RestClientMock {
	public res: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('FeatureSwitchApi', () => {
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
				FeatureSwitchApi,
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([FeatureSwitchApi], (service: FeatureSwitchApi) => {
		expect(service).toBeTruthy();
	}));

	describe('after initialization', () => {
		let featureSwitchesPromise;
		const DOCUMENTATION = 'DOCUMENTATION';

		beforeEach(() => {
			featureSwitchesPromise = () => {
				return Promise.resolve({
					featureSwitches: [
						{
							type: 'ADMIN_PANEL',
							enabled: true
						}
					]
				});
			};

			restClientMock.common = {
				panel: {
					featureSwitch: {
						get: featureSwitchesPromise
					}
				}
			};
		});

		it('does not throw error', inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			expect(() => {
				featureSwitchApi.loadFeatureSwitches();
			}).not.toThrow();
		}));

		it('tells when feature switch is enabled', async(inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			featureSwitchApi.loadFeatureSwitches();

			setTimeout(() => {
				expect(featureSwitchApi.isEnabled('ADMIN_PANEL')).toBeTrue();
			});
		})));

		it('tells when feature switch is enabled', async(inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			featureSwitchApi.loadFeatureSwitches();

			setTimeout(() => {
				expect(featureSwitchApi.isEnabled('OTHER_FEATURE')).toBeFalse();
			});
		})));
	});
});
