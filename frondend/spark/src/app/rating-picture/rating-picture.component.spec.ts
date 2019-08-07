import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingPictureComponent } from './rating-picture.component';

describe('RatingPictureComponent', () => {
  let component: RatingPictureComponent;
  let fixture: ComponentFixture<RatingPictureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatingPictureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingPictureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
