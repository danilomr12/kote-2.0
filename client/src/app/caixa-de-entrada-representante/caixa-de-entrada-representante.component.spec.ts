import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CaixaDeEntradaRepresentanteComponent} from './caixa-de-entrada-representante.component';

describe('CaixaDeEntradaRepresentanteComponent', () => {
  let component: CaixaDeEntradaRepresentanteComponent;
  let fixture: ComponentFixture<CaixaDeEntradaRepresentanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CaixaDeEntradaRepresentanteComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CaixaDeEntradaRepresentanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
