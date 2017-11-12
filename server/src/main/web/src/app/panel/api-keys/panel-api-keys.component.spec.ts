import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelApiKeysComponent } from './panel-api-keys.component';
import { PanelApiKeysApi } from './panel-api-keys-api.service';

class PanelApiKeysApiMock {
	public createApiKey() {}
}

describe('PanelApiKeysComponent', () => {
	let component: PanelApiKeysComponent;
	let fixture: ComponentFixture<PanelApiKeysComponent>;
	let panelApiKeysApiMock: PanelApiKeysApiMock;

	beforeEach(async(() => {
		panelApiKeysApiMock = new PanelApiKeysApiMock();

		TestBed.configureTestingModule({
			declarations: [PanelApiKeysComponent],
			providers: [
				{
					provide: PanelApiKeysApi,
					useValue: panelApiKeysApiMock
				}
			]
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
