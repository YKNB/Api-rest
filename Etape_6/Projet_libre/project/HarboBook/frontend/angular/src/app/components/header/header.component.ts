import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {AuthenticationService} from '../../shared/services/authentication.service';
import { UserService } from '../../shared/services/user.service';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  [x: string]: any;
  introLogo: string;
  public isMenuCollapsed = true;
  username: string= "username";
  password: string= "password";
  hide_admin_menu:boolean=true;
  role:string;
  signinUrl: string = 'http://127.0.0.1:8090/authenticate';
  userRole$: Observable<string> = new Observable<string>();



  constructor(public authService: AuthenticationService, private userService: UserService) {
    this.introLogo = environment.images_urls + 'etna-logo-1-quadri.png';
  }

  ngOnInit(): void {
    this.checkAutority();
    this.hide_admin_menu = this.role=="ROLE_USER";
    this.userRole$ = this.userService.getUserRole(); 
    console.log(this.role);
    console.log("hide menu :"+ this.hide_admin_menu);
  }

  j(){
   return this.authService.whoami(
      (result) => {
       return result.role;
      },
      (error) => {
        console.log(error);
        return null;
      }, "http://127.0.0.1:8090/me");
  }

  checkAutority() {
    this.userService.whoami().subscribe(
      (result) => {
        console.log(result);
        this.role = result.role;
        console.log(this.role);
      },
      (error) => {
        console.log(error);
        this.role = "";
      }
    );
    console.log(this.role);
  }

  makeAuth() {
    this.authService.makeAuth(
      (result) => {
        console.log(result);
        console.log(result.token);
         // Accéder au rôle depuis AuthenticationService
      // Mettre à jour la valeur de userRole$ avec le rôle résultant
      this.userRole$ = of(result.role);
      console.log(of(result.role));
      this.successMessage = 'Authentication réussie !'; // Message de succès
      this.errorMessage = ''; // Réinitialise le message d'erreur
      },
      (error) => {
        console.log(error);
        this.successMessage = ''; // Réinitialise le message de succès
        this.errorMessage = 'Erreur lors de l\'authentication. Veuillez réessayer.'; // Message d'erreur
      }, this.username,
      this.password, this.signinUrl
    );
  }

  delete(){
    console.log("suppression");
    this.authService.removeUser(
      (result) => {
        console.log(result);
        this.authService.logout();
        console.log("success delete");
      },
      (error) => {
        console.log("echec supprimer");
        console.log(error);
      },'http://127.0.0.1:8090/delete'
    )
  }

  disconnected() {
    this.authService.logout();
  }

  checkConnected(){
    return this.authService.checkLogin();
  }

  // AuthenticationService (auth.service.ts)
getUsername(): string | null {
  const loginData = localStorage.getItem('login');
  if (loginData) {
    const monObjet = JSON.parse(loginData);
    return monObjet.username;
  }
  return null;
}

getRole() {
 
}

getToken(): string | null {
  const loginData = localStorage.getItem('login');
  if (loginData) {
    const monObjet = JSON.parse(loginData);
    return monObjet.token;
  }
  return null;
}
}
