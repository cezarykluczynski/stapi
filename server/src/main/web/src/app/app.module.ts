import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ApiBrowserComponent } from './api-browser/api-browser.component';
import { ApiBrowserApi } from './api-browser/api-browser-api.service';
import { ApiDocumentationComponent } from './api-documentation/api-documentation.component';
import { ApiDocumentationApi } from './api-documentation/api-documentation-api.service';
import { ClientsComponent } from './clients/clients.component';
import { LicensingComponent } from './licensing/licensing.component';
import { StatisticsApi } from './statistics/statistics-api.service';
import { EntityStatisticsCloudComponent } from './statistics/entity-statistics-cloud.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RestApiService } from './rest-api/rest-api.service';
import { InitializerService } from './initializer/initializer.service';
import { RestClientFactoryService } from './rest-api/rest-client-factory.service';
import { InfoComponent } from './info/info.component';
import { ApiOverviewComponent } from './api-overview/api-overview.component';
import { LegalComponent } from './legal/legal.component';
import { FeatureSwitchApi } from './feature-switch/feature-switch-api.service';
import { AppRoutingModule } from "./app-routing.module";

import * as $ from 'jquery';

export function initConfiguration(initializerService: InitializerService) {
	return () => initializerService.init().then(() => {
		$('.loader').hide();
	});
}

@NgModule({
	declarations: [
		AppComponent,
		HomeComponent,
		AboutComponent,
		ApiBrowserComponent,
		ApiDocumentationComponent,
		ClientsComponent,
		LicensingComponent,
		EntityStatisticsCloudComponent,
		PageNotFoundComponent,
		InfoComponent,
		ApiOverviewComponent,
		LegalComponent
	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		AppRoutingModule,
		FormsModule,
		ReactiveFormsModule
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
		StatisticsApi,
		RestClientFactoryService,
		FeatureSwitchApi
	],
	bootstrap: [AppComponent]
})
export class AppModule {
}
