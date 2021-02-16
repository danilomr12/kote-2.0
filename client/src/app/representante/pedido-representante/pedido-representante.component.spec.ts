import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PedidoRepresentanteComponent} from './pedido-representante.component';

describe('PedidoRepresentanteComponent', () => {
  let component: PedidoRepresentanteComponent;
  let fixture: ComponentFixture<PedidoRepresentanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PedidoRepresentanteComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PedidoRepresentanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
