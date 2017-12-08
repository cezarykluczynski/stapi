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
	const GET_RESULT = {
		apiKeys: {},
		pager: {}
	};
	const BLOCK_RESULT = {
		successful: true
	};
	const UNBLOCK_RESULT = {
		successful: true
	};

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

		expect(res.calls.count()).toBe(8);
		expect(res.calls.argsFor(0)).toEqual(['panel']);
		expect(res.calls.argsFor(1)).toEqual(['admin']);
		expect(res.calls.argsFor(2)).toEqual(['apiKeys']);
		expect(res.calls.argsFor(3)).toEqual(['block']);
		expect(res.calls.argsFor(4)).toEqual(['panel']);
		expect(res.calls.argsFor(5)).toEqual(['admin']);
		expect(res.calls.argsFor(6)).toEqual(['apiKeys']);
		expect(res.calls.argsFor(7)).toEqual(['unblock']);
	}));

	describe('after initialization', () => {
		beforeEach(() => {
			restClientMock.panel = {
				admin: {
					apiKeys: {
						get: (data: any) => {
							expect(data.pageNumber).toBe(PAGE_NUMBER);
							return Promise.resolve(GET_RESULT);
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
					}
				}
			};
		});

		it('gets API keys page', inject([PanelAdminManagementApi], (panelAdminManagementApi: PanelAdminManagementApi) => {
			panelAdminManagementApi.getApiKeysPage(PAGE_NUMBER).then((response) => {
				expect(response).toBe(GET_RESULT);
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
	});
});
