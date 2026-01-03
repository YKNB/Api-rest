import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionnaireBookComponent } from './gestionnaire-book.component';

describe('GestionnaireBookComponent', () => {
  let component: GestionnaireBookComponent;
  let fixture: ComponentFixture<GestionnaireBookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionnaireBookComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionnaireBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
