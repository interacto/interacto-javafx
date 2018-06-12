import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TitleComponent } from './Component/title/title.component';
import { NewButtonComponent } from './Component/menu-button/new-button/new-button.component';
import { SaveButtonComponent } from './Component/menu-button/save-button/save-button.component';
import {MenuButtonComponent} from './Component/menu-button/menu-button.component';
import { FSMpartSelectorComponent } from './Component/fsmpart-selector/fsmpart-selector.component';
import { FSMpartComponent } from './Component/fsmpart-selector/fsmpart/fsmpart.component';
import { XmlViewComponent } from './View/xml-view/xml-view.component';

@NgModule({
  declarations: [
    AppComponent,
    TitleComponent,
    NewButtonComponent,
    SaveButtonComponent,
    MenuButtonComponent,
    FSMpartSelectorComponent,
    FSMpartComponent,
    XmlViewComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
