# LAB 18 : ViewModel et LiveData (Android Java)

Ce projet est une application de démonstration interactive illustrant l'utilisation des composants d'architecture Android (**Jetpack**) : **ViewModel** et **LiveData**. 

L'objectif est de créer un compteur robuste qui survit aux changements de configuration (comme la rotation de l'écran) et qui met à jour l'interface utilisateur de manière réactive et sécurisée.

## 🚀 Fonctionnalités
- **Incrémenter / Décrémenter** : Gestion d'un état numérique simple.
- **Réinitialiser** : Remise à zéro du compteur.
- **Survie à la Rotation** : Le chiffre reste intact même si vous tournez votre téléphone.
- **Feedback Visuel (Ma Touche)** : La couleur du texte change dynamiquement :
  - **Vert** pour les nombres positifs.
  - **Rouge** pour les nombres négatifs.
  - **Noir** pour zéro.

## 🧠 Concepts Clés Appliqués

### 1. ViewModel (Le Cerveau)
Le `CounterViewModel` étend la classe `ViewModel`. Contrairement à une Activity, le ViewModel n'est pas détruit lors d'une rotation d'écran. 
- **Pourquoi ?** Parce qu'il est lié au cycle de vie de l'application via un `ViewModelStore`. 
- **Bénéfice** : Fini les pertes de données et les codes complexes avec `onSaveInstanceState`.

### 2. LiveData (L'Observateur)
Nous utilisons `MutableLiveData<Integer>` pour stocker le score. 
- **Observabilité** : L'Activity "observe" ce flux de données. Dès que la valeur change dans le ViewModel, l'interface se met à jour instantanément.
- **Lifecycle-Aware** : Le LiveData sait si l'Activity est visible ou non. Si l'app est en arrière-plan, il ne gaspille pas de ressources à mettre à jour l'UI, évitant ainsi les crashs.

### 3. Architecture MVVM (Séparation des tâches)
- **Model** : Les données (le compteur).
- **View** (`MainActivity`) : Affiche les boutons et le texte. Elle ne contient aucune logique de calcul.
- **ViewModel** : Fait le pont entre les deux. Il contient la logique métier (`increment`, `decrement`).

## 🛠️ Configuration Technique
- **Language** : Java
- **Minimum SDK** : API 24 (Android 7.0)
- **Target SDK** : API 36 (Android 15/16)
- **Dépendances** :
  - `androidx.lifecycle:lifecycle-viewmodel`
  - `androidx.lifecycle:lifecycle-livedata`

## 📖 Comment utiliser ce projet ?
1. Clonez ou copiez les fichiers dans un projet Android Studio.
2. Synchronisez Gradle.
3. Lancez l'application sur un émulateur ou un téléphone.
4. **Testez la rotation** : Incrémentez jusqu'à 10, tournez l'écran... le 10 est toujours là !

---
*Réalisé dans le cadre du TP Programmation Mobile - Android avec Java.*
