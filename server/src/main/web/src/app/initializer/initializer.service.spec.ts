import { TestBed, inject, fakeAsync, flushMicrotasks } from '@angular/core/testing';

import { InitializerService } from './initializer.service';

import { ApiBrowserApi } from '../api-browser/api-browser-api.service';
import { StatisticsApi } from '../statistics/statistics-api.service';
import { ApiDocumentationApi } from '../api-documentation/api-documentation-api.service';
import { FeatureSwitchApi } from '../feature-switch/feature-switch-api.service';

describe('InitializerService', () => {
	let apiBrowserApiMock: jasmine.SpyObj<ApiBrowserApi>;
	let statisticsApiMock: jasmine.SpyObj<StatisticsApi>;
	let apiDocumentationApiMock: jasmine.SpyObj<ApiDocumentationApi>;
	let featureSwitchApiMock:jasmine.SpyObj< FeatureSwitchApi>;

	beforeEach(() => {
		apiBrowserApiMock = jasmine.createSpyObj('ApiBrowserApi', ['loadDetails'], ['']);
		statisticsApiMock = jasmine.createSpyObj('StatisticsApi', ['loadStatistics'], ['']);
		apiDocumentationApiMock = jasmine.createSpyObj('ApiDocumentationApi', ['loadDocumentation', 'loadDataVersion'], ['']);
		featureSwitchApiMock = jasmine.createSpyObj('FeatureSwitchApi', ['loadFeatureSwitches'], ['']);

		TestBed.configureTestingModule({
			providers: [
				InitializerService,
				{
					provide: ApiBrowserApi,
					useValue: apiBrowserApiMock
				},
				{
					provide: StatisticsApi,
					useValue: statisticsApiMock
				},
				{
					provide: ApiDocumentationApi,
					useValue: apiDocumentationApiMock
				},
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				}
			]
		});
	});

	it('is created', inject([InitializerService], (initializerService: InitializerService) => {
		expect(initializerService).toBeTruthy();
	}));

	it('initializes', fakeAsync(inject([InitializerService], (initializerService: InitializerService) => {
		initializerService.init().then(() => {
			expect(apiBrowserApiMock.loadDetails).toHaveBeenCalled();
			expect(statisticsApiMock.loadStatistics).toHaveBeenCalled();
			expect(apiDocumentationApiMock.loadDocumentation).toHaveBeenCalled();
			expect(apiDocumentationApiMock.loadDataVersion).toHaveBeenCalled();
			expect(featureSwitchApiMock.loadFeatureSwitches).toHaveBeenCalled();
		});
		flushMicrotasks();
	})));
});
