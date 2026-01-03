import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Book } from 'src/app/shared/model/Book';

@Component({
  selector: 'app-success-dialog',
  templateUrl: './success-dialog.component.html',
  styleUrls: ['./success-dialog.component.scss']
})
export class SuccessDialogComponent implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<SuccessDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { book: Book } // Injectez les données du livre
  ) {}

  ngOnInit(): void {
    // Accédez aux informations du livre à partir de this.data.book
    const book: Book = this.data.book;
    console.log('Informations du livre :', book);
  }

  closeDialog() {
    this.dialogRef.close();
  }
}