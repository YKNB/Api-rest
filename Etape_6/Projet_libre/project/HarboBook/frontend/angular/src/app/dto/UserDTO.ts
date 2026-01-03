import { Address } from "../shared/model/Address";

export class UserDTO {
    id: number;
    username: string;
    role: string;
    creationDate: string;
    // Ajoutez d'autres propriétés si nécessaire
    addressesList: Address[];
  }
  