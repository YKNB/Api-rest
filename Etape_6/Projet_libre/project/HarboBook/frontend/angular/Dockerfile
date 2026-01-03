# Étape 1 : Utilisez une image de base Node.js pour construire l'application Angular
FROM node:14 AS builder

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez les fichiers du projet local dans le conteneur
COPY . .

# Installez les dépendances et créez la version de production de l'application
RUN npm install
RUN npm run build --prod

# Étape 2 : Utilisez une image de base Nginx pour exécuter l'application Angular
FROM nginx:latest

# Copiez les fichiers de l'étape précédente (builder) dans le conteneur Nginx
COPY --from=builder /app/dist/IDV-WEB-Projet /usr/share/nginx/html

# Nginx expose automatiquement le port 80

# La commande CMD pour l'image Nginx est suffisante
CMD ["nginx", "-g", "daemon off;"]
