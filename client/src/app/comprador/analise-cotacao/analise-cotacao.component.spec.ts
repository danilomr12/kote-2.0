import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AnaliseCotacaoComponent} from './analise-cotacao.component';

describe('AnaliseCotacaoComponent', () => {
  let component: AnaliseCotacaoComponent;
  let fixture: ComponentFixture<AnaliseCotacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnaliseCotacaoComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnaliseCotacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
