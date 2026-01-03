import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.checkLogin()) {
      return true;
    } else {
      // Redirigez vers la page de connexion si l'utilisateur n'est pas authentifié.
      this.router.navigate(['/home']); // Remplacez '/login' par l'URL de votre page de connexion.
      return false;
    }
  }
}