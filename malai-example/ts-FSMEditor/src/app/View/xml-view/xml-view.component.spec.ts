import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { XmlViewComponent } from './xml-view.component';

describe('XmlViewComponent', () => {
  let component: XmlViewComponent;
  let fixture: ComponentFixture<XmlViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ XmlViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(XmlViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
