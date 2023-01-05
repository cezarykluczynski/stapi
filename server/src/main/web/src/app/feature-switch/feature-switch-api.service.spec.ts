import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { RestApiService } from '../rest-api/rest-api.service';
import { FeatureSwitchApi } from './feature-switch-api.service';

class RestClientMock {
	public res: any;
	public common: any;
}

class RestApiServiceMock {
	/* eslint  @typescript-eslint/no-empty-function:0 */
	public getApi(): any {}
}

describe('FeatureSwitchApi', () => {
	let restClientMock: RestClientMock;
	let restApiServiceMock: RestApiServiceMock;
	let res: any;

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

		beforeEach(() => {
			featureSwitchesPromise = () => {
				return Promise.resolve({
					featureSwitches: [
						{
							type: 'TOS_AND_PP',
							enabled: true
						}
					]
				});
			};

			restClientMock.common = {
				featureSwitch: {
					get: featureSwitchesPromise
				}
			};
		});

		it('does not throw error', inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			expect(() => {
				featureSwitchApi.loadFeatureSwitches();
			}).not.toThrow();
		}));

		it('tells when feature switch is enabled', waitForAsync(inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			featureSwitchApi.loadFeatureSwitches();

			setTimeout(() => {
				expect(featureSwitchApi.isEnabled('TOS_AND_PP')).toBeTrue();
			});
		})));

		it('tells when feature switch is enabled', waitForAsync(inject([FeatureSwitchApi], (featureSwitchApi: FeatureSwitchApi) => {
			featureSwitchApi.loadFeatureSwitches();

			setTimeout(() => {
				expect(featureSwitchApi.isEnabled('OTHER_FEATURE')).toBeFalse();
			});
		})));
	});
});
