import { Component } from '@angular/core';

import { PanelApiKeysApi } from './panel-api-keys-api.service';

@Component({
	selector: 'panel-api-keys',
	templateUrl: './panel-api-keys.component.html',
	styleUrls: ['./panel-api-keys.component.sass']
})
export class PanelApiKeysComponent {

	private panelApiKeysApi: PanelApiKeysApi;

	constructor(panelApiKeysApi: PanelApiKeysApi) {
		this.panelApiKeysApi = panelApiKeysApi;
	}

	createApiKey() {
		this.panelApiKeysApi.createApiKey();
	}

}
