import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class HttpClientService {

  constructor(private http: HttpClient) { }

  makePostRequest(url, body): Observable<any> {
    return this.http.post( url, body );
  }

  makePostRequestToken(url, body): Observable<any> {
    return this.http.post( url, body, this.requestOption(this.getToken()));
  }

  makePutRequestToken(url, body): Observable<any> {
    return this.http.put( url, body, this.requestOption(this.getToken()));
  }

  makeGetRequestToken(url): Observable<any> {
    return this.http.get(url,this.requestOption(this.getToken()))
  }

  makeDeleteRequestToken(url): Observable<any>{
    return this.http.delete(url, this.requestOption(this.getToken()))
  }

 makeDeleteRequestTokenId(url): Observable<any>{
    return this.http.delete(url, this.requestOption(this.getToken()))
  }

  processJson(jsonResponse){
    return JSON.parse(jsonResponse['result'])
  }

  requestOption(token:string){
    const headerDict = {
      'Content-Type': 'application/json',
      'Authorization' : 'Bearer ' + token
    };

    const requestOptions = {
      headers: new HttpHeaders(headerDict),
    };

    return requestOptions
  }

  getToken(){
    let monObjet = JSON.parse(localStorage.getItem('login'));
    return monObjet.token
  }
}
