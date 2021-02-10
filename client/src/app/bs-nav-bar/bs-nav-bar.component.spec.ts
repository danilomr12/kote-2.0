import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BsNavBarComponent} from './bs-nav-bar.component';

describe('BsNavBarComponent', () => {
  let component: BsNavBarComponent;
  let fixture: ComponentFixture<BsNavBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BsNavBarComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BsNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
