import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RightBottomCornerComponent } from './right-bottom-corner.component';

describe('RightBottomCornerComponent', () => {
  let component: RightBottomCornerComponent;
  let fixture: ComponentFixture<RightBottomCornerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RightBottomCornerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RightBottomCornerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
