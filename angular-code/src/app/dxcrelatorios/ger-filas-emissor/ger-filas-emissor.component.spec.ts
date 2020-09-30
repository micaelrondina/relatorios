import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelGerFilasEmissorComponent } from './ger-filas-emissor.component';

describe('RelGerFilasEmissorComponent', () => {
  let component: RelGerFilasEmissorComponent;
  let fixture: ComponentFixture<RelGerFilasEmissorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelGerFilasEmissorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelGerFilasEmissorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
