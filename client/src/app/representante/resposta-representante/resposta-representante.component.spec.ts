import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RespostaRepresentanteComponent} from './resposta-representante.component';

describe('RespostaRepresentanteComponent', () => {
  let component: RespostaRepresentanteComponent;
  let fixture: ComponentFixture<RespostaRepresentanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RespostaRepresentanteComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RespostaRepresentanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
