package com.quest.etna;

import com.quest.etna.dto.AddressDTO;
import com.quest.etna.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String authToken; // Vous stockerez ici le token pour les tests qui nécessitent l'authentification.

    @BeforeEach
    public void setup() throws Exception {
        // Cette méthode vous permet d'obtenir un token d'authentification valide.
        // Vous pouvez la personnaliser en fonction de votre application.
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"karl\",\"password\":\"karl\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        authToken = result.getResponse().getContentAsString();
    }

    @Test
    @DirtiesContext
    @Order(1)
    public void testAuthenticate() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Ganba");
        userDTO.setPassword("Ganba");


        // Testez la route /register répond bien en 201.
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
//Tester encore la route /register avec les mêmes paramètres, pour obtenir une réponse 409 pour verifier que l'utilisateur existe déjà
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
//Tester la route /authenticate retourne bien un statut 200 ainsi que votre token
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON) // Set content type to JSON
                        .content(asJsonString(userDTO))) // Set the JSON body
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Créez un token de test avec la clé secrète de test pour supprimer les confilts de validation de token
        TestJwtTokenUtil testJwtTokenUtil = new TestJwtTokenUtil();
        Map<String, Object> extraClaims = new HashMap<>();
        String testToken = testJwtTokenUtil.generateTestToken(userDTO.getUsername(), extraClaims);
        //Tester la route /me retourne un statut 200 avec les informations de l'utilisateur
        mockMvc.perform(MockMvcRequestBuilders.get("/me")
                        .header("Authorization", "Bearer " + testToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate").exists());
    }



    @Test
    @DirtiesContext
    @Order(2)
    public void testUser() throws Exception {
    // Testez la route /user sans token Bearer (devrait échouer avec un statut 401).
        TestJwtTokenUtil testJwtTokenUtil = new TestJwtTokenUtil();
        Map<String, Object> extraClaims = new HashMap<>();
        String testToken = testJwtTokenUtil.generateTestToken("Ao", extraClaims);

        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Testez la route /user avec un token Bearer valide (devrait réussir avec un statut 200).
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .header("Authorization", "Bearer " + testToken))
                .andExpect(MockMvcResultMatchers.status().isOk());


        String testTokenUser = testJwtTokenUtil.generateTestToken("Ao", extraClaims);
        // Testez la suppression avec un rôle ROLE_USER (devrait échouer avec un statut 403).
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", 23)
                        .header("Authorization", "Bearer " + testTokenUser))
                .andExpect(MockMvcResultMatchers.status().isForbidden());



        String testTokenAdmin = testJwtTokenUtil.generateTestToken("karl", extraClaims);
        // Testez la suppression avec un rôle ROLE_ADMIN (devrait réussir avec un statut 200).
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", 32)
                        .header("Authorization", "Bearer " + testTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    @DirtiesContext
    @Order(3)
    public void testAddress() throws Exception {


        // Testez la route /address/ sans token Bearer (devrait échouer avec un statut 401).
        mockMvc.perform(MockMvcRequestBuilders.get("/address"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());


        TestJwtTokenUtil testJwtTokenUtil = new TestJwtTokenUtil();
        Map<String, Object> extraClaims = new HashMap<>();
        // Ajoutez des réclamations supplémentaires si nécessaire
        String testToken = testJwtTokenUtil.generateTestToken("kaleb", extraClaims);

        // Testez la route /address/ avec un token Bearer valide (devrait réussir avec un statut 200).
        mockMvc.perform(MockMvcRequestBuilders.get("/address")
                        .header("Authorization", "Bearer " + testToken))
                .andExpect(MockMvcResultMatchers.status().isOk());



        // Testez l'ajout d'une adresse (devrait réussir avec un statut 201).
        mockMvc.perform(MockMvcRequestBuilders.post("/address")
                        .header("Authorization", "Bearer " + testToken)
                        .contentType("application/json")
                        .content("{\"street\":\"fourgone\",\"postalCode\":\"35000\",\"city\":\"Rennes\",\"country\":\"France\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        // Testez la suppression d'une adresse avec un rôle ROLE_USER qui n'est pas le sien (devrait échouer avec un statut 403).
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/{address}", 6)
                        .header("Authorization", "Bearer " + testToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());


        String testTokenAdmin = testJwtTokenUtil.generateTestToken("karl", extraClaims);
        // Testez la suppression d'une adresse avec un rôle ROLE_ADMIN qui n'est pas le sien (devrait réussir avec un statut 200).
        mockMvc.perform(MockMvcRequestBuilders.delete("/address/{address}", 21)
                        .header("Authorization", "Bearer " + testTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
