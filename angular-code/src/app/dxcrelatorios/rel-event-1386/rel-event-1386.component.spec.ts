import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelEvent1386Component } from './rel-event-1386.component';

describe('RelEvent1386Component', () => {
  let component: RelEvent1386Component;
  let fixture: ComponentFixture<RelEvent1386Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelEvent1386Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelEvent1386Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
