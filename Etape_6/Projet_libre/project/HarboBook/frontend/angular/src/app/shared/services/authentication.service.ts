import {EventEmitter, Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {HttpClientService} from './http-client.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private response: any;

  public tokenResult = new EventEmitter<any>();

  private helper;
  // Utilisez BehaviorSubject pour gérer userRole en tant qu'Observable
  private userRoleSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');

  // Exposez userRole en tant qu'Observable
  public userRole$: Observable<string> = this.userRoleSubject.asObservable();


  constructor(private httpService: HttpClientService,private http: HttpClient ) {
    this.helper = new JwtHelperService();
  }

  makeAuth(onSuccess: Function, onFailure: Function, username: String, password: String, loginUrl: string) {
    let requestParams = {
      username: username,
      password: password
    }

    let postRequest = this.httpService.makePostRequest(loginUrl, requestParams)
    postRequest.subscribe(
      (result) => {
        this.response = result;
        let login = {
          username: requestParams.username,
          token: this.response.token
        }
         // Extraire le rôle de la réponse et le stocker dans une variable de service
         this.userRoleSubject.next(result.role);
         localStorage.setItem('userId', result.userId);
        this.tokenResult.emit(login)
        localStorage.setItem('login', JSON.stringify(login)); // sync the data
        console.log(localStorage.getItem("login").length);
        onSuccess(result)
      },
      error => {
        onFailure(error)
      }
    )
  }

  whoami(onSuccess: Function, onFailure: Function, loginUrl: string) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}` // Ajouter le token d'authentification ici
    });

    const options = { headers: headers };

    this.http.get(loginUrl, options).subscribe(
      (result) => {
        this.response = result;
        onSuccess(result);
        console.log(result);
        return result;
      },
      error => {
        onFailure(error);
        this.logout();
        return error;
      }
    );
  }

  updateUser(onSuccess: Function, onFailure: Function, username: String, password: String, loginUrl: string) {
    let requestParams = {
      username: username,
      password: password
    }

    let putRequest = this.httpService.makePutRequestToken(loginUrl, requestParams)
    putRequest.subscribe(
      (result) => {
        this.response = result;
        let login = {
          username: requestParams.username,
          token: this.response.token
        }
        this.tokenResult.emit(login)
        localStorage.setItem('login', JSON.stringify(login)); // sync the data
        console.log(localStorage.getItem("login"));
        onSuccess(result)
      },
      error => {
        onFailure(error)
      }
    )
  }

  removeUser(onSuccess: Function, onFailure: Function, loginUrl: string) {
    let postRequest = this.httpService.makeDeleteRequestToken(loginUrl);
    postRequest.subscribe(
      (result) => {
        this.response = result;
        onSuccess(result);
      },
      error => {
        onFailure(error);
      }
    );
  }

/*
  checkLogin(){
    let savedToken = JSON.parse(localStorage.getItem('login') || '[]')
    let isExpired = this.helper.isTokenExpired(savedToken.token);
    if(isExpired == false){
      this.tokenResult.emit(savedToken)
    }else{
      localStorage.removeItem('login');
      this.tokenResult.emit([])
    }
    return savedToken != '';
  }
*/
checkLogin(): boolean {
  const savedToken = JSON.parse(localStorage.getItem('login') || '[]');
  const isExpired = this.helper.isTokenExpired(savedToken.token);

  if (!isExpired) {
    // Le jeton n'est pas expiré, l'utilisateur est authentifié
    this.tokenResult.emit(savedToken);
    return true;
  } else {
    // Le jeton est expiré, déconnectez l'utilisateur et redirigez-le vers la page de connexion
    localStorage.removeItem('login');
    this.tokenResult.emit({});
    // Vous pouvez également rediriger l'utilisateur vers la page de connexion ici.
    return false;
  }
}


  getToken(){
    let savedToken = JSON.parse(localStorage.getItem('login') || '[]')
    let isExpired = this.helper.isTokenExpired(savedToken.token);
    if(isExpired){
      console.log("Token expired")
      this.checkLogin()
    }else{
      return savedToken.token
    }
  }

  logout(){
    localStorage.removeItem('login');
    let login = {
      username: '',
      token: ''
    }
    this.tokenResult.emit({
      login
    })
  }

  getRoleFromAuthResponse(response: any): string | null {
    if (response && response.role) {
      return response.role;
    }
    return null;
  }
  
}
