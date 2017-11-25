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

		expect(res.calls.count()).toBe(4);
		expect(res.calls.argsFor(0)).toEqual(['panel']);
		expect(res.calls.argsFor(1)).toEqual(['accountSettings']);
		expect(res.calls.argsFor(2)).toEqual(['panel']);
		expect(res.calls.argsFor(3)).toEqual(['basicData']);
	}));

	describe('after initialization', () => {
		const DELETE_RESULT = {
			successful: true
		};
		const UPDATE_RESULT = {
			successful: true
		}

		beforeEach(() => {
			restClientMock.panel = {};
			restClientMock.panel.accountSettings = {
				delete: () => {
					return Promise.resolve(DELETE_RESULT);
				}
			};
			restClientMock.panel.basicData = {
				post: () => {
					return Promise.resolve(UPDATE_RESULT);
				}
			}
		});

		it('removes account', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.removeAccount().then((response) => {
				expect(response).toBe(DELETE_RESULT);
			});
		}));

		it('updates basic data', inject([PanelAccountApi], (panelAccountApi: PanelAccountApi) => {
			panelAccountApi.updateBasicData({}).then((response) => {
				expect(response).toBe(UPDATE_RESULT);
			});
		}));
	});
});
