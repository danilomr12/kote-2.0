import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NovaCotacaoComponent} from './nova-cotacao.component';

describe('NovaCotacaoComponent', () => {
  let component: NovaCotacaoComponent;
  let fixture: ComponentFixture<NovaCotacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NovaCotacaoComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NovaCotacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
