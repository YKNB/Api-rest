import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../modele/User';
import {Observable, of} from 'rxjs';
import {HttpClientService} from './http-client.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import { map } from 'rxjs/operators';
import { UserDTO } from '../dto/UserDTO';
import { forkJoin } from 'rxjs';
import { Address } from '../modele/Address';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  [x: string]: any;
  private userRole: string;
  private response: any;

  public tokenResult = new EventEmitter<any>();

  private helper;

  constructor(private httpService: HttpClientService) {
    this.helper = new JwtHelperService();
  }

  list_user= [
    {
      id: "1", username: "Paul", password: "test", role: "ROLE_USER",
    creation_date: "2012-12-1",last_modification_date: "2012-12-1"
     }
  ]


  /**
   * Save a new Customer object in the Backend server data base.
   * @param book
   */
  saveUser(onSuccess: Function, onFailure: Function,user: User){
    let Request =  this.httpService.makePostRequest('http://localhost:8090/register', user);
    Request.subscribe((result) => {
        this.response = result;
        onSuccess(result);
        console.log(result);
        return result;
      },
      error => {
        onFailure(error);
        return error;
      });
  }

  /**
   * Update an existing Customer object in the Backend server data base.
   * @param customer

  /**
   * Delete an existing Customer object in the Backend server data base.
   * @param customer
   */
  deleteUser(userId: number): Observable<string> {
    const url = `http://localhost:8090/user/${userId}`;
    return this.httpService.makeDeleteRequestToken(url).pipe(
      map(() => {
        // Supprimer l'utilisateur de la liste
        this.list_user = this.list_user.filter((user) => parseInt(user.id) !== userId);
        return 'Utilisateur supprimé avec succès';
      })
    );
  }


  getUser(onSuccess: Function, onFailure: Function,url){
    let Request =  this.httpService.makeGetRequestToken(url);
    Request.subscribe((result) => {

        onSuccess(result);
        console.log(result);
        return result;
      },
      error => {
        onFailure(error);
        return error;
      });
  }

  getUserDTOList(onSuccess: Function, onFailure: Function, url: string) {
    this.getUser(
      (result) => {
        const userDTOList = result.map((user) => this.mapToUserDTO(user));
        onSuccess(userDTOList);
      },
      (error) => {
        onFailure(error);
      },
      url
    );
  }
  
  private mapToUserDTO(user: any): UserDTO {
    return {
      id: user.id,
      username: user.username,
      role: user.role,
      creationDate: user.creationDate,
      addressesList: user.addressesList // Assurez-vous d'ajuster ceci en fonction de votre structure d'adresse
    };
  }

  
  getUserRole(): Observable<string> {
    if (this.userRole) {
      return of(this.userRole);
    } else {
      return this.whoami().pipe(
        map(result => {
          const role = result.role;
          this.userRole = role; // Stocker le rôle pour une utilisation ultérieure
          return role;
        })
      );
    }
  }
  

  whoami(): Observable<any> {
    return this.httpService.makeGetRequestToken('http://localhost:8090/me');
  }

  updateUserDetails(
    userId: number,
    userDetails: { username?: string; role?: string }
  ): Observable<UserDTO> {
    const url = `http://localhost:8090/user/${userId}`;
    return this.httpService.makePutRequestToken(url, userDetails).pipe(
      map((result: UserDTO) => {
        // Mettre à jour le tableau list_user avec les nouvelles données
        const updatedList = this.list_user.map((user) => {
          if (parseInt(user.id) === userId) { // Conversion de user.id en nombre
            // Mettre à jour les détails de l'utilisateur modifié
            if (userDetails.username !== undefined) {
              user.username = userDetails.username;
            }
            if (userDetails.role !== undefined) {
              user.role = userDetails.role;
            }
          }
          return user;
        });
        this.list_user = updatedList;
        return result;
      })
    );
  }
  
  updateUsername(userId: number, newUsername: string): Observable<UserDTO> {
    const url = `http://localhost:8090/user/${userId}`;
    const userDetails = { username: newUsername }; // Créez un objet avec le nouveau nom d'utilisateur

    return this.httpService.makePutRequestToken(url, userDetails).pipe(
      map((result: UserDTO) => {
        // Mettre à jour le tableau list_user avec les nouvelles données
        const updatedList = this.list_user.map((user) => {
          if (parseInt(user.id) === userId) { // Conversion de user.id en nombre
            // Mettre à jour le nom d'utilisateur de l'utilisateur modifié
            user.username = newUsername;
          }
          return user;
        });
        this.list_user = updatedList;
        return result;
      })
    );
  }
  
  getUserAddresses(username: string): Observable<UserDTO> {
    const url = `http://localhost:8090/user/${username}`;
    return this.httpService.makeGetRequestToken(url);
  }
  
  
  

}
