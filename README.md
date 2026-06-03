# LAB 10 : Démo Navigation Drawer et Fragments 📱

## 📝 Description
Ce projet est une application Android développée en **Kotlin** illustrant la mise en œuvre d'un menu de navigation latéral (**Navigation Drawer**) et la gestion dynamique des **Fragments**. 

L'objectif principal est de naviguer entre différents écrans (fragments) au sein d'une seule et même activité (`MainActivity`), tout en respectant les standards modernes de développement Android.

---

## deemo vedio 




https://github.com/user-attachments/assets/7c861c69-8c36-4006-b3bb-1ef6f53417a6


Par rapport aux étapes de base du laboratoire, j'ai ajouté plusieurs améliorations pour rendre l'application plus professionnelle :

1.  **View Binding** : Utilisation du View Binding pour éliminer les `findViewById`, rendant le code plus performant et évitant les erreurs de type `null pointer`.
2.  **Toolbar Dynamique** : La barre d'outils (`Toolbar`) met à jour son titre automatiquement selon le fragment sélectionné.
3.  **Animations de Transition** : Ajout d'animations fluides (fondu entrant/sortant) lors du remplacement des fragments.
4.  **Gestion du Bouton Retour** : Intégration de `OnBackPressedDispatcher` pour fermer le menu latéral s'il est ouvert avant de quitter l'application.
5.  **En-tête Personnalisé (Header)** : Création d'un `nav_header_main.xml` élégant avec une icône de profil et des informations utilisateur.

---

## 🚀 Fonctionnalités
- **Navigation Drawer** : Menu coulissant permettant d'accéder aux différentes sections.
- **Fragments de Base** :
    - `Fragment 1` : Un écran avec un fond rose (`#F8BBD0`).
    - `Fragment 2` : Un écran avec un fond bleu (`#3F51B5`).
- **Fragment de Liste** : 
    - `Fragment List` : Utilisation de `ListFragment` pour afficher une liste d'éléments (`Item 1` à `Item 10`) de manière optimisée.
- **Indicateur de sélection** : L'élément actif dans le menu est automatiquement mis en surbrillance.

---

## 📂 Structure du Projet

### 🎨 Layouts (XML)
- `activity_main.xml` : Contient le `DrawerLayout`, la `Toolbar` et le `NavigationView`.
- `content_main.xml` : Définit la zone (`FrameLayout`) où les fragments sont injectés.
- `nav_header_main.xml` : Design de la partie supérieure du menu latéral.
- `fragment_blank.xml` & `fragment_blank2.xml` : Designs simples pour les fragments de démo.

### 💻 Code Source (Kotlin)
- `MainActivity.kt` : Gère les interactions avec le menu et les transactions de fragments.
- `BlankFragment.kt` & `BlankFragment2.kt` : Contrôleurs simples pour les vues colorées.
- `FragmentList.kt` : Hérite de `ListFragment` pour gérer l'affichage de données sous forme de liste.

---

## 🛠 Configuration Technique
- **Language** : Kotlin
- **Architecture** : View-based (XML) avec Fragments
- **Bibliothèques clés** :
    - `androidx.appcompat`
    - `com.google.android.material` (Navigation Drawer, Toolbar)
    - `androidx.fragment:fragment-ktx`

---

## 📸 Installation
1. Cloner le projet.
2. Ouvrir avec **Android Studio (Ladybug ou plus récent)**.
3. Synchroniser le projet avec **Gradle**.
4. Lancer sur un émulateur ou un appareil physique (API 24+).

---
**Cours : Programmation Mobile : Android avec Java (Adapté en Kotlin)**
**LAB 10**
