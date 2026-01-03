import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpClientService} from './http-client.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from '../model/User';
import {Address} from '../model/Address';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private response: any;

  public tokenResult = new EventEmitter<any>();

  private helper;

  constructor(private httpService: HttpClientService) {
    this.helper = new JwtHelperService();
  }

  /**
   * Save a new Customer object in the Backend server data base.
   * @param book
   */
  saveCustomer(onSuccess: Function, onFailure: Function,address: Address){
    let Request =  this.httpService.makePostRequest('http://localhost:8090/register', address);
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
   */
  updateCustomer(onSuccess: Function, onFailure: Function,address: Address, url){
    let Request =  this.httpService.makePutRequestToken(url,address);
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
   * Delete an existing Customer object in the Backend server data base.
   * @param customer
   */
  deleteAddress(onSuccess: Function, onFailure: Function,url){
    let Request =  this.httpService.makeDeleteRequestToken(url);
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


  getAddress(onSuccess: Function, onFailure: Function,url){
    let Request =  this.httpService.makeGetRequestToken(url);
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
   * Récupère toutes les adresses depuis le backend.
   * @param token Le token d'authentification.
   * @returns Un Observable contenant un tableau d'adresses.
   */
  getAllAddresses(): Observable<Address[]> {
    const url = 'http://localhost:8090/address'; // L'URL du backend pour récupérer toutes les adresses

    // Utilisez le service HTTP pour effectuer la requête GET avec le token d'authentification
    return this.httpService.makeGetRequestToken(url);
  }

  updateAddressWithToken(addressId: number, updatedAddress: Address): Observable<Address> {
    // Construction de l'URL complète avec l'ID de l'adresse
    const url = `http://localhost:8090/address/${addressId}`;
  
    // Envoi de la requête PUT avec le nouvel objet d'adresse et le jeton d'authentification dans l'en-tête
    return this.httpService.makePutRequestToken(url, updatedAddress).pipe(
      map((result) => result as Address)
    );
  }
  deleteAddressWithToken(addressId: number): Observable<any> {
    const url = `http://localhost:8090/address/${addressId}`;
    return this.httpService.makeDeleteRequestToken(url);
  }
  
  createAddress(address: Address): Observable<Address> {
    const url = 'http://localhost:8090/address'; // URL pour créer une adresse

    // Utilisez le service HTTP pour effectuer la requête POST avec le token d'authentification
    return this.httpService.makePostRequestToken(url, address);
  }
}
