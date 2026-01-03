import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../shared/model/Book';
import {BookService} from '../../shared/services/book.service';


@Component({
  selector: 'app-addelement',
  templateUrl: './addelement.component.html',
  styleUrls: ['./addelement.component.scss'],

})
export class AddelementComponent implements OnInit {
  successMessage: string = '';
  errorMessage: string = '';
  
  @Input() book:Book = new Book() ;
  @Input() ajout=true;
  constructor(public bookService:BookService) {
  }



  ngOnInit(): void {

  }
  

  addbook() {
    console.log(this.book);
    this.bookService.ajouter(this.book).subscribe(
      (result) => {
        console.log(result);
        this.successMessage = 'Le livre a été ajouté avec succès !';
        this.errorMessage = '';
      },
      (error) => {
        console.error(error);
        this.successMessage = '';
        this.errorMessage = "Une erreur s'est produite lors de l'ajout du livre. Veuillez réessayer.";
      }
    );
  }

  update(){
    console.log(this.book);
    this.bookService.enregistrer(this.book);
  }

}
