import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FSMpartSelectorComponent } from './fsmpart-selector.component';

describe('FSMpartSelectorComponent', () => {
  let component: FSMpartSelectorComponent;
  let fixture: ComponentFixture<FSMpartSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FSMpartSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FSMpartSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeDefined();
  });
});
