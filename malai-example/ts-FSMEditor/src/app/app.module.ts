import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TitleComponent } from './View/editor-view/title/title.component';
import { NewButtonComponent } from './View/editor-view/menu-button/new-button/new-button.component';
import { SaveButtonComponent } from './View/editor-view/menu-button/save-button/save-button.component';
import {MenuButtonComponent} from './View/editor-view/menu-button/menu-button.component';
import {FSMpartSelectorComponent} from './View/editor-view/fsmpart-selector/fsmpart-selector.component';
import { FSMpartComponent } from './View/editor-view/fsmpart-selector/fsmpart/fsmpart.component';
import { XmlViewComponent } from './View/xml-view/xml-view.component';
import { EditorViewComponent } from './View/editor-view/editor-view.component';
import {TabsComponent} from './Component/tabs/tabs.component';
import {TabComponent} from './Component/tabs/tab/tab.component';
import { DrawboxComponent } from './View/editor-view/DrawBox/drawbox/drawbox.component';

@NgModule({
  declarations: [
    AppComponent,
    TitleComponent,
    NewButtonComponent,
    SaveButtonComponent,
    MenuButtonComponent,
    FSMpartSelectorComponent,
    FSMpartComponent,
    XmlViewComponent,
    EditorViewComponent,
    TabsComponent,
    TabComponent,
    DrawboxComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
