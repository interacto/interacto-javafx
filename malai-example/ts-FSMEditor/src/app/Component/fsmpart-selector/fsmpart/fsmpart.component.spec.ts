import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FSMpartComponent } from './fsmpart.component';

describe('FSMpartComponent', () => {
  let component: FSMpartComponent;
  let fixture: ComponentFixture<FSMpartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FSMpartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FSMpartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
