package com.quest.etna.service;

import com.quest.etna.dto.DeleteResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import com.quest.etna.model.Address;
import com.quest.etna.model.Erreur;
import com.quest.etna.model.UserRole;
import com.quest.etna.model.Success;
import com.quest.etna.model.User;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    public final AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;




    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> checkAllAddress(User user) {
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            List<Address> listAddresses = this.addressRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(listAddresses);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Erreur("User Role UNAUTHORIZED"));
        }
    }

    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> checkAllAddressForUser(User user) {
        List<Address> listAddresses = this.addressRepository.findAllForUserId(user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listAddresses);
    }

    /*
      public Address checkAddressById(User user, int addressId) {
          Address address = this.addressRepository.findAddressById(addressId);

          if (address != null) {
              // Vérifiez si l'utilisateur est propriétaire de l'adresse ou s'il a le rôle ROLE_ADMIN
              if (address.getUser() != null && address.getUser().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN) {
                  return address;
              }
          }

          return null;
      }
    */
    public Address checkAddressById(User user, int addressId) {
        Address address = this.addressRepository.findAddressById(addressId);

        if (address != null) {
            // Vérifiez si l'utilisateur est propriétaire de l'adresse ou s'il a le rôle ROLE_ADMIN
            if (address.getUser() != null && (address.getUser().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN)) {
                return address;
            }
        }

        return null;
    }

    //  @PreAuthorize("hasRole('ROLE_USER' or 'ROLE_ADMIN')")
    public ResponseEntity<?> createAddress(Address mAddress, User user, UserRepository userRepository) {
        user.setAddress(mAddress);
        mAddress.setUser(user);
        userRepository.save(user);
        addressRepository.save(mAddress);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mAddress);
    }
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateAddress(int addressId, Address upAddress, User user) {
        // Votre logique ici pour mettre à jour l'adresse

        // Vérifiez si l'utilisateur a le droit de mettre à jour cette adresse
        Address oldAddress = this.addressRepository.findAddressById(addressId);

        if (oldAddress != null && oldAddress.getUser() != null && oldAddress.getUser().getId() == user.getId()) {
            // L'utilisateur est autorisé à mettre à jour cette adresse
            if (upAddress.getCity() != null) {
                oldAddress.setCity(upAddress.getCity());
            }
            if (upAddress.getCountry() != null) {
                oldAddress.setCountry(upAddress.getCountry());
            }
            if (upAddress.getPostalCode() != null) {
                oldAddress.setPostalCode(upAddress.getPostalCode());
            }
            if (upAddress.getStreet() != null) {
                oldAddress.setStreet(upAddress.getStreet());
            }
            oldAddress.setUpdatedDate(new Date());
            addressRepository.save(oldAddress);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(oldAddress);
        } else {
            // L'utilisateur n'est pas autorisé à mettre à jour cette adresse
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Erreur("User Role UNAUTHORIZED"));
        }
    }
    // @PreAuthorize("hasRole('ROLE_USER')")
    /*
    public ResponseEntity<?> deleteAddress(int addressId, User user) {
        // Votre logique ici pour supprimer l'adresse
        Address address = this.addressRepository.findAddressById(addressId);

        // Vérifiez si l'utilisateur a le droit de supprimer cette adresse
        if (address != null && address.getUser() != null && address.getUser().getId() == user.getId()) {
            // L'utilisateur est autorisé à supprimer cette adresse
            this.addressRepository.delete(address);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Success("TRUE"));
        } else {
            // L'utilisateur n'est pas autorisé à supprimer cette adresse
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Success("FALSE"));
        }
    }

     */
/*
    public ResponseEntity<?> deleteAddress(int addressId, User user) {
        // Vérifiez si l'utilisateur a le rôle ROLE_ADMIN
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            // L'utilisateur est un administrateur, autorisez la suppression
            Address address = this.addressRepository.findAddressById(addressId);

            if (address != null) {
                this.addressRepository.delete(address);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new Success("TRUE"));
            } else {
                // L'adresse n'existe pas
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new Error("L'adresse n'existe pas."));
            }
        } else {
            // L'utilisateur n'a pas le rôle ROLE_ADMIN, vérifiez la propriété de l'adresse
            if (checkAddressOwnership(user.getUsername(), addressId)) {
                Address address = this.addressRepository.findAddressById(addressId);

                if (address != null) {
                    this.addressRepository.delete(address);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(new Success("TRUE"));
                } else {
                    // L'adresse n'existe pas
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(new Error("L'adresse n'existe pas."));
                }
            } else {
                // L'utilisateur n'est pas autorisé à supprimer cette adresse
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(new Error("Vous n'avez pas la permission de supprimer cette adresse."));
            }
        }
    }
*/
    /*
    @Transactional
    public ResponseEntity<?> deleteAddress(int addressId, User user) {
        // Votre logique ici pour supprimer l'adresse
        Address address = this.addressRepository.findAddressById(addressId);

        if (user.getRole() == UserRole.ROLE_USER ) {
            if(address.getUser().getId() == user.getId()) {
                this.addressRepository.deleteAddressById(addressId);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(true); // Retournez simplement true
            }
        }
        // Vérifiez si l'utilisateur a le droit de supprimer cette adresse
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            // L'utilisateur est autorisé à supprimer cette adresse
            this.addressRepository.deleteAddressById(addressId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(true); // Retournez simplement true
        } else {
            // L'utilisateur n'est pas autorisé à supprimer cette adresse
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(false); // Retournez simplement false
        }
    }

*/
    @Transactional
    public DeleteResponseDTO deleteAddress(int addressId, User user) {
        // Votre logique ici pour supprimer l'adresse
        Address address = this.addressRepository.findAddressById(addressId);

        if (user.getRole() == UserRole.ROLE_USER ) {
            if(address.getUser().getId() == user.getId()) {
                this.addressRepository.deleteAddressById(addressId);
                return new DeleteResponseDTO(true); // Adresse supprimée avec succès
            }
        }
        // Vérifiez si l'utilisateur a le droit de supprimer cette adresse
        if (user.getRole() == UserRole.ROLE_ADMIN) {
            // L'utilisateur est autorisé à supprimer cette adresse
            this.addressRepository.deleteAddressById(addressId);
            return new DeleteResponseDTO(true); // Adresse supprimée avec succès
        } else {
            // L'utilisateur n'est pas autorisé à supprimer cette adresse
            return new DeleteResponseDTO(false); // Échec de la suppression de l'adresse
        }
    }

    /*
    public Boolean checkAddressOwnership(String username, int addressId) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Récupérez l'utilisateur
            User currentUser = user.get();

            // Récupérez l'adresse par son ID
            Address address = addressRepository.findAddressById(addressId);

            // Vérifiez si l'utilisateur est propriétaire de l'adresse en comparant les ID
            return address != null && address.getUser() != null && address.getUser().getId() == currentUser.getId();
        }

        // Si l'utilisateur n'existe pas, ou si l'adresse n'existe pas, ou si l'adresse n'appartient pas à l'utilisateur, renvoyez false
        return false;
    }

     */
    /*
    public Boolean checkAddressOwnership(String username, int addressId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Address> addressOptional = Optional.ofNullable(addressRepository.findAddressById(addressId));

        if (userOptional.isPresent() && addressOptional.isPresent()) {
            User currentUser = userOptional.get();
            Address address = addressOptional.get();
            return address.getUser() != null && address.getUser().getId() == currentUser.getId();
        }

        return false;
    }

     */
    public Boolean checkAddressOwnership(String username, int addressId) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            // Récupérez l'utilisateur
            User currentUser = user.get();

            // Récupérez l'adresse par son ID
            Address address = addressRepository.findAddressById(addressId);

            if (address != null) {
                if (currentUser.getRole() == UserRole.ROLE_ADMIN) {
                    // ROLE_ADMIN peut modifier toutes les adresses
                    return true;
                } else if (currentUser.getRole() == UserRole.ROLE_USER) {
                    // ROLE_USER peut seulement modifier ses propres adresses
                    if (address.getUser() != null && address.getUser().getId() == currentUser.getId()) {
                        return true;
                    }
                }
            }
        }

        // Si l'utilisateur n'existe pas, ou si l'adresse n'existe pas,
        // ou si l'adresse n'appartient pas à l'utilisateur (ROLE_USER),
        // renvoyez false
        return false;
    }

}
