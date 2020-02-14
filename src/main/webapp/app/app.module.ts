import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RenseignementSharedModule } from 'app/shared/shared.module';
import { RenseignementCoreModule } from 'app/core/core.module';
import { RenseignementAppRoutingModule } from './app-routing.module';
import { RenseignementHomeModule } from './home/home.module';
import { RenseignementEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RenseignementSharedModule,
    RenseignementCoreModule,
    RenseignementHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RenseignementEntityModule,
    RenseignementAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class RenseignementAppModule {}
