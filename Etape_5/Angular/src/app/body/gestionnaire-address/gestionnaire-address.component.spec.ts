import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionnaireAddressComponent } from './gestionnaire-address.component';

describe('GestionnaireAddressComponent', () => {
  let component: GestionnaireAddressComponent;
  let fixture: ComponentFixture<GestionnaireAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionnaireAddressComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionnaireAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
