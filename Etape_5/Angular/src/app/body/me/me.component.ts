import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { UserService } from '../../services/user.service';
import { Observable } from 'rxjs';
import { UserDTO } from 'src/app/dto/UserDTO';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddressComponent } from '../address/address.component';
@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  username: string;
  userRole$: Observable<string>;
  token: string;
  newUsername: string; 
  userId: number; 
  addresses: any[];
  constructor(private authService: AuthenticationService, private userService: UserService,private modalService: NgbModal ) { }
  
  ngOnInit(): void {
    this.userService.whoami().subscribe(
      (result) => {
        console.log(result);
        this.username = result.username;
        this.userRole$ = this.userService.getUserRole();
        this.token = result.token;
        this.userId = result.id; // Stockez l'ID de l'utilisateur
        this.getUserAddresses();
      },
      (error) => {
        console.log(error);
        // Gérez les erreurs ici
      }
    );
    
  }


  getUsername(): string | null {
    const loginData = localStorage.getItem('login');
    if (loginData) {
      const monObjet = JSON.parse(loginData);
      return monObjet.username;
    }
    return null;
  }

  getToken(): string | null {
    const loginData = localStorage.getItem('login');
    if (loginData) {
      const monObjet = JSON.parse(loginData);
      return monObjet.token;
    }
    return null;
  }
    // Méthode pour mettre à jour le nom d'utilisateur
    updateUsername() {
      // Vérifiez si le nouveau nom d'utilisateur est défini
      if (this.newUsername) {
        // Utilisez le service UserService pour mettre à jour le nom d'utilisateur
        const userId = this.userId; // Ajoutez une méthode pour obtenir l'ID de l'utilisateur connecté si nécessaire
        this.userService.updateUsername(userId, this.newUsername).subscribe(
          (result) => {
            // Mise à jour réussie, mettez à jour la propriété username
            this.username = this.newUsername;
            // Réinitialisez le champ de saisie
            this.newUsername = '';
          },
          (error) => {
            console.error('Erreur lors de la mise à jour du nom d\'utilisateur :', error);
          }
        );
      }
    }
    
    // Méthode pour récupérer les adresses de l'utilisateur
    getUserAddresses() {
      this.userService.getUserAddresses(this.username).subscribe(
        (result: UserDTO) => {
          console.log(result);
          // Vous pouvez maintenant obtenir les adresses directement depuis result.addressesList
          this.addresses = result.addressesList;
        },
        (error) => {
          console.error('Erreur lors de la récupération des adresses :', error);
        }
      );
    }

    openAddressDialog() {
      const modalRef = this.modalService.open(AddressComponent, { size: 'lg' }); // 'lg' pour une boîte de dialogue de grande taille, ajustez-la selon vos besoins
      modalRef.result.then((result) => {
        if (result === 'adresseAjoutee') {
          // Si une adresse a été ajoutée, rechargez la liste des adresses
          this.getUserAddresses();
        }
      }).catch((error) => {
        console.log("Boîte de dialogue fermée sans ajout d'adresse :", error);
      });
    }
}
