



# LAB 18 : ViewModel et LiveData (Android Java)

Ce projet est une application de démonstration interactive illustrant l'utilisation des composants d'architecture Android (**Jetpack**) : **ViewModel** et **LiveData**. 

L'objectif est de créer un compteur robuste qui survit aux changements de configuration (comme la rotation de l'écran) et qui met à jour l'interface utilisateur de manière réactive et sécurisée.

## 🎥 Video / Démo

https://github.com/user-attachments/assets/6f576a50-0d0b-4c61-b539-8c330674b5a4


> 💡 *Astuce : vous pouvez héberger une vidéo sur YouTube ou GitHub, ou simplement enregistrer un GIF de l'application en fonctionnement.*

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

**Explication détaillée** :  
Une Activity normale est détruite et recréée à chaque rotation (changement de configuration). Sans ViewModel, la valeur du compteur serait perdue. Le ViewModel reste en mémoire tant que le `ViewModelStoreOwner` (l'Activity) n'est pas définitivement détruit. Cela permet de conserver les données lors des rotations, changements de langue, redimensionnement, etc.

### 2. LiveData (L'Observateur)
Nous utilisons `MutableLiveData<Integer>` pour stocker le score. 
- **Observabilité** : L'Activity "observe" ce flux de données. Dès que la valeur change dans le ViewModel, l'interface se met à jour instantanément.
- **Lifecycle-Aware** : Le LiveData sait si l'Activity est visible ou non. Si l'app est en arrière-plan, il ne gaspille pas de ressources à mettre à jour l'UI, évitant ainsi les crashs.

**Explication détaillée** :  
LiveData suit le cycle de vie des observateurs (comme une Activity en `STARTED` ou `RESUMED`). Si l'Activity est en arrière-plan, LiveData ne déclenche pas les mises à jour inutiles. De plus, il n'y a pas de risque de "memory leak" car LiveData se nettoie automatiquement quand le cycle de vie se termine.

### 3. Architecture MVVM (Séparation des tâches)
- **Model** : Les données (le compteur).
- **View** (`MainActivity`) : Affiche les boutons et le texte. Elle ne contient aucune logique de calcul.
- **ViewModel** : Fait le pont entre les deux. Il contient la logique métier (`increment`, `decrement`).

**Explication détaillée** :  
MVVM (Model-View-ViewModel) sépare clairement la logique d'affichage de la logique métier et des données. Cela rend le code plus testable, maintenable et réutilisable. La View observe le ViewModel via LiveData, et le ViewModel expose des données immuables à la View.

## 📊 Comparaison : Avec vs Sans ViewModel/LiveData

| Situation | Sans ViewModel/LiveData | Avec ViewModel + LiveData |
|-----------|------------------------|---------------------------|
| Rotation d'écran | Le compteur revient à 0 | Le compteur reste intact |
| Mise à jour UI | `findViewById` + manuelle |  Automatique via observation |
| Gestion cycle de vie | Risque de crash et memory leak | Sécurisé, lifecycle-aware |
| Code requis | Plus de boilerplate (savedInstanceState, etc.) |  Plus propre et concis |

## 🛠️ Configuration Technique
- **Language** : Java
- **Minimum SDK** : API 24 (Android 7.0)
- **Target SDK** : API 36 (Android 15/16)
- **Dépendances** (dans `build.gradle` au niveau module) :
  ```gradle
  dependencies {
      implementation 'androidx.lifecycle:lifecycle-viewmodel:2.6.2'
      implementation 'androidx.lifecycle:lifecycle-livedata:2.6.2'
      implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0' // optionnel pour compatibilité
  }
  ```

## 📖 Comment utiliser ce projet ?
1. Clonez ou copiez les fichiers dans un projet Android Studio.
2. Synchronisez Gradle (File → Sync Project with Gradle Files).
3. Lancez l'application sur un émulateur ou un téléphone.
4. **Testez la rotation** : Incrémentez jusqu'à 10, tournez l'écran... le 10 est toujours là !
5. Testez aussi le changement de couleur : descendez en négatif (le texte devient rouge), remontez en positif (vert).

## 📁 Structure du projet (extrait)
```
app/
├── src/main/java/.../
│   ├── MainActivity.java          (View + observe LiveData)
│   ├── CounterViewModel.java      (ViewModel avec MutableLiveData)
│   └── ... 
├── src/main/res/layout/
│   └── activity_main.xml          (TextView + 3 boutons)
└── build.gradle (module)
```

## ❓ Questions fréquentes

**Q : Pourquoi utiliser LiveData plutôt qu'un simple getter/setter ?**  
R : LiveData est lifecycle-aware et observable. Il évite les crashes si l'UI n'est pas active.

**Q : Est-ce que ViewModel remplace onSaveInstanceState ?**  
R : Partiellement. ViewModel gère la rotation, mais pour le kill de l'app par le système, onSaveInstanceState est encore utile. Les deux sont complémentaires.

**Q : Puis-je utiliser ça en Kotlin ?**  
R : Oui, c'est même recommandé. Les concepts sont identiques, la syntaxe diffère légèrement.

---
*Réalisé dans le cadre du TP Programmation Mobile - Android avec Java.*

---
