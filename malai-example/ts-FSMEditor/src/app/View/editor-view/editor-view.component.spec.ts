import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditorViewComponent } from './editor-view.component';
import {FSMpartSelectorComponent} from './fsmpart-selector/fsmpart-selector.component';
import {DrawboxComponent} from './DrawBox/drawbox/drawbox.component';
import {MenuButtonComponent} from './menu-button/menu-button.component';
import {TitleComponent} from './title/title.component';

describe('EditorViewComponent', () => {
  let component: EditorViewComponent;
  let fixture: ComponentFixture<EditorViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        EditorViewComponent,
        FSMpartSelectorComponent,
        DrawboxComponent,
        MenuButtonComponent,
        TitleComponent
      ]
    });
    fixture = TestBed.createComponent(EditorViewComponent);
    component = fixture.componentInstance;
  }));

  test('should create', () => {
    expect(component).toBeDefined();
  });
});
