import { Component, OnInit } from '@angular/core';
import {BookService} from '../../shared/services/book.service';
import {Book} from '../../shared/model/Book';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/shared/services/user.service';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  [x: string]: any;
  listBook:[Book];
  searchTerm: string = '';
  searchResult: Book | null = null;
  current = 'https://thumbs.dreamstime.com/b/no-image-available-icon-photo-camera-flat-vector-illustration-132483097.jpg';
  userRole$: Observable<string>;

  modification:boolean = false;

  PrintText(title, size): string{
    let res = title;
    if (title.length > size){
      res = res.substring(0, size);
      res = res + '...';
      return res;
    }
    return title;
  }

  constructor(private bookService:BookService, private userService: UserService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.bookService.getBook((result) => {
        console.log(result);
        this.listBook = result
        this.userRole$ = this.userService.getUserRole();
        console.log("books charged");
      },
      (error) => {
        console.log(error);
      },"http://127.0.0.1:8090/books")
  }

  bookOpened(book:Book) {
    localStorage.setItem('openedbook', JSON.stringify(book)); // sync the data
    console.log(localStorage.getItem("openedbook"));
    console.log("book opened")
    
  // Appel de la méthode openSuccessDialog avec les informations du livre
  this.openSuccessDialog(book);
  }

  openSuccessDialog(book: Book) {
    const dialogRef = this.dialog.open(SuccessDialogComponent, {
      data: { book }, // Passez le livre à afficher à la boîte de dialogue
    });
  
    dialogRef.afterClosed().subscribe(() => {
      // Code à exécuter après la fermeture de la boîte de dialogue (si nécessaire)
    });
  }
  searchBooks() {
    // Utilisation de la méthode filter pour trouver le premier livre correspondant
    this.searchResult = this.listBook.find(book =>
      book.title.includes(this.searchTerm) ||
      book.author.includes(this.searchTerm)
    );
  }
}
