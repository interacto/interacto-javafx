import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrawboxComponent } from './drawbox.component';

describe('DrawboxComponent', () => {
  let component: DrawboxComponent;
  let fixture: ComponentFixture<DrawboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DrawboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrawboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
