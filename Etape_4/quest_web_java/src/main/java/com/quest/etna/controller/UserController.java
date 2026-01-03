package com.quest.etna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.WorkerJob;
import com.quest.etna.config.cryPass;
import com.quest.etna.dto.AddressResponseDTO;
import com.quest.etna.dto.UserResponseDTO;
import com.quest.etna.dto.UsersResponseDTO;
import com.quest.etna.dto.UserUpdateDTO ;
import com.quest.etna.model.*;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;
import com.quest.etna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;
    private WorkerJob workerjob;
    private cryPass passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    public UserController (JwtTokenUtil jwtTokenUtil, WorkerJob workerjob,
                           cryPass passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.workerjob = workerjob;
        this.passwordEncoder = passwordEncoder;
    }
    /*
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer limit) {
        return userService.getList(page, limit);
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> getOneByName(@PathVariable("name") String name) {
        User user = userService.getOneByName(name);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userService.createEntity(user);
    }
*/
/*
    @GetMapping("/{name}")
    public ResponseEntity<UsersResponseDTO> getOneByName(@PathVariable("name") String name) {
        User user = userService.getOneByName(name);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Récupérez les adresses de l'utilisateur et convertissez-les en DTOs d'adresse
        List<Address> userAddresses = user.getAddressesList();
        List<AddressResponseDTO> addressDTOs = convertToAddressResponseDTOList(userAddresses);

        // Créez un objet UsersResponseDTO pour l'utilisateur avec ses adresses
        UsersResponseDTO userDTO = new UsersResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().toString());
        userDTO.setCreationDate(user.getcreation_date());
        userDTO.setAddressesList(addressDTOs);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> getOneById(@PathVariable("id") int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        // Récupérez les adresses de l'utilisateur et convertissez-les en DTOs d'adresse
        List<Address> userAddresses = addressRepository.findAllForUserId(user.getId());
        List<AddressResponseDTO> addressDTOs = convertToAddressResponseDTOList(userAddresses);

        // Créez un objet UsersResponseDTO pour l'utilisateur avec ses adresses
        UsersResponseDTO userDTO = new UsersResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().toString());
        userDTO.setCreationDate(user.getcreation_date());
        userDTO.setAddressesList(addressDTOs);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

*/
    @GetMapping("/{identifier}")
    public ResponseEntity<UsersResponseDTO> getUserByIdentifier(@PathVariable("identifier") String identifier) {
        User user = null;

        if (identifier.matches("\\d+")) {
            // Si l'identificateur est un nombre, recherchez par ID
            int userId = Integer.parseInt(identifier);
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
        } else {
            // Sinon, recherchez par nom d'utilisateur
            user = userService.getOneByName(identifier);
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Récupérez les adresses de l'utilisateur et convertissez-les en DTOs d'adresse
        List<Address> userAddresses = addressRepository.findAllForUserId(user.getId());
        List<AddressResponseDTO> addressDTOs = convertToAddressResponseDTOList(userAddresses);

        // Créez un objet UsersResponseDTO pour l'utilisateur avec ses adresses
        UsersResponseDTO userDTO = new UsersResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().toString());
        userDTO.setCreationDate(user.getcreation_date());
        userDTO.setAddressesList(addressDTOs);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    private List<AddressResponseDTO> convertToAddressResponseDTOList(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertToAddressResponseDTO)
                .collect(Collectors.toList());
    }


/*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User userbody) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> userToUpdate = userRepository.findById(id);

            if (!userToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
            }

            User existingUser = userToUpdate.get();

            if (userbody.getUsername() != null && !userbody.getUsername().equals(existingUser.getUsername())) {
                // Mettre à jour le nom d'utilisateur
                existingUser.setUsername(userbody.getUsername());
            }

            if (userbody.getRole() != null && userbody.getRole() != existingUser.getRole()) {
                // Mettre à jour le rôle
                existingUser.setRole(userbody.getRole());
            }

            userRepository.save(existingUser);

            String mToken = workerjob.getValidToken();
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("token", mToken);

            String jsonString = mapper.writeValueAsString(result);
            return ResponseEntity.status(HttpStatus.OK).body(jsonString);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Error"));
        }
    }
*/
    /*
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User userbody) {
    try {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> userToUpdate = userRepository.findById(id);

        if (!userToUpdate.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
        }

        User existingUser = userToUpdate.get();

        if (userbody.getUsername() != null && !userbody.getUsername().equals(existingUser.getUsername())) {
            // Mettre à jour le nom d'utilisateur
            if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")) || userDetails.getUsername().equals(existingUser.getUsername())) {
                existingUser.setUsername(userbody.getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de changer le nom d'utilisateur"));
            }
        }

        if (userbody.getRole() != null && userbody.getRole() != existingUser.getRole()) {
            // Mettre à jour le rôle
            if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                existingUser.setRole(userbody.getRole());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de changer le rôle de l'utilisateur"));
            }
        }

        userRepository.save(existingUser);

        String mToken = workerjob.getValidToken();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("token", mToken);

        String jsonString = mapper.writeValueAsString(result);
        return ResponseEntity.status(HttpStatus.OK).body(jsonString);
    } catch (Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Error"));
    }
}
*/


/*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User userbody) {

        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> userToUpdate = userRepository.findById(id);
            Optional<User> userJw = userRepository.findByUsername(userName);

            if (!userToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
            }

            User existingUser = userToUpdate.get();

            if (userJw.isPresent()) {
                if (userJw.get().getId() == existingUser.getId() || userJw.get().getRole() == UserRole.ROLE_ADMIN) {
                    if (userbody.getUsername() != null && !userbody.getUsername().equals(existingUser.getUsername())) {
                        // Mettre à jour le nom d'utilisateur
                        existingUser.setUsername(userbody.getUsername());
                        userRepository.save(existingUser);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous ne pouvez pas changer le username d'un autre utilisateur"));
                }

            }

            if (userJw.isPresent()) {
                if (userJw.get().getRole() == UserRole.ROLE_ADMIN) {
                    if (userbody.getRole() != null && userbody.getRole() != existingUser.getRole()) {
                        // Mettre à jour le rôle
                        existingUser.setRole(userbody.getRole());
                        userRepository.save(existingUser);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de changer le role de l'utilisateur"));
                }
            }

            String mToken = workerjob.getValidToken();
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("token", mToken);

            String jsonString = mapper.writeValueAsString(result);
            return ResponseEntity.status(HttpStatus.OK).body(jsonString);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Error"));
        }

    }
*/

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User userbody) {

        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> userToUpdate = userRepository.findById(id);
            Optional<User> userJw = userRepository.findByUsername(userName);

            if (!userToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
            }

            User existingUser = userToUpdate.get();

            // Vérifier d'abord si l'utilisateur peut mettre à jour son propre profil
            if (userJw.isPresent() && userJw.get().getId() == existingUser.getId()) {
                if (userbody.getUsername() != null && !userbody.getUsername().equals(existingUser.getUsername())) {
                    // Mettre à jour le nom d'utilisateur
                    existingUser.setUsername(userbody.getUsername());
                }

                // Si l'utilisateur a le rôle ROLE_ADMIN, vérifiez également le rôle

                    if (userbody.getRole() != null && userbody.getRole() != existingUser.getRole()) {
                        // Mettre à jour le rôle
                        if (userJw.get().getRole() == UserRole.ROLE_ADMIN) {
                            existingUser.setRole(userbody.getRole());
                        }
                }

                userRepository.save(existingUser);

                String mToken = workerjob.getValidToken();
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("token", mToken);

                String jsonString = mapper.writeValueAsString(result);
                return ResponseEntity.status(HttpStatus.OK).body(jsonString);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission nécessaire."));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Error"));
        }
    }

*/

/*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> userToUpdate = userRepository.findById(id);

            if (!userToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
            }

            User existingUser = userToUpdate.get();

            // Vérifiez si l'utilisateur actuel est ROLE_ADMIN ou si l'utilisateur essaie de modifier son propre profil
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
                    userName.equals(existingUser.getUsername())) {

                if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().equals(existingUser.getUsername())) {
                    // Vérifier si le nouveau nom d'utilisateur est différent de l'ancien
                    if (!userUpdateDTO.getUsername().equals(userName)) {
                        // Vérifier si le nouveau nom d'utilisateur existe déjà
                        Optional<User> userWithNewUsername = userRepository.findByUsername(userUpdateDTO.getUsername());
                        if (userWithNewUsername.isPresent()) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Le nom d'utilisateur existe déjà"));
                        }
                        existingUser.setUsername(userUpdateDTO.getUsername());
                    }
                }

                if (userUpdateDTO.getRole() != null && userUpdateDTO.getRole() != existingUser.getRole()) {
                    // Si l'utilisateur actuel a le rôle ROLE_USER, il ne peut pas changer le rôle d'un autre utilisateur
                    if (existingUser.getRole() == UserRole.ROLE_USER) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de changer le rôle de cet utilisateur"));
                    }
                    // Mettre à jour le rôle
                    existingUser.setRole(userUpdateDTO.getRole());
                }

                userRepository.save(existingUser);

                String mToken = workerjob.getValidToken();
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("token", mToken);

                String jsonString = mapper.writeValueAsString(result);
                return ResponseEntity.status(HttpStatus.OK).body(jsonString);
            } else {
                // L'utilisateur n'est pas autorisé à modifier cet utilisateur, renvoyer une réponse FORBIDDEN
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de modifier cet utilisateur"));
            }
        }catch (Exception ex) {
            // Gérer d'autres exceptions de sauvegarde ici
            System.out.println("Erreur lors de la mise à jour du profil de l'utilisateur : " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Erreur("Erreur interne du serveur"));
        }
    }

*/


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody User userbody) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> userToUpdate = userRepository.findById(id);
            Optional<User> userAuth = userRepository.findByUsername(userName);

            if (!userToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur("Utilisateur introuvable"));
            }

            User existingUser = userToUpdate.get();

            if (userAuth.isPresent() && userAuth.get().getRole() == UserRole.ROLE_USER && userAuth.get().getId() == existingUser.getId() || userAuth.isPresent() && userAuth.get().getRole() == UserRole.ROLE_ADMIN) {
                // Vérifier si l'utilisateur actuel est le propriétaire de l'utilisateur cible
                if (userbody.getUsername() != null && !userbody.getUsername().equals(existingUser.getUsername())) {
                    // Mettre à jour le nom d'utilisateur
                    existingUser.setUsername(userbody.getUsername());
                    // Sauvegarder les modifications directement
                    userRepository.save(existingUser);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous ne pouvez pas changer le username d'un autre utilisateur"));
            }

            if (userAuth.get().getRole() == UserRole.ROLE_ADMIN) {
                // Vérifier si l'utilisateur actuel est un administrateur
                if (userbody.getRole() != null) {
                    // Mettre à jour le rôle
                    existingUser.setRole(userbody.getRole());
                    // Sauvegarder les modifications directement
                    userRepository.save(existingUser);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Erreur("Vous n'avez pas la permission de changer le rôle de l'utilisateur"));
                }
            }

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("id", existingUser.getId());
            responseMap.put("username", existingUser.getUsername());
            responseMap.put("role", existingUser.getRole());
            responseMap.put("creation_date", existingUser.getcreation_date());

// Retournez la Map dans la réponse
            return ResponseEntity.status(HttpStatus.OK).body(responseMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erreur("Error"));
        }
    }






/*
    @GetMapping("/users")
    public ResponseEntity<?> getList(@RequestParam(defaultValue="0") Integer page , @RequestParam(defaultValue="5") Integer limit){
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(userName);
            if (user.get().getRole() == UserRole.ROLE_ADMIN) {
                PageRequest pageable = PageRequest.of(page, limit);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(userRepository.getListByPage(pageable));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Erreur("Utilisateur non habilité"));

        }catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Error"));
        }

    }
*/

    /*
    @GetMapping("/users")
    public ResponseEntity<?> getList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer limit) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(userName);

            if (user.get().getRole() == UserRole.ROLE_ADMIN) {
                PageRequest pageable = PageRequest.of(page, limit);
                List<User> userList = userRepository.getListByPage(pageable);

                Map<UsersResponseDTO, List<AddressResponseDTO>> userAddressMap = new HashMap<>();

                for (User u : userList) {
                    List<Address> userAddresses = addressRepository.findAllForUserId(u.getId());
                    List<AddressResponseDTO> addressDTOs = new ArrayList<>();

                    for (Address address : userAddresses) {
                        AddressResponseDTO addressDTO = convertToAddressResponseDTO(address);
                        addressDTOs.add(addressDTO);
                    }

                    UsersResponseDTO userDTO = new UsersResponseDTO();
                    userDTO.setId(u.getId());
                    userDTO.setUsername(u.getUsername());
                    userDTO.setRole(u.getRole().toString());
                    userDTO.setCreationDate(u.getcreation_date());
                    userDTO.setAddressesList(addressDTOs);

                    userAddressMap.put(userDTO, addressDTOs);
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .body(userAddressMap);
            } else {
                List<Address> userAddresses = addressRepository.findAllForUserId(user.get().getId());

                UsersResponseDTO currentUserDTO = new UsersResponseDTO();
                currentUserDTO.setId(user.get().getId());
                currentUserDTO.setUsername(user.get().getUsername());
                currentUserDTO.setRole(user.get().getRole().toString());
                currentUserDTO.setCreationDate(user.get().getcreation_date());

                if (!userAddresses.isEmpty()) {
                    List<AddressResponseDTO> addressDTOs = new ArrayList<>();

                    for (Address address : userAddresses) {
                        AddressResponseDTO addressDTO = convertToAddressResponseDTO(address);
                        addressDTOs.add(addressDTO);
                    }

                    currentUserDTO.setAddressesList(addressDTOs);
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .body(currentUserDTO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Une erreur s'est produite."));
        }
    }

*/
    @GetMapping("")
    public ResponseEntity<?> getList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer limit) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(userName);

            if (user.get().getRole() == UserRole.ROLE_ADMIN) {
                Pageable pageable = PageRequest.of(page, limit);

                List<User> userList = userRepository.getListByPage(pageable); // Utilisez List<User> au lieu de Page<User>

                List<UsersResponseDTO> userDTOList = new ArrayList<>();

                for (User u : userList) {
                    List<Address> userAddresses = addressRepository.findAllForUserId(u.getId());
                    List<AddressResponseDTO> addressDTOs = convertToAddressResponseDTOList(userAddresses);

                    UsersResponseDTO userDTO = new UsersResponseDTO();
                    userDTO.setId(u.getId());
                    userDTO.setUsername(u.getUsername());
                    userDTO.setRole(u.getRole().toString());
                    userDTO.setCreationDate(u.getcreation_date());
                    userDTO.setAddressesList(addressDTOs);

                    userDTOList.add(userDTO);
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .body(userDTOList);
            } else {
                List<Address> userAddresses = addressRepository.findAllForUserId(user.get().getId());

                UsersResponseDTO currentUserDTO = new UsersResponseDTO();
                currentUserDTO.setId(user.get().getId());
                currentUserDTO.setUsername(user.get().getUsername());
                currentUserDTO.setRole(user.get().getRole().toString());
                currentUserDTO.setCreationDate(user.get().getcreation_date());
                currentUserDTO.setAddressesList(convertToAddressResponseDTOList(userAddresses));

                return ResponseEntity.status(HttpStatus.OK)
                        .body(currentUserDTO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Une erreur s'est produite."));
        }
    }




    private AddressResponseDTO convertToAddressResponseDTO(Address address) {
        AddressResponseDTO addressDTO = new AddressResponseDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCreationDate(address.getCreationDate()); // Assurez-vous que votre modèle Address a une méthode getCreation_date() pour obtenir la date de création.
        return addressDTO;
    }




    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().toString());
        return userDTO;
    }


/*
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete( ) {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();
            Optional<User> user = userRepository.findByUsername(userName);

            List<Address> list= this.addressRepository.findAllForUserId(user.get().getId());
            for (int i=0;i<list.size();i++) {
                this.addressRepository.delete(list.get(i));;
            }
            userRepository.delete(user.get());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user.get());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Erreur("Error"));
        }
    }
*/
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteOther(@PathVariable int id) {
    try {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(userName);
        Optional<User> userToDelete = userRepository.findById(id);

        if (!userToDelete.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }

        if (user.get().getRole() == UserRole.ROLE_ADMIN || user.get().getId() == userToDelete.get().getId()) {
            List<Address> addressesToDelete = addressRepository.findAllForUserId(userToDelete.get().getId());

            for (Address address : addressesToDelete) {
                addressRepository.delete(address);
            }

            userRepository.delete(userToDelete.get());

            // Vous pouvez choisir ce que vous voulez renvoyer ici, par exemple, un message de succès.
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Utilisateur supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Utilisateur non habilité à effectuer cette action");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erreur lors de la suppression de l'utilisateur");
    }
}


}
