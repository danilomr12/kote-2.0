import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RespostaCotacaoComponent} from './resposta-cotacao.component';

describe('RespostaCotacaoComponent', () => {
  let component: RespostaCotacaoComponent;
  let fixture: ComponentFixture<RespostaCotacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RespostaCotacaoComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RespostaCotacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
