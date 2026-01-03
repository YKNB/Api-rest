import DateTimeFormat = Intl.DateTimeFormat;

export class Book {
  constructor() {
    this.vision=false;
  }

  id: number;

  title: string;

  description: string;

  author: string;

  image: string;

  pages:number;

  quantity:number;

  date_publication: Date; 

  creation_date: DateTimeFormat;

  last_modification_date: DateTimeFormat;

  vision:boolean; //variable pour gère l'état du livre si ilest en consultation ou en modification



}
