import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CompradorViewComponent} from './comprador-view.component';

describe('CompradorViewComponent', () => {
  let component: CompradorViewComponent;
  let fixture: ComponentFixture<CompradorViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompradorViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompradorViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
