import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionnaireUserComponent } from './gestionnaire-user.component';

describe('GestionnaireUserComponent', () => {
  let component: GestionnaireUserComponent;
  let fixture: ComponentFixture<GestionnaireUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionnaireUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionnaireUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
