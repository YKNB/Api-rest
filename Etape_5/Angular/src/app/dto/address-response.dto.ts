// address-response.dto.ts
export interface AddressResponseDTO {
    id: number;
    userId: number;
    street: string;
    postalCode: string;
    city: string;
    country: string;
    creationDate: Date;
    updatedDate: Date;
  }
  