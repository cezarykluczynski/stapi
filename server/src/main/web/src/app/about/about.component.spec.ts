import { fakeAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutComponent } from './about.component';

describe('AboutComponent', () => {
	let component: AboutComponent;
	let fixture: ComponentFixture<AboutComponent>;

	beforeEach(fakeAsync(() => {
		TestBed.configureTestingModule({
			declarations: [AboutComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(AboutComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});
});
