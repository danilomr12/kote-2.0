import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RepresentanteViewComponent} from './representante-view.component';

describe('RepresentanteViewComponent', () => {
  let component: RepresentanteViewComponent;
  let fixture: ComponentFixture<RepresentanteViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RepresentanteViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RepresentanteViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
