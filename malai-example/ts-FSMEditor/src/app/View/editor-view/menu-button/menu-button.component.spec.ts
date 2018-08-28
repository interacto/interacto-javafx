import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuButtonComponent } from './menu-button.component';
import {NewButtonComponent} from './new-button/new-button.component';
import {TitleComponent} from '../title/title.component';
import {SaveButtonComponent} from './save-button/save-button.component';

describe('MenuButtonComponent', () => {
  let component: MenuButtonComponent;
  let fixture: ComponentFixture<MenuButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuButtonComponent, NewButtonComponent, TitleComponent, SaveButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeDefined();
  });
});
