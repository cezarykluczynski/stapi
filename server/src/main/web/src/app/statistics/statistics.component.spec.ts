import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { StatisticsComponent } from './statistics.component';

describe('EntityHitsGridComponent', () => {
	let component: StatisticsComponent;
	let fixture: ComponentFixture<StatisticsComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [StatisticsComponent],
			schemas: [NO_ERRORS_SCHEMA]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(StatisticsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
