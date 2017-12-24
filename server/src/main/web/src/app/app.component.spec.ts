import { TestBed, async } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';

import {RouterTestingModule} from "@angular/router/testing";
import {Router} from "@angular/router";

class FeatureSwitchApiMock {
	public isEnabled() {}
}

describe('AppComponent', () => {
	let featureSwitchApiMock: FeatureSwitchApiMock;

	beforeEach(async(() => {
		featureSwitchApiMock = new FeatureSwitchApiMock();

		TestBed.configureTestingModule({
			imports: [RouterTestingModule.withRoutes([])],
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [AppComponent],
			providers: [
				{
					provide: FeatureSwitchApi,
					useValue: featureSwitchApiMock
				}
			]
		}).compileComponents();
	}));

	it('creates the app', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));

	it('returns GitHub star button', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const gitHubButtonHtml = fixture.componentInstance.getGitHubStar()['changingThisBreaksApplicationSecurity'];

		expect(gitHubButtonHtml).toContain('github.com/cezarykluczynski/stapi');
		expect(gitHubButtonHtml).toContain('Star cezarykluczynski/stapi on GitHub');
	}));

	it('tells whether panel is enabled', async(() => {
		const fixture = TestBed.createComponent(AppComponent);

		spyOn(featureSwitchApiMock, 'isEnabled').and.returnValue(true);

		const panelIsEnabled = fixture.componentInstance.panelIsEnabled();

		expect(featureSwitchApiMock.isEnabled).toHaveBeenCalledWith('PANEL');
		expect(panelIsEnabled).toBeTrue();
	}));
});
