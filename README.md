# Api-rest
Projet IDV-WEB4 "API REST" avec 6 etapes progressives. Le depot contient les
sujets (PDF) et les livrables par etape, allant d'un backend Spring Boot securise
jusqu'a un projet libre avec frontend.

## Documentation
- `documentation/API REST.pdf`: presentation generale, prerequis et objectifs.
- `documentation/IDV-WEB4 _ API REST _ Etape 1 (Java).pdf` a `Etape 5.pdf`:
  consignes detaillees par etape.
- `documentation/IDV-WEB4 _ API REST _ Projet libre.pdf`: cahier des charges du projet final.

## Structure du depot
- `Etape_1/` a `Etape_4/`: backend Spring Boot (Maven) avec progression des fonctionnalites.
- `Etape_5/Angular/`: frontend Angular 11 qui consomme l'API.
- `Etape_6/Projet_libre/project/HarboBook/`: projet final (backend Spring Boot + frontend Angular).

## Resume des etapes (1 a 6)
1. Initialisation Spring Boot (Java 17/20), port 8090, endpoints de test dans
   `DefaultController`.
2. Ajout JPA + MySQL, configuration datasource, creation de l'entite `User`
   et de `UserRole`.
3. Securisation JWT: dependances security, utilitaire JWT, service `UserDetails`,
   endpoint `/authenticate`, filtre de requetes.
4. Nouvelles entites et REST: `Address` (relation Many-To-One), CRUD adresses,
   controle d'acces par role, controller utilisateurs.
5. Frontend Angular: inscription/connexion, liste utilisateurs, gestion profils
   et adresses (droits User/Admin).
6. Projet libre: application complete avec au moins 4 tables, roles, et
   fonctionnalites definies. Implementation presentee dans `HarboBook`.

## Notes techniques
- Backend base sur Spring Boot 3.1.x et Java 17.
- Base de donnees MySQL 5.7/8.0 (voir prerequis dans la doc).

## Execution (par etape)

### Etapes 1 a 5 (backend Spring Boot)
Prerequis: Java 17, Maven wrapper, MySQL.
La config MySQL est dans `Etape_*/quest_web_java/src/main/resources/application.properties`
(base `quest_web`, user `application`, password `password`).

```bash
cd Etape_1/quest_web_java
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```

Repetez la meme commande pour `Etape_2` a `Etape_5`.
Le serveur ecoute sur `http://localhost:8090`.

### Etape 5 (frontend Angular)
```bash
cd Etape_5/Angular
npm install
npm start
```
Par defaut, l'app tourne sur `http://localhost:4200`.

### Etape 6 (projet libre HarboBook)
Backend:
```bash
cd Etape_6/Projet_libre/project/HarboBook/backend/quest_web_java
./mvnw spring-boot:run
```

Frontend:
```bash
cd Etape_6/Projet_libre/project/HarboBook/frontend/angular
npm install
npm start
```

## Projet libre: HarboBook
Application web autour de la gestion de livres, avec authentification JWT et
roles utilisateurs.
- Auth: `/register`, `/authenticate`, `/me`
- Utilisateurs: liste, detail, modification et suppression (role admin)
- Adresses: CRUD et controle d'acces par utilisateur
- Livres: CRUD avec restrictions (role admin pour ajout/modif/suppression)

Backend: `Etape_6/Projet_libre/project/HarboBook/backend/quest_web_java`
Frontend: `Etape_6/Projet_libre/project/HarboBook/frontend/angular`
