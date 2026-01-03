import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Address } from 'src/app/modele/Address';
import { AddressService } from 'src/app/services/address.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AddressDetailsModalComponent } from '../address-details-modal/address-details-modal.component';

@Component({
  selector: 'app-gestionnaire-address',
  templateUrl: './gestionnaire-address.component.html',
  styleUrls: ['./gestionnaire-address.component.scss']
})
export class GestionnaireAddressComponent implements OnInit {
  addresses: Address[] = [];
  token: string; 
  selectedAddress: Address | null = null;
  updatedAddress: Address = new Address();
  constructor(private addressService: AddressService, private authService: AuthenticationService,  private modalService: NgbModal ) { }

  ngOnInit(): void {
    this.token = this.authService.getToken();

    // Chargez les adresses lors de l'initialisation du composant en incluant le token
    this.getAddresses();
  }

  getAddresses() {
    this.addressService.getAllAddresses().subscribe(
      (result: Address[]) => {
        this.addresses = result;
      },
      (error) => {
        console.error('Erreur lors de la récupération des adresses :', error);
      }
    );
  }

  
  // Fonction appelée lorsque vous cliquez sur une adresse de la liste
// Fonction appelée lorsque vous cliquez sur une adresse de la liste
onAddressClick(address: Address) {
  this.selectedAddress = address;

  // Ouvrir la boîte de dialogue des détails de l'adresse
  const modalRef = this.modalService.open(AddressDetailsModalComponent);
  modalRef.componentInstance.address = address;
}
  // Fonction appelée lorsque vous cliquez sur le bouton "Modifier"
  updateAddress() {
    if (this.selectedAddress) {
      // Appelez la méthode du service pour mettre à jour l'adresse avec le jeton
      this.addressService.updateAddressWithToken(this.selectedAddress.id, this.selectedAddress)
        .subscribe(
          (result) => {
            console.log('Adresse mise à jour avec succès :', result);
            // Mettez à jour l'adresse sélectionnée avec la réponse du backend
            this.selectedAddress = result;
          },
          (error) => {
            console.error('Erreur lors de la mise à jour de l\'adresse :', error);
          }
        );
    }
  }
  
  

  // Fonction appelée lorsque vous cliquez sur le bouton "Supprimer"
  onDeleteClick(addressId: number) {
    if (addressId) {
        // Appelez la méthode du service pour supprimer l'adresse avec le jeton
        this.addressService.deleteAddressWithToken(addressId)
            .subscribe(
                () => {
                    console.log('Adresse supprimée avec succès');
                    // Supprimez l'adresse sélectionnée de la liste des adresses
                    this.addresses = this.addresses.filter((address) => address.id !== addressId);
                    // Réinitialisez la sélection
                    this.selectedAddress = null;
                },
                (error) => {
                    console.error('Erreur lors de la suppression de l\'adresse :', error);
                }
            );
    }
}
  
}
