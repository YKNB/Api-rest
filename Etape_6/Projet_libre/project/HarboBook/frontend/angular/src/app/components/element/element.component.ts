import {Component, Input, OnInit} from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {AddressService} from '../../shared/services/address.service';
import {BookService} from '../../shared/services/book.service';
import {Book} from '../../shared/model/Book';


@Component({
  selector: 'app-element',
  templateUrl: './element.component.html',
  styleUrls: ['./element.component.scss']
})
export class ElementComponent implements OnInit {


  constructor(private bookService: BookService) { }
  model: NgbDateStruct;
  @Input() book : Book = new Book();
  mode_vision:boolean = true;

  successMessage: string = '';
  errorMessage: string = '';

  current = 'https://thumbs.dreamstime.com/b/no-image-available-icon-photo-camera-flat-vector-illustration-132483097.jpg';

  ngOnInit(): void {
    this.book = JSON.parse(localStorage.getItem('openedbook'));
  }

  changeMode(){
    if (this.mode_vision) this.mode_vision=false;
    else this.mode_vision=true;
  }

  addbook() {
    console.log(this.book);
    this.bookService.ajouter(this.book);
  }

 
  update(){
    console.log(this.book);
    this.bookService.enregistrer(this.book).subscribe(
      (result) => {
        console.log(result);
        this.successMessage = 'Le livre a été modifié avec succès !';
        this.errorMessage = '';
      },
  (error) => {
    console.error(error);
    this.successMessage = '';
    this.errorMessage = "Une erreur s'est produite lors de la modification du livre. Veuillez réessayer.";
  }
);

}

  supprimer(){
    this.bookService.supprimer(this.book);
  }
}
