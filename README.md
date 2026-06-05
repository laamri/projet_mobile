# 🛡️ SecureStorageLab - LAB 14 : Sauvegarde & Sécurité

Ce projet est une étude approfondie de la persistance des données sous Android, mettant l'accent sur la sécurité et le respect des bonnes pratiques de développement.

---

## 🎯 Objectifs d'apprentissage
- Différencier le stockage **synchrone (commit)** et **asynchrone (apply)**.
- Implémenter le chiffrement matériel via le **Keystore** Android.
- Gérer la sérialisation **JSON** pour les objets complexes.
- Comprendre les différents niveaux d'isolation des fichiers (`MODE_PRIVATE`, `cache`, `external`).

---

## 🛠️ Détails des Tâches Réalisées

### Tâche 1 : Configuration & Crypto
- Intégration de `androidx.security:security-crypto`.
- Configuration du projet pour cibler le SDK 36 (Android 15).

### Tâche 2 : SharedPreferences (Lecture/Écriture)
- Stockage des données non sensibles : Nom, Langue, Thème.
- Implémentation de la classe `Triple` pour regrouper les données de préférence sans dépendance externe.
- Utilisation de `apply()` pour une écriture en arrière-plan sans bloquer l'interface.

### Tâche 3 : Sécurité Avancée (EncryptedSharedPreferences)
- Chiffrement du Token API avec une clé AES-256 générée par le système.
- **Sécurité logicielle** : Masquage du token dans l'UI (`textPassword`).
- **Sécurité système** : Stockage des clés privées dans la puce de sécurité du téléphone.

### Tâche 4 : Fichiers Internes & JSON
- Création d'un magasin JSON pour la classe `Student`.
- Utilisation d'un `ByteArrayOutputStream` pour lire les fichiers de manière robuste sur toutes les versions d'Android.
- Stockage UTF-8 imposé pour éviter les erreurs d'encodage entre différents appareils.

---

## 📋 Checklist de Sécurité (Validée)

1.  ✅ **Zéro Fuite de Logs** : Aucun Token n'apparaît dans le Logcat.
2.  ✅ **Chiffrement Hardware** : Utilisation de `MasterKey` pour protéger les SharedPreferences.
3.  ✅ **Confidentialité** : Utilisation stricte de `MODE_PRIVATE`.
4.  ✅ **Masquage UI** : Token saisi via un champ mot de passe.
5.  ✅ **Nettoyage Garanti** : Suppression complète des fichiers et préférences via le bouton "Effacer".
6.  ✅ **Gestion du Cache** : Les données temporaires sont isolées dans `cacheDir`.
7.  ✅ **Export Maîtrisé** : Le stockage externe utilisé est spécifique à l'app (pas de dossier public).
8.  ✅ **Intégrité JSON** : Gestion des erreurs si le fichier JSON est corrompu ou absent.
9.  ✅ **Encodage Robuste** : Utilisation systématique de `StandardCharsets.UTF_8`.
10. ✅ **Validation Manuelle** : Vérification effectuée via le **Device File Explorer**.

---

## 🚀 Guide d'Utilisation Technique

### Inspection des fichiers
Pour vérifier le fonctionnement réel, utilisez l'explorateur de fichiers d'Android Studio :
- **Chemin des fichiers** : `/data/data/com.example.securestoragejava/`
- **Fichiers attendus** :
    - `shared_prefs/app_prefs.xml` : Stockage XML classique.
    - `shared_prefs/secure_prefs.xml` : Stockage chiffré (AES).
    - `files/students.json` : Données structurées.
    - `files/metadata.txt` : Horodatage des sauvegardes.

### Dépannage
- **Symbol 'R' non résolu** : Cela arrive souvent après un changement de package. Utilisez `Build > Clean Project` puis `Rebuild Project`.
- **Token vide** : Assurez-vous d'avoir cliqué sur "Sauvegarder les données" après avoir saisi le token.
- **Fichiers invisibles** : Faites un clic droit sur le dossier de l'app dans le Device File Explorer et sélectionnez "Synchronize".

---

## 📦 Dépendances Principales
- `androidx.appcompat:appcompat` : Support de l'interface.
- `androidx.security:security-crypto` : APIs de chiffrement.
- `org.json` : Manipulation du format JSON.

---
*Développé dans le cadre du module Programmation Mobile - Java.*
