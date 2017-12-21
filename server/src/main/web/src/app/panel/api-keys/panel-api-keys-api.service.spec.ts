import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';
import { PanelApiKeysApi } from './panel-api-keys-api.service';

class RestClientMock {
	public res: any;
	public panel: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('PanelApiKeysApi', () => {
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
					provide: PanelApiKeysApi,
					useClass: PanelApiKeysApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([PanelApiKeysApi], (panelApiKeysApi: PanelApiKeysApi) => {
		expect(panelApiKeysApi).toBeTruthy();

		expect(res.calls.count()).toBe(3);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['panel']);
		expect(res.calls.argsFor(2)).toEqual(['apiKeys']);
	}));

	describe('after initialization', () => {
		let API_KEY = {
			apiKey: 'apiKey'
		};
		let API_KEYS = {
			apiKeys: 'apiKeys'
		};
		let API_KEY_ID: number = 17;
		let DELETE_RESULT = {
			removed: true
		};
		const UPDATE_RESULT = {
			successful: true
		};
		const API_KEY_DETAILS = {};

		beforeEach(() => {
			restClientMock.common = {};
			restClientMock.common.panel = {};
			restClientMock.common.panel.apiKeys = (apiKeyId) => {
				return {
					delete: () => {
						expect(apiKeyId).toBe(API_KEY_ID);
						return Promise.resolve(DELETE_RESULT);
					},
					post: (details) => {
						expect(apiKeyId).toBe(API_KEY_ID);
						expect(details).toBe(API_KEY_DETAILS);
						return Promise.resolve(UPDATE_RESULT);
					}
				}
			};
			restClientMock.common.panel.apiKeys.post = () => {
				return Promise.resolve(API_KEY);
			};
			restClientMock.common.panel.apiKeys.get = () => {
				return Promise.resolve(API_KEYS);
			};
		});

		it('creates API key', inject([PanelApiKeysApi], (panelApiKeysApi: PanelApiKeysApi) => {
			panelApiKeysApi.createApiKey().then((response) => {
				expect(response).toBe(API_KEY);
			});
		}));

		it('gets API keys', inject([PanelApiKeysApi], (panelApiKeysApi: PanelApiKeysApi) => {
			panelApiKeysApi.getApiKeys().then((response) => {
				expect(response).toBe(API_KEYS);
			});
		}));

		it('removes API key', inject([PanelApiKeysApi], (panelApiKeysApi: PanelApiKeysApi) => {
			panelApiKeysApi.removeApiKey(API_KEY_ID).then((response) => {
				expect(response).toBe(DELETE_RESULT);
			});
		}));

		it('save API key details', inject([PanelApiKeysApi], (panelApiKeysApi: PanelApiKeysApi) => {
			panelApiKeysApi.saveApiKeyDetails(API_KEY_ID, API_KEY_DETAILS).then((response) => {
				expect(response).toBe(UPDATE_RESULT);
			});
		}));
	});
});
