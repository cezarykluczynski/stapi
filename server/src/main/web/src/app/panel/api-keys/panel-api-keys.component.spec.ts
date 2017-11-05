import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelApiKeysComponent } from './panel-api-keys.component';

describe('PanelApiKeysComponent', () => {
	let component: PanelApiKeysComponent;
	let fixture: ComponentFixture<PanelApiKeysComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [PanelApiKeysComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PanelApiKeysComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
