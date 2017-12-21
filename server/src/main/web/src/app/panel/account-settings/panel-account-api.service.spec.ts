import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';
import { PanelAccountApi } from './panel-account-api.service';

class RestClientMock {
	public res: any;
	public panel: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('PanelAccountApi', () => {
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
					provide: PanelAccountApi,
					useClass: PanelAccountApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
		expect(panelAccountApi).toBeTruthy();

		expect(res.calls.count()).toBe(12);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['panel']);
		expect(res.calls.argsFor(2)).toEqual(['accountSettings']);
		expect(res.calls.argsFor(3)).toEqual(['common']);
		expect(res.calls.argsFor(4)).toEqual(['panel']);
		expect(res.calls.argsFor(5)).toEqual(['accountSettings']);
		expect(res.calls.argsFor(6)).toEqual(['basicData']);
		expect(res.calls.argsFor(7)).toEqual(['common']);
		expect(res.calls.argsFor(8)).toEqual(['panel']);
		expect(res.calls.argsFor(9)).toEqual(['accountSettings']);
		expect(res.calls.argsFor(10)).toEqual(['consents']);
		expect(res.calls.argsFor(11)).toEqual(['own']);
	}));

	describe('after initialization', () => {
		const DELETE_RESULT = {
			successful: true
		};
		const UPDATE_BASIC_DATA_RESULT = {
			successful: true
		};
		const GET_CONSENTS_RESULT = {
			successful: true
		};
		const UPDATE_CONSENTS_RESULT = {
			successful: true
		};
		const GET_OWN_CONSENTS_RESULT = {
			successful: true
		};

		beforeEach(() => {
			restClientMock.common = {};
			restClientMock.common.panel = {};
			restClientMock.common.panel.accountSettings = {
				delete: () => {
					return Promise.resolve(DELETE_RESULT);
				}
			};
			restClientMock.common.panel.accountSettings.basicData = {
				post: () => {
					return Promise.resolve(UPDATE_BASIC_DATA_RESULT);
				}
			};
			restClientMock.common.panel.accountSettings.consents = {};
			restClientMock.common.panel.accountSettings.consents.get = () => {
				return Promise.resolve(GET_CONSENTS_RESULT);
			};
			restClientMock.common.panel.accountSettings.consents.own = {
				post: () => {
					return Promise.resolve(UPDATE_CONSENTS_RESULT);
				},
				get: () => {
					return Promise.resolve(GET_OWN_CONSENTS_RESULT);
				}
			};
		});

		it('removes account', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.removeAccount().then((response) => {
				expect(response).toBe(DELETE_RESULT);
			});
		}));

		it('updates basic data', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.updateBasicData({}).then((response) => {
				expect(response).toBe(UPDATE_BASIC_DATA_RESULT);
			});
		}));

		it('updates own consents', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.updateOwnConsents({}).then((response) => {
				expect(response).toBe(UPDATE_CONSENTS_RESULT);
			});
		}));

		it('gets own consents', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.getOwnConsents().then((response) => {
				expect(response).toBe(GET_OWN_CONSENTS_RESULT);
			});
		}));

		it('gets consents', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.getConsents().then((response) => {
				expect(response).toBe(GET_CONSENTS_RESULT);
			});
		}));
	});
});
