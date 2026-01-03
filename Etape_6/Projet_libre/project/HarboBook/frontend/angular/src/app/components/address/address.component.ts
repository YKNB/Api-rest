import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {Address} from '../../shared/model/Address';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AddressService } from '../../shared/services/address.service';
@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.scss']
})
export class AddressComponent implements OnInit {
  address:Address=new Address();
  visualisation:boolean=true;

  constructor(private activeModal: NgbActiveModal , private addressService: AddressService) { }

  ngOnInit(): void {
  }

  creerAdress(addAddressForm: Address) {
    // Appelez le service pour créer l'adresse
    this.addressService.createAddress(this.address).subscribe(
      (result) => {
        console.log('Adresse créée avec succès :', result);
        // Fermez la boîte de dialogue en renvoyant un résultat
        this.activeModal.close('adresseAjoutee');
      },
      (error) => {
        console.error('Erreur lors de la création de l\'adresse :', error);
      }
    );
  }
}
