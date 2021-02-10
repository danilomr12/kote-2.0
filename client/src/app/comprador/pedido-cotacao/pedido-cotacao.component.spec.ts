import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PedidoCotacaoComponent} from './pedido-cotacao.component';

describe('PedidoCotacaoComponent', () => {
  let component: PedidoCotacaoComponent;
  let fixture: ComponentFixture<PedidoCotacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PedidoCotacaoComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PedidoCotacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
