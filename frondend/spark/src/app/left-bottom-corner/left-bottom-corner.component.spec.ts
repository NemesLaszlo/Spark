import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftBottomCornerComponent } from './left-bottom-corner.component';

describe('LeftBottomCornerComponent', () => {
  let component: LeftBottomCornerComponent;
  let fixture: ComponentFixture<LeftBottomCornerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeftBottomCornerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeftBottomCornerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
