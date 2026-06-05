# Lab 19 : Room, MVVM, Repository, ViewModel, LiveData et RecyclerView

## 📝 Présentation du Projet
Ce laboratoire démontre l'implémentation d'une architecture Android moderne (**MVVM**) pour une application de gestion de notes. L'objectif est d'assurer une séparation claire des responsabilités, une persistance des données locale et une interface utilisateur réactive.

## DEMO VEDIO


https://github.com/user-attachments/assets/96e3896b-831e-4755-a093-0e9cc1c37c51




## 🚀 Fonctionnalités
*   **Ajout de notes** : Saisie d'un titre et d'une description.
*   **Affichage en temps réel** : Liste gérée par `RecyclerView` avec mise à jour automatique via `LiveData`.
*   **Suppression unitaire** : Suppression d'une note spécifique via un **clic long**.
*   **Suppression globale** : Bouton pour vider toute la base de données.
*   **Persistance locale** : Les données sont sauvegardées dans une base de données **Room (SQLite)**.
*   **Résistance aux changements** : Les données survivent à la rotation de l'écran grâce au `ViewModel`.

## 🏗️ Architecture (MVVM)
L'application suit le modèle recommandé par Google :
1.  **Vue (Activity/XML)** : Affiche les données et capture les actions utilisateur.
2.  **ViewModel** : Prépare les données pour la vue et survit aux changements de configuration.
3.  **Repository** : Gère la logique d'accès aux données (source unique de vérité).
4.  **Room Database** : Couche de persistance locale au-dessus de SQLite.

## 💻 Code Principal

### Les Dépendances
```gradle
dependencies {
    implementation "androidx.room:room-runtime:2.6.1"
    annotationProcessor "androidx.room:room-compiler:2.6.1"
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.8.7"
    implementation "androidx.lifecycle:lifecycle-livedata:2.8.7"
}
```

### Couches de Données
*   **Note.java** : L'entité `@Entity` définissant la table SQL.
*   **NoteDao.java** : L'interface `@Dao` contenant les requêtes SQL (Insert, Delete, Query).
*   **NoteRepository.java** : Utilise `ExecutorService` pour exécuter les tâches en arrière-plan afin de ne pas bloquer l'UI.

### Interface Utilisateur
*   **NoteAdapter.java** : Gère le recyclage des vues et les listeners de clics.
*   **MainActivity.java** : Observe le `LiveData` du ViewModel pour rafraîchir la liste automatiquement.

## 🛠️ Installation et Tests
1.  Cloner le projet.
2.  Synchroniser Gradle.
3.  Lancer l'application sur un émulateur ou un appareil physique.
4.  **Test conseillé** : Ajoutez une note, fermez l'app, et rouvrez-la. Vos données seront toujours présentes !
