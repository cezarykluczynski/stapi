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
			restClientMock.panel = {};
		});
	});
});
