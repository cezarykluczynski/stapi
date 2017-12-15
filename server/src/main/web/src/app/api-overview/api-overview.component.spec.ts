import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiOverviewComponent } from './api-overview.component';

describe('ApiOverviewComponent', () => {
	let component: ApiOverviewComponent;
	let fixture: ComponentFixture<ApiOverviewComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ApiOverviewComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ApiOverviewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('is created', () => {
		expect(component).toBeTruthy();
	});
});
