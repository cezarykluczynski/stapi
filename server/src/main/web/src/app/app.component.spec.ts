import { TestBed, async } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { PanelApi } from './panel/panel-api.service';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';
import {RouterTestingModule} from '@angular/router/testing';

class FeatureSwitchApiMock {
	public isEnabled() {}
}

class PanelApiMock {
	public getGitHubProjectDetails() {}
}

class ApiDocumentationApiMock {
	public getDataVersion() {}
}

describe('AppComponent', () => {
	let featureSwitchApiMock: FeatureSwitchApiMock;
	let panelApiMock: PanelApiMock;
	let apiDocumentationApiMock: ApiDocumentationApiMock;
	let resolve;

	beforeEach(async(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();
		panelApiMock = new PanelApiMock();
		apiDocumentationApiMock = new ApiDocumentationApiMock();

		TestBed.configureTestingModule({
			imports: [RouterTestingModule.withRoutes([])],
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [AppComponent],
			providers: [
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				},
				{
					provide: PanelApi,
					useValue: panelApiMock
				},
				{
					provide: ApiDocumentationApi,
					useValue: apiDocumentationApiMock
				}
			]
		}).compileComponents();

		spyOn(panelApiMock, 'getGitHubProjectDetails').and.returnValue(new Promise((_resolve) => {
			resolve = () => {
				_resolve({
					stargazersCount: 23
				});
			};
		}));
	}));

	it('creates the app', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));

	it('tells whether panel is enabled', async(() => {
		const fixture = TestBed.createComponent(AppComponent);

		const isEnabled: jasmine.Spy = spyOn(featureSwitchApiMock, 'isEnabled');

		isEnabled.and.callFake((featureSwitchName) => {
			return featureSwitchName === 'ADMIN_PANEL';
		});

		const panelIsEnabled = fixture.componentInstance.panelIsEnabled();

		expect(isEnabled.calls.argsFor(0)[0]).toBe('USER_PANEL');
		expect(isEnabled.calls.argsFor(1)[0]).toBe('ADMIN_PANEL');
		expect(panelIsEnabled).toBeTrue();
	}));

	it('load stargazers count from API', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		expect(fixture.componentInstance.hasGitHubProjectDetails()).toBeFalse();

		fixture.componentInstance.ngOnInit();
		resolve();
		fixture.detectChanges();

		fixture.whenStable().then(() => {
			expect(fixture.componentInstance.hasGitHubProjectDetails()).toBeTrue();
			expect(fixture.componentInstance.getGitHubStarsCount()).toBe(23);
		});
	}));
});
