import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelGerFilasAcquirerComponent } from './ger-filas-acquirer.component';

describe('RelGerFilasAcquirerComponent', () => {
  let component: RelGerFilasAcquirerComponent;
  let fixture: ComponentFixture<RelGerFilasAcquirerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelGerFilasAcquirerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelGerFilasAcquirerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
