import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import {User} from '../../shared/model/User';
import {AuthenticationService} from '../../shared/services/authentication.service';
import {UserService} from '../../shared/services/user.service';
import { UserDTO } from 'src/app/dto/UserDTO';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserDetailsModalComponent } from '../user-details-modal/user-details-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestionnaire-user',
  templateUrl: './gestionnaire-user.component.html',
  styleUrls: ['./gestionnaire-user.component.scss']
})
export class GestionnaireUserComponent implements OnInit , OnChanges{
  public list_user: UserDTO[] = [];
  selectedUserId: number | null = null;
  selectedUserDetails: { username?: string; role?: string } = {};
  constructor(public userService: UserService, private modalService: NgbModal, private router: Router) { }
  ngOnChanges(changes: SimpleChanges): void {
    // Votre logique pour actualiser la liste ici
    if (changes.list_user) {
      // Le changement de list_user a été détecté, vous pouvez mettre à jour la liste ici
      console.log('Liste d\'utilisateurs mise à jour :', this.list_user);
    }
  }
  

  ngOnInit(): void {
    this.getAllUsers();
  }



  getAllUsers() {
    this.userService.getUserDTOList(
        (result: UserDTO[]) => {
            console.log(result);
            this.list_user = result;
            console.log(this.list_user)
        },
        (error) => {
            console.log(error);
        },
        "http://127.0.0.1:8090/user"
    );
}



  openUserDetailsModal(user: UserDTO) {
    const modalRef = this.modalService.open(UserDetailsModalComponent);
    modalRef.componentInstance.user = user;
    // Stocker les détails de l'utilisateur sélectionné
  this.selectedUserId = user.id;
  this.selectedUserDetails = { username: user.username, role: user.role };
  }

  updateUser() {
    if (this.selectedUserId !== null) {
      this.userService
        .updateUserDetails(this.selectedUserId, this.selectedUserDetails)
        .subscribe(
          (result) => {
            console.log('Détails de l\'utilisateur mis à jour avec succès:', result);
            // Réinitialiser les valeurs
            this.selectedUserId = null;
            this.selectedUserDetails = {};
          },
          (error) => {
            console.error('Erreur lors de la mise à jour des détails de l\'utilisateur:', error);
          }
        );
    }
  }
  deleteUser(userId: number) {
    this.userService.deleteUser(userId).subscribe(
      (result: string) => {
        console.log(result);
        // Actualisez list_user ici ou émettez un événement pour signaler le changement
        this.getAllUsers();
        // Forcez le rechargement de la page
        window.location.reload();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  
  
}
