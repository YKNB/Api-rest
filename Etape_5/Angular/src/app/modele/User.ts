import { Address } from "./Address";


export class User {

  id: number;
  username: string;
  password: string;
  role?: string;
  creation_date: Date;
  last_modification_date: Date;
  addressesList: Address[];

}
