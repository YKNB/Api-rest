import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../modele/User';
import {NgForm} from '@angular/forms';
import {JsonObject} from '@angular/compiler-cli/ngcc/src/packages/entry_point';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  public actionButton: string = 'Créer';
  user:User= new User();
  constructor(private userservice: UserService, private authentification:AuthenticationService) { }

  ngOnInit(): void {
  }

  /**
   * Method that save in the Backend server,
   *  a new customer data retreived from the form
   * @param addUserForm
   */
  saveOrUpdateCustomer(addUserForm: NgForm){
    if(!addUserForm.valid) console.log("formulaire ajout erreur");
    else if ( this.authentification.checkLogin()) {
      this.register(this.user);
      this.authentification.makeAuth(
        (result) => {
          console.log("user créer" )
        },
        (error) => {
          console.log("user pas créer  ");
        }, this.user.username,
        this.user.password,'http://127.0.0.1:8090/authenticate')
      this.actionButton = 'Modifier';
    }
    else  this.updateCustomer(this.user);
  }

  updateCustomer(user: User) {
        throw new Error('Method not implemented.');
    }


  creerUser(addUserForm: NgForm){
    if(!addUserForm.valid){
      console.log("invalid creation user");
    }
    if(this.actionButton){
      this.register(this.user);
      console.log("creation user success");
    }
  }


  register(user: User) {
    this.userservice.saveUser(
      (result: User) => {
        console.log(result);
      },
      (error) => {
        console.log(error);
      }, user
    );
  }






  /**
   * Erase all data from this NgForm.
   * @param addCustomerForm
   */
  clearForm(addUserForm: NgForm){
    addUserForm.form.reset();
  }


}
