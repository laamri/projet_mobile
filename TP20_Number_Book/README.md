# TP 20 - Application Number Book

Cette application Android permet de gérer des contacts en les synchronisant avec une base de données distante (MySQL) via une API REST développée en PHP et consommée avec Retrofit.

## demo  vedio



https://github.com/user-attachments/assets/8fd7dc5f-7748-436c-a1ba-26ad753cd7c9




## 🚀 Fonctionnalités
- **Chargement local** : Lecture des contacts du téléphone via `ContentResolver`.
- **Synchronisation** : Envoi des contacts locaux vers le serveur distant (MySQL).
- **Recherche distante** : Recherche de contacts dans la base de données globale par nom ou par numéro.
- **Affichage moderne** : Utilisation de `RecyclerView` pour une liste fluide.

## 🛠️ Stack Technique
- **Android** : Java, Retrofit 2, Gson, RecyclerView.
- **Backend** : PHP 7/8 (PDO).
- **Base de données** : MySQL.
- **Réseau** : Communication asynchrone via `enqueue`.

## 📂 Structure du Projet
- `app/` : Code source de l'application Android.
- `backend/` : Scripts PHP et SQL pour le serveur.
    - `numberbook-api/` : Dossier à copier dans `htdocs` (XAMPP).
    - `schema.sql` : Script de création de la base de données.

## ⚙️ Configuration

### 1. Serveur (XAMPP)
1. Copiez le dossier `backend/numberbook-api` dans `C:/xampp/htdocs/`.
2. Importez `backend/schema.sql` dans phpMyAdmin pour créer la base `numberbook`.
3. Lancez Apache et MySQL.

### 2. Android Studio
1. Le projet est configuré avec le **SDK 36**.
2. Dans `RetrofitClient.java`, l'URL de base est `http://10.0.2.2/`. 
   - *Note : `10.0.2.2` est l'adresse pour accéder au localhost du PC depuis l'émulateur Android.*

## 📖 Comment tester
1. Créez des contacts manuellement dans l'application "Contacts" de l'émulateur.
2. Ouvrez **Number Book**.
3. Cliquez sur **Charger les contacts** (Acceptez la permission).
4. Cliquez sur **Synchroniser vers le serveur**.
5. Vérifiez votre base MySQL via phpMyAdmin.
6. Utilisez le champ **Rechercher** pour filtrer les contacts stockés sur le serveur.
