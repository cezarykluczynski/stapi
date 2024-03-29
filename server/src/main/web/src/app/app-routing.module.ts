import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import { AboutComponent } from "./about/about.component";
import { ApiBrowserComponent } from "./api-browser/api-browser.component";
import { ApiDocumentationComponent } from "./api-documentation/api-documentation.component";
import { ClientsComponent } from "./clients/clients.component";
import { LicensingComponent } from "./licensing/licensing.component";
import { ApiOverviewComponent } from "./api-overview/api-overview.component";
import { LegalComponent } from "./legal/legal.component";
import { PageNotFoundComponent } from "./page-not-found/page-not-found.component";

const routes: Routes = [
	{
		path: '',
		component: HomeComponent
	},
	{
		path: 'about',
		component: AboutComponent
	},
	{
		path: 'api-browser',
		component: ApiBrowserComponent
	},
	{
		path: 'api-documentation',
		component: ApiDocumentationComponent
	},
	{
		path: 'clients',
		component: ClientsComponent
	},
	{
		path: 'licensing',
		component: LicensingComponent
	},
	{
		path: 'api-overview',
		component: ApiOverviewComponent
	},
	{
		path: 'privacy-policy',
		component: LegalComponent
	},
	{
		path: 'terms-of-service',
		component: LegalComponent
	},
	{
		path: '**',
		component: PageNotFoundComponent
	}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
