import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AgGridModule } from 'ag-grid-angular/main';
import * as hljs from 'highlight.js';
import { HighlightJsModule, HIGHLIGHT_JS } from 'angular-highlight-js';
import { CookieService } from 'ngx-cookie-service';
import { SimpleNotificationsModule } from 'angular2-notifications';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

export function highlightJsFactory() {
	return hljs;
}

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ApiBrowserComponent } from './api-browser/api-browser.component';
import { ApiBrowserApi } from './api-browser/api-browser-api.service';
import { ApiDocumentationComponent } from './api-documentation/api-documentation.component';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';
import { LicensingComponent } from './licensing/licensing.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { StatisticsApi } from './statistics/statistics-api.service';
import { EntityStatisticsCloudComponent } from './statistics/entity-statistics-cloud.component';
import { EntityHitsGridComponent } from './statistics/entity-hits-grid.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RestApiService } from './rest-api/rest-api.service';
import { InitializerService } from './initializer/initializer.service';
import { RestClientFactoryService } from './rest-api/rest-client-factory.service';
import { WindowReferenceService } from './window-reference/window-reference.service';
import { InfoComponent } from './info/info.component';
import { PanelComponent } from './panel/panel.component';
import { PanelApi } from './panel/panel-api.service';
import { PanelApiKeysComponent } from './panel/api-keys/panel-api-keys.component';
import { PanelApiKeysApi } from './panel/api-keys/panel-api-keys-api.service';
import { PanelAccountSettingsComponent } from './panel/account-settings/panel-account-settings.component';
import { PanelAccountApi } from './panel/account-settings/panel-account-api.service';
import { PanelAdminManagementApi } from './panel/admin-management/panel-admin-management-api.service';
import { PanelAdminApiKeysComponent } from './panel/admin-management/panel-admin-api-keys/panel-admin-api-keys.component';
import { PanelAdminAccountsComponent } from './panel/admin-management/panel-admin-accounts/panel-admin-accounts.component';
import { ApiOverviewComponent } from './api-overview/api-overview.component';
import { LegalComponent } from './legal/legal.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { APP_ROUTES } from './app.routes';

export function initConfiguration(initializerService: InitializerService): Function {
	return () => initializerService.init();
}

@NgModule({
	declarations: [
		AppComponent,
		HomeComponent,
		AboutComponent,
		ApiBrowserComponent,
		ApiDocumentationComponent,
		LicensingComponent,
		StatisticsComponent,
		EntityStatisticsCloudComponent,
		EntityHitsGridComponent,
		PageNotFoundComponent,
		InfoComponent,
		PanelComponent,
		PanelApiKeysComponent,
		PanelAccountSettingsComponent,
		PanelAdminApiKeysComponent,
		PanelAdminAccountsComponent,
		ApiOverviewComponent,
		LegalComponent
	],
	imports: [
		RouterModule.forRoot(APP_ROUTES),
		BrowserModule,
		BrowserAnimationsModule,
		FormsModule,
		ReactiveFormsModule,
		AgGridModule.withComponents([]),
		HighlightJsModule.forRoot({
			provide: HIGHLIGHT_JS,
			useFactory: highlightJsFactory
		}),
		SimpleNotificationsModule.forRoot(),
		NgbModule.forRoot()
	],
	providers: [
		{
			provide: APP_INITIALIZER,
			useFactory: initConfiguration,
			multi: true,
			deps: [InitializerService]
		},
		InitializerService,
		ApiBrowserApi,
		ApiDocumentationApi,
		RestApiService,
		PanelAdminManagementApi,
		PanelAccountApi,
		PanelApiKeysApi,
		PanelApi,
		StatisticsApi,
		RestClientFactoryService,
		WindowReferenceService,
		CookieService,
		FeatureSwitchApi
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
