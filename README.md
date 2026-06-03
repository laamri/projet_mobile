# Géolocalisation Mobile et Suivi en Temps Réel

Ce projet est une application Android complète permettant de récupérer les coordonnées géographiques (Latitude, Longitude, Altitude) d'un smartphone et de les envoyer automatiquement vers un serveur distant PHP/MySQL.

## 🚀 Fonctionnalités

- **Détection GPS :** Récupération précise de la position via `LocationManager`.
- **Transmission Réseau :** Envoi des données au format POST via la bibliothèque **Volley**.
- **Interface Moderne :** Utilisation de `CardView`, `Button` stylisé et `ProgressBar`.
- **Identification Unique :** Gestion intelligente de l'identifiant (IMEI pour les anciens, Android ID pour Android 10+).
- **Architecture Pro :** Backend PHP structuré avec le pattern **DAO**.

---

## 🏗️ Architecture du Système

### 1. Partie Mobile (Android Java)
- Gestion dynamique des permissions au runtime.
- Mise à jour automatique (toutes les 1 min / 150m).
- File d'attente Volley pour la robustesse des envois.

### 2. Partie Serveur (Backend PHP)
- **Base de données :** MySQL (Table `position`).
- **Structure :** 
  - `classe/` : Modèle Position.
  - `connexion/` : Singleton PDO.
  - `dao/` : Interface IDao.
  - `service/` : PositionService (SQL).
  - `createPosition.php` : API REST simplifiée.

---

## 🛠️ Installation et Configuration

### Étape 1 : Base de Données (XAMPP/WAMP)
Créez la base `localisation` et la table :
```sql
CREATE TABLE position (
    id INT AUTO_INCREMENT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    date_position DATETIME NOT NULL,
    imei VARCHAR(50) NOT NULL
);
```

### Étape 2 : Serveur PHP
Copiez le dossier `server/` dans votre dossier `htdocs` ou `www`. 
Testez l'accès : `http://localhost/localisation/createPosition.php`.

### Étape 3 : Application Android
Dans `MainActivity.java`, configurez la variable `SERVER_IP` :
- **Émulateur :** Utilisez `10.0.2.2`.
- **Vrai Téléphone :** Utilisez l'IP de votre PC (ex: `192.168.1.15`).

---

## 🧪 Comment Tester la Localisation ?

Si vous êtes sur émulateur et que les boutons de contrôle sont bloqués :
1. Ouvrez le **Terminal** dans Android Studio (en bas).
2. Tapez la commande suivante pour simuler une position à Paris :
   ```bash
   adb emu geo fix 2.3522 48.8566
   ```
3. L'application mettra à jour l'affichage et enverra les données à MySQL instantanément.

---

## 📝 Auteur
Réalisé dans le cadre du TP de Programmation Mobile Android.
*Améliorations apportées : Interface Material Design, compatibilité Android 10+ et simulation de trajet.*
