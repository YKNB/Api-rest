package com.quest.etna.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.quest.etna.dto.*;
import com.quest.etna.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.quest.etna.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class AddressController {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private AddressRepository addressRepository;

    public AddressController(UserRepository userRepository, AddressService addressService,  AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }
    /*
    @GetMapping("/address/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable int id) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        Address address = addressService.checkAddressById(user.get(), id);

        if (address != null) {
            AddressResponseDTO addressDTO = convertToAddressResponseDTO(address);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(addressDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Erreur("User Role UNAUTHORIZED"));
        }
    }
    */
    @GetMapping("/address/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable int id) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        Address address = addressService.checkAddressById(user.get(), id);

        if (address != null) {
            AddressResponseDTO addressDTO = convertToAddressResponseDTO(address);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(addressDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Erreur("User Role UNAUTHORIZED"));
        }
    }
    /*
    @GetMapping("/address")
    ResponseEntity<?> getAllAddressesForUser()
    {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.get().getRole() == UserRole.ROLE_ADMIN)
            return addressService.checkAllAddress(user.get());
        else return addressService.checkAllAddressForUser(user.get());
    }
*/
    /*
@GetMapping("/address")
public ResponseEntity<List<AddressResponseDTO>> getAllAddressesForUser() {
    JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    String userName = userDetails.getUsername();
    Optional<User> user = userRepository.findByUsername(userName);

    List<Address> addresses;

    if (user.get().getRole() == UserRole.ROLE_ADMIN) {
        addresses = addressRepository.findAll();
    } else {
        addresses = addressRepository.findAllForUserId(user.get().getId());
    }

    // Utilisez ModelMapper ou un autre outil de mappage pour convertir Address en AddressResponseDTO
    List<AddressResponseDTO> addressDTOs = addresses.stream()
            .map(this::convertToAddressResponseDTO)
            .collect(Collectors.toList());

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(addressDTOs);
}
*/
    /*
    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesForUser() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        List<Address> addresses;

        if (user.get().getRole() == UserRole.ROLE_ADMIN) {
            addresses = addressRepository.findAll();
        } else {
            addresses = addressRepository.findAllForUserId(user.get().getId());
        }

        if (addresses.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        List<AddressResponseDTO> addressDTOs = addresses.stream()
                .map(this::convertToAddressResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressDTOs);
    }
*/
    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesForUser() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        List<Address> addresses;

        if (user.get().getRole() == UserRole.ROLE_ADMIN) {
            addresses = addressRepository.findAll();
        } else {
            addresses = addressRepository.findAllForUserId(user.get().getId());
        }

        List<AddressResponseDTO> addressDTOs = addresses.stream()
                .map(this::convertToAddressResponseDTO)
                .collect(Collectors.toList());

        if (addressDTOs.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.emptyList()); // Retourne une liste vide
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressDTOs);
    }

    private AddressResponseDTO convertToAddressResponseDTO(Address address) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(address, AddressResponseDTO.class);
    }



/*
    @GetMapping("/address/{id}")
    ResponseEntity<?> getAddressById(@PathVariable int id)
    {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        return addressService.checkAddressById(user.get(), id);
    }
 */
/*
    @PostMapping("/address")
 //   @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #mAddress.user.username == authentication.name)")
    ResponseEntity<?> createAddress(@RequestBody Address mAddress){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        return addressService.createAddress(mAddress, user.get(), userRepository);
    }
*/

    /*
@PostMapping("/address")
// @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #requestDTO.user.username == authentication.name)")
public ResponseEntity<GenericResponseDTO> createAddress(@RequestBody CreateAddressRequestDTO requestDTO) {
    // Convertir le DTO de demande en entité Address
    Address mAddress = new Address();
    mAddress.setStreet(requestDTO.getStreet());
    mAddress.setPostalCode(requestDTO.getPostalCode());
    mAddress.setCity(requestDTO.getCity());
    mAddress.setCountry(requestDTO.getCountry());

    // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
    JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Associer l'adresse à l'utilisateur actuel
    User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    mAddress.setUser(currentUser);

    // Enregistrer l'adresse dans la base de données
    Address savedAddress =  addressRepository.save(mAddress);


    ResponseEntity<GenericResponseDTO> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponseDTO("Adresse créée avec succès"));

    return responseEntity;
}
     */
    @PostMapping("/address")
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody CreateAddressRequestDTO requestDTO) {
        // Convertir le DTO de demande en entité Address
        Address mAddress = new Address();
        mAddress.setStreet(requestDTO.getStreet());
        mAddress.setPostalCode(requestDTO.getPostalCode());
        mAddress.setCity(requestDTO.getCity());
        mAddress.setCountry(requestDTO.getCountry());

        // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Associer l'adresse à l'utilisateur actuel
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        mAddress.setUser(currentUser);

        // Enregistrer l'adresse dans la base de données
        Address savedAddress = addressRepository.save(mAddress);

        // Convertir l'entité "Address" en un objet "AddressResponseDTO"
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId(savedAddress.getId());
        responseDTO.setStreet(savedAddress.getStreet());
        responseDTO.setPostalCode(savedAddress.getPostalCode());
        responseDTO.setCity(savedAddress.getCity());
        responseDTO.setCountry(savedAddress.getCountry());
        // Inclure les dates de création et de mise à jour
        responseDTO.setCreationDate(savedAddress.getCreationDate());
        responseDTO.setUpdatedDate(savedAddress.getUpdatedDate());

        // Retourner l'objet "AddressResponseDTO" dans la réponse
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }



/*
    @PutMapping("/address/{address}")
  //  @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @addressService.checkAddressOwnership(authentication.name, #addressId))")
    ResponseEntity<?> updateAddress(@PathVariable(value = "address") int addressId,
                                    @RequestBody Address mAddress)
    {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        return addressService.updateAddress(addressId, mAddress, user.get());
    }
*/

    /*
@PutMapping("/address/{addressId}")
// @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @addressService.checkAddressOwnership(authentication.name, #addressId))")
public ResponseEntity<GenericResponseDTO> updateAddress(@PathVariable(value = "addressId") int addressId,
                                                        @RequestBody UpdateAddressRequestDTO requestDTO) {
    // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
    JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userName = userDetails.getUsername();
    Optional<User> user = userRepository.findByUsername(userName);

    // Vérifier si l'utilisateur a le droit de mettre à jour cette adresse
    if (addressService.checkAddressOwnership(userName, addressId)) {
        // Récupérer l'adresse à mettre à jour depuis la base de données
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Adresse introuvable"));

        // Mettre à jour les champs de l'adresse avec les nouvelles valeurs du DTO
        existingAddress.setStreet(requestDTO.getStreet());
        existingAddress.setPostalCode(requestDTO.getPostalCode());
        existingAddress.setCity(requestDTO.getCity());
        existingAddress.setCountry(requestDTO.getCountry());

        // Mettre à jour la date de mise à jour
        existingAddress.setUpdatedDate(new Date());

        // Enregistrer l'adresse mise à jour dans la base de données
        addressRepository.save(existingAddress);

        // Retourner une réponse générique en cas de succès
        return ResponseEntity.ok(new GenericResponseDTO("Adresse mise à jour avec succès"));
    } else {
        // L'utilisateur n'est pas autorisé à mettre à jour cette adresse, renvoyer une réponse d'erreur
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GenericResponseDTO("Vous n'êtes pas autorisé à mettre à jour cette adresse"));
    }
}
*/

    /*
    @PutMapping("/address/{addressId}")
    public ResponseEntity<GenericResponseDTO> updateAddress(@PathVariable(value = "addressId") int addressId,
                                                            @RequestBody UpdateAddressRequestDTO requestDTO) {
        // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        // Vérifier si l'utilisateur a le droit de mettre à jour cette adresse
        if (addressService.checkAddressOwnership(userName, addressId)) {
            // Récupérer l'adresse à mettre à jour depuis la base de données
            Address existingAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Adresse introuvable"));

            // Mettre à jour les champs de l'adresse avec les nouvelles valeurs du DTO s'ils sont fournis
            if (requestDTO.getStreet() != null) {
                existingAddress.setStreet(requestDTO.getStreet());
            }
            if (requestDTO.getPostalCode() != null) {
                existingAddress.setPostalCode(requestDTO.getPostalCode());
            }
            if (requestDTO.getCity() != null) {
                existingAddress.setCity(requestDTO.getCity());
            }
            if (requestDTO.getCountry() != null) {
                existingAddress.setCountry(requestDTO.getCountry());
            }

            // Mettre à jour la date de mise à jour
            existingAddress.setUpdatedDate(new Date());

            // Enregistrer l'adresse mise à jour dans la base de données
            addressRepository.save(existingAddress);

            // Retourner une réponse générique en cas de succès
            return ResponseEntity.ok(new GenericResponseDTO("Adresse mise à jour avec succès"));
        } else {
            // L'utilisateur n'est pas autorisé à mettre à jour cette adresse, renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GenericResponseDTO("Vous n'êtes pas autorisé à mettre à jour cette adresse"));
        }
    }
*/
    /*
    @PutMapping("/address/{address_id}")
    public ResponseEntity<UpdateAddressResponseDTO> updateAddress(@PathVariable(value = "address_id") int addressId,
                                                                  @RequestBody UpdateAddressRequestDTO requestDTO) {
        // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        // Vérifier si l'utilisateur a le droit de mettre à jour cette adresse
        if (addressService.checkAddressOwnership(userName, addressId)) {
            // Récupérer l'adresse à mettre à jour depuis la base de données
            Address existingAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Adresse introuvable"));

            // Mettre à jour les champs de l'adresse avec les nouvelles valeurs du DTO s'ils sont fournis
            if (requestDTO.getStreet() != null) {
                existingAddress.setStreet(requestDTO.getStreet());
            }
            if (requestDTO.getPostalCode() != null) {
                existingAddress.setPostalCode(requestDTO.getPostalCode());
            }
            if (requestDTO.getCity() != null) {
                existingAddress.setCity(requestDTO.getCity());
            }
            if (requestDTO.getCountry() != null) {
                existingAddress.setCountry(requestDTO.getCountry());
            }

            // Mettre à jour la date de mise à jour
            existingAddress.setUpdatedDate(new Date());

            // Enregistrer l'adresse mise à jour dans la base de données
            addressRepository.save(existingAddress);

            // Créer l'objet UpdateAddressResponseDTO avec les valeurs mises à jour
            UpdateAddressResponseDTO responseDTO = new UpdateAddressResponseDTO();
            responseDTO.setId(existingAddress.getId());
            responseDTO.setStreet(existingAddress.getStreet());
            responseDTO.setPostalCode(existingAddress.getPostalCode());
            responseDTO.setCity(existingAddress.getCity());
            responseDTO.setCountry(existingAddress.getCountry());
            responseDTO.setMessage("Adresse mise à jour avec succès");

            // Retourner l'objet UpdateAddressResponseDTO dans la réponse
            return ResponseEntity.ok(responseDTO);
        } else {
            // L'utilisateur n'est pas autorisé à mettre à jour cette adresse, renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UpdateAddressResponseDTO());
        }
    }
*/
    @PutMapping("/address/{address_id}")
    public ResponseEntity<UpdateAddressResponseDTO> updateAddress(@PathVariable(value = "address_id") int addressId,
                                                                  @RequestBody UpdateAddressRequestDTO requestDTO) {
        // Obtenir l'utilisateur actuellement authentifié depuis le contexte de sécurité
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);

        // Récupérer l'adresse à mettre à jour depuis la base de données
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Adresse introuvable"));

        // Vérifier si l'utilisateur a le droit de mettre à jour cette adresse
        if (addressService.checkAddressOwnership(userName, addressId)) {
            // Mettre à jour les champs de l'adresse avec les nouvelles valeurs du DTO s'ils sont fournis
            if (requestDTO.getStreet() != null) {
                existingAddress.setStreet(requestDTO.getStreet());
            }
            if (requestDTO.getPostalCode() != null) {
                existingAddress.setPostalCode(requestDTO.getPostalCode());
            }
            if (requestDTO.getCity() != null) {
                existingAddress.setCity(requestDTO.getCity());
            }
            if (requestDTO.getCountry() != null) {
                existingAddress.setCountry(requestDTO.getCountry());
            }

            // Mettre à jour la date de mise à jour
            existingAddress.setUpdatedDate(new Date());

            // Enregistrer l'adresse mise à jour dans la base de données
            addressRepository.save(existingAddress);

            // Créer l'objet UpdateAddressResponseDTO avec les valeurs mises à jour
            UpdateAddressResponseDTO responseDTO = new UpdateAddressResponseDTO();
            responseDTO.setId(existingAddress.getId());
            responseDTO.setStreet(existingAddress.getStreet());
            responseDTO.setPostalCode(existingAddress.getPostalCode());
            responseDTO.setCity(existingAddress.getCity());
            responseDTO.setCountry(existingAddress.getCountry());
            responseDTO.setMessage("Adresse mise à jour avec succès");

            // Retourner l'objet UpdateAddressResponseDTO dans la réponse
            return ResponseEntity.ok(responseDTO);
        } else {
            // L'utilisateur n'est pas autorisé à mettre à jour cette adresse, renvoyer une réponse d'erreur
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new UpdateAddressResponseDTO());
        }
    }

/*
    @DeleteMapping("/address/{address_id}")
    ResponseEntity<?> deleteAddress(@PathVariable(value = "address_id") int addressId){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        return addressService.deleteAddress(addressId, user.get());
    }
*/
/*
    @DeleteMapping("/address/{address}")
    ResponseEntity<?> deleteAddress(@PathVariable(value = "address") int addressId){
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        return addressService.deleteAddress(addressId, user.get());
    }
*/
@DeleteMapping("/address/{address}")
public ResponseEntity<DeleteResponseDTO> deleteAddress(@PathVariable(value = "address") int addressId) {
    JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    String userName = userDetails.getUsername();
    Optional<User> user = userRepository.findByUsername(userName);
    DeleteResponseDTO responseDTO = addressService.deleteAddress(addressId, user.get());

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDTO);
}

}
