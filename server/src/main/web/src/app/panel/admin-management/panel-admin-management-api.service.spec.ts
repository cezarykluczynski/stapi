import { TestBed, inject, async } from '@angular/core/testing';
import RestClient from 'another-rest-client';

import { RestApiService } from '../../rest-api/rest-api.service';
import { PanelAdminManagementApi } from './panel-admin-management-api.service';

class RestClientMock {
	public res: any;
	public panel: any;
	public common: any;
}

class RestApiServiceMock {
	public getApi() {}
}

describe('PanelAdminManagementApi', () => {
	let restClientMock: RestClientMock;
	let restApiServiceMock: RestApiServiceMock;
	let res;

	const PAGE_NUMBER = 2;
	const BLOCK = {
		accountId: 1,
		apiKeyId: 2
	};
	const UNBLOCK = {
		accountId: 3,
		apiKeyId: 4
	};
	const API_KEYS_SEARCH_CRITERIA_RESULT = {
		apiKeys: {},
		pager: {}
	};
	const ACCOUNTS_SEARCH_CRITERIA_RESULT = {
		apiKeys: {},
		pager: {}
	};
	const BLOCK_RESULT = {
		successful: true
	};
	const UNBLOCK_RESULT = {
		successful: true
	};
	const API_KEYS_SEARCH_CRITERIA = {};
	const ACCOUNTS_SEARCH_CRITERIA = {};

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
					provide: PanelAdminManagementApi,
					useClass: PanelAdminManagementApi
				},
				{
					provide: RestApiService,
					useValue: restApiServiceMock
				}
			]
		});
	});

	it('is created', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
		expect(panelAdminManagementApi).toBeTruthy();

		expect(res.calls.count()).toBe(14);
		expect(res.calls.argsFor(0)).toEqual(['common']);
		expect(res.calls.argsFor(1)).toEqual(['panel']);
		expect(res.calls.argsFor(2)).toEqual(['admin']);
		expect(res.calls.argsFor(3)).toEqual(['apiKeys']);
		expect(res.calls.argsFor(4)).toEqual(['block']);
		expect(res.calls.argsFor(5)).toEqual(['common']);
		expect(res.calls.argsFor(6)).toEqual(['panel']);
		expect(res.calls.argsFor(7)).toEqual(['admin']);
		expect(res.calls.argsFor(8)).toEqual(['apiKeys']);
		expect(res.calls.argsFor(9)).toEqual(['unblock']);
		expect(res.calls.argsFor(10)).toEqual(['common']);
		expect(res.calls.argsFor(11)).toEqual(['panel']);
		expect(res.calls.argsFor(12)).toEqual(['admin']);
		expect(res.calls.argsFor(13)).toEqual(['accounts']);
	}));

	describe('after initialization', () => {
		beforeEach(() => {
			restClientMock.common = {
				panel: {
					admin: {
						apiKeys: {
							post: (searchCriteria: any) => {
								expect(searchCriteria).toBe(API_KEYS_SEARCH_CRITERIA);
								return Promise.resolve(API_KEYS_SEARCH_CRITERIA_RESULT);
							},
							block: {
								post: (block: any) => {
									expect(block).toBe(BLOCK);
									return Promise.resolve(BLOCK_RESULT);
								}
							},
							unblock: {
								post: (unblock: any) => {
									expect(unblock).toBe(UNBLOCK);
									return Promise.resolve(UNBLOCK_RESULT);
								}
							}
						},
						accounts: {
							post: (searchCriteria: any) => {
								expect(searchCriteria).toBe(ACCOUNTS_SEARCH_CRITERIA);
								return Promise.resolve(ACCOUNTS_SEARCH_CRITERIA_RESULT);
							},
						}
					}
				}
			};
		});

		it('searches for API keys', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
			panelAdminManagementApi.searchApiKeys(API_KEYS_SEARCH_CRITERIA).then((response) => {
				expect(response).toBe(API_KEYS_SEARCH_CRITERIA_RESULT);
			});
		}));

		it('block API key', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
			panelAdminManagementApi.blockApiKey(BLOCK).then((response) => {
				expect(response).toBe(BLOCK_RESULT);
			});
		}));

		it('unblock API key', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
			panelAdminManagementApi.unblockApiKey(UNBLOCK).then((response) => {
				expect(response).toBe(UNBLOCK_RESULT);
			});
		}));

		it('searches for API keys', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
			panelAdminManagementApi.searchAccounts(ACCOUNTS_SEARCH_CRITERIA).then((response) => {
				expect(response).toBe(ACCOUNTS_SEARCH_CRITERIA_RESULT);
			});
		}));
	});
});
