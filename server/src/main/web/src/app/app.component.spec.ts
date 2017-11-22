import { TestBed, async } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';

import {RouterTestingModule} from "@angular/router/testing";
import {Router} from "@angular/router";

describe('AppComponent', () => {
	beforeEach(async(() => {
		TestBed.configureTestingModule({
			imports: [RouterTestingModule.withRoutes([])],
			schemas: [NO_ERRORS_SCHEMA],
			declarations: [AppComponent],
		}).compileComponents();
	}));

	it('creates the app', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		expect(app).toBeTruthy();
	}));

	it('returns GitHub star button', async(() => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.debugElement.componentInstance;
		const gitHubButtonHtml = fixture.componentInstance.getGitHubStar()['changingThisBreaksApplicationSecurity'];

		expect(gitHubButtonHtml).toContain('github.com/cezarykluczynski/stapi');
		expect(gitHubButtonHtml).toContain('Star cezarykluczynski/stapi on GitHub');
	}));
});
