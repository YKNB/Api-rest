import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Address } from 'src/app/modele/Address';

@Component({
  selector: 'app-address-details-modal',
  templateUrl: './address-details-modal.component.html',
  styleUrls: ['./address-details-modal.component.scss']
})
export class AddressDetailsModalComponent implements OnInit {
  @Input() address: Address;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    // Vous pouvez effectuer des opérations d'initialisation ici si nécessaire
  }
}
