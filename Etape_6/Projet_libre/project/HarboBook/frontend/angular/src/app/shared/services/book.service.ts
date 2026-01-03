import { Injectable } from '@angular/core';
import {HttpClientService} from './http-client.service';
import {Book} from '../model/Book';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BookService {
  // [x: string]: any;
  constructor(private httpService: HttpClientService) { }

  addBook(onSuccess: Function, onFailure: Function, book:Book , url){
    let Request =  this.httpService.makePostRequestToken(url, book);
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


  ajouter(book: Book): Observable<any> { // Remarquez le type de retour 'Observable<any>'
    return new Observable((observer) => {
      this.addBook(
        (result) => {
          console.log(result);
          console.log("book added");
          observer.next(result); // Émettre le résultat
          observer.complete(); // Indiquer que l'opération est terminée
        },
        (error) => {
          console.log(error);
          observer.error(error); // Émettre une erreur
          observer.complete(); // Indiquer que l'opération est terminée
        },
        book,
        'http://127.0.0.1:8090/book'
      );
    });
  }

  updateBook(onSuccess: Function, onFailure: Function,book: Book, url){
    let Request =  this.httpService.makePutRequestToken(url,book);
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

  // enregistrer(book:Book){
  //   this.updateBook((result) => {
  //        console.log(result);
  //        console.log("book updated");
  //      },
  //      (error) => {
  //        console.log(error);
  //      }, book , 'http://127.0.0.1:8090/book/'+book.id);
  // }

  enregistrer(book: Book): Observable<any> {
    return new Observable((observer) => {
      this.updateBook(
        (result) => {
          console.log(result);
          console.log("book updated");
          observer.next(result); // Émettre le résultat
          observer.complete(); // Indiquer que l'opération est terminée
        },
        (error) => {
          console.log(error);
          observer.error(error); // Émettre une erreur
          observer.complete(); // Indiquer que l'opération est terminée
        },
        book,
        'http://127.0.0.1:8090/book/' + book.id
      );
    });
  }
  


  deleteBook(onSuccess: Function, onFailure: Function,url){
    let Request =  this.httpService.makeDeleteRequestToken(url);
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

  supprimer(book:Book){
    this.deleteBook(
      (result) => {
        console.log(result);
        console.log("book supprimer");
      },
      (error) => {
        console.log(error);
      }, "http://127.0.0.1:8090/book/"+book.id)

  }




  getBook(onSuccess: Function, onFailure: Function,url){
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

  charge(id:number){
    this.getBook(
      (result) => {
        console.log(result);
      },
      (error) => {
        console.log(error);
      }, "http://127.0.0.1:8090/book/"+id)
  }


  

}
