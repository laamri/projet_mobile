# Lab 21 : Capteurs Embarqués Android

Ce projet est une application Android complète développée en Java permettant d'explorer et d'exploiter les différents capteurs matériels d'un smartphone.


## demo vedio 


https://github.com/user-attachments/assets/fb1c18e5-3859-4cf2-8f4a-e7222a29d70d




## 📱 Fonctionnalités

L'application est organisée via un menu latéral (Navigation Drawer) permettant d'accéder aux modules suivants :

1.  **Liste des Capteurs** : Affiche tous les capteurs disponibles sur l'appareil avec leurs spécifications techniques (Vendeur, Résolution, Puissance, Portée maximale, etc.).
2.  **Visualisation en Temps Réel** : Un moteur de graphique personnalisé (`LineChartView`) permet de suivre l'évolution des données pour :
    *   Température ambiante
    *   Humidité relative
    *   Capteur de proximité
    *   Champ magnétique (Norme/Magnitude)
3.  **Capteurs de Mouvement** :
    *   **Accéléromètre** : Mesure l'accélération sur les axes X, Y, Z.
    *   **Gravité** : Isole la force gravitationnelle.
    *   **Gyroscope** : Mesure la vitesse de rotation en rad/s.
4.  **Boussole Numérique** : Combine l'accéléromètre et le magnétomètre pour déterminer l'orientation précise du téléphone (Nord, Sud, Est, Ouest).
5.  **Compteur de Pas** : Utilise le capteur `TYPE_STEP_COUNTER` pour suivre les pas depuis le démarrage et durant la session actuelle (Gestion des permissions dynamiques incluse).
6.  **Reconnaissance d'Activité** : Algorithme basé sur l'accéléromètre et un filtre passe-bas pour détecter si l'utilisateur est :
    *   Stable (immobile)
    *   En train de marcher
    *   En train de sauter
    *   Assis ou debout (selon l'inclinaison)

## 🏗️ Structure du Projet

Le code est structuré de manière modulaire :

*   `MainActivity.java` : Point d'entrée gérant la navigation et le cycle de vie des fragments. Utilise `OnBackPressedDispatcher` pour une navigation moderne.
*   `fragments/` : Contient un fragment dédié pour chaque capteur ou fonctionnalité, facilitant la maintenance.
*   `views/LineChartView.java` : Composant graphique personnalisé utilisant l'API `Canvas` pour dessiner les courbes sans dépendances externes.
*   `utils/SensorFormatter.java` : Utilitaire pour transformer les données brutes des capteurs en texte lisible.

## 🛠️ Concepts Techniques Utilisés

*   **SensorManager** : Service système pour accéder aux capteurs.
*   **SensorEventListener** : Interface pour recevoir les mises à jour des capteurs en temps réel.
*   **Filtre Passe-Bas (Low-pass Filter)** : Utilisé dans la reconnaissance d'activité pour séparer la gravité du mouvement linéaire.
*   **Gestion des Permissions** : Implémentation de `ActivityResultLauncher` pour la permission `ACTIVITY_RECOGNITION`.
*   **Navigation Drawer** : Interface utilisateur moderne avec `DrawerLayout` et `NavigationView`.

## 🚀 Installation

1. Clonez le dépôt.
2. Ouvrez le projet dans Android Studio.
3. Compilez et lancez sur un appareil physique (recommandé pour les capteurs) ou un émulateur.

---
*Projet réalisé dans le cadre du cours de Programmation Mobile.*
