# LAB 22 : Développement Android avec JNI (Java Native Interface) 🚀

Ce projet est une démonstration complète de l'intégration de code natif **C++** dans une application **Android** via **JNI** (Java Native Interface) et le **NDK**.

## 📌 Aperçu du Projet
L'application `JNIDemo` illustre comment Java communique avec le code C++ natif via une bibliothèque partagée (`.so`) compilée avec **CMake**. Ce laboratoire couvre tout le cycle de développement JNI, de la configuration du build à la gestion de la mémoire et des ressources.

---
# Demo video 



https://github.com/user-attachments/assets/c7d0fcee-ae63-452b-a2bf-36bb37840fac




# 🎯 Objectifs Pédagogiques
À la fin de ce laboratoire, vous aurez appris à :
*   **Créer un projet Android avec support C++** et configurer le NDK.
*   Comprendre le rôle de : **JNI**, **NDK**, et **CMake**.
*   Déclarer et appeler des méthodes natives depuis Java.
*   Échanger des données complexes : `String`, `int`, `int[]`.
*   Gérer les erreurs comme `UnsatisfiedLinkError` et les crashs natifs.
*   Lire les logs natifs dans **Logcat**.
*   Appliquer les meilleures pratiques JNI pour l'optimisation et la sécurité.

---

# 🧠 Fonctionnalités de l'Application
L'application réalise quatre démonstrations interactives et inclut une zone de tests automatisés :

| Fonctionnalité | Description |
| :--- | :--- |
| `helloFromJNI()` | Appel simple renvoyant un message "Hello" depuis le monde natif. |
| `factorial(int n)` | Calcul d'un factoriel en C++ avec gestion d'overflow et d'erreurs. |
| `reverseString(String s)` | Envoi d'une chaîne Java, inversion via `std::reverse` et retour. |
| `sumArray(int[] values)` | Passage d'un tableau d'entiers au C++ pour calcul de somme. |

---

# 🏗️ Architecture Technique
```text
Java (MainActivity)
        ↓
Appelle une méthode native
        ↓
Android charge libnative-lib.so
        ↓
JNI transmet l'appel au code C++
        ↓
Le code C++ exécute le traitement
        ↓
Le résultat est converti et renvoyé vers Java
        ↓
L'interface Android affiche le résultat
```

---

# ⚙️ Configuration du Build

### 1. Gradle (`build.gradle.kts`)
Indique à Gradle où se trouve le script CMake :
```kotlin
externalNativeBuild {
    cmake {
        path = file("src/main/cpp/CMakeLists.txt")
    }
}
```

### 2. CMake (`CMakeLists.txt`)
Définit la création de la bibliothèque native et lie les outils de logs :
```cmake
add_library(native-lib SHARED native-lib.cpp)
find_library(log-lib log)
target_link_libraries(native-lib ${log-lib})
```

---

# 🔍 Concepts JNI Indispensables

### 🏷️ Convention de Nommage
La signature des fonctions en C++ est stricte pour permettre le lien automatique :
`Java_<package>_<class>_<method>`
*Exemple :* `Java_com_example_jnidemo_MainActivity_factorial`

### 🔄 Gestion de la Mémoire
⚠️ **Règle d'or** : Toute ressource "capturée" (Get) doit être libérée (Release) explicitement en C++ pour éviter les fuites mémoire.
*   **Strings** : `env->GetStringUTFChars` → `env->ReleaseStringUTFChars`.
*   **Tableaux** : `env->GetIntArrayElements` → `env->ReleaseIntArrayElements`.

---

## 🧪 Tests Guidés & Validation (Étape 10)
L'application intègre un module de tests automatiques validant les scénarios limites :
- ✅ **T1 (Normal)** : `factorial(10)` → `3628800`.
- ✅ **T2 (Négatif)** : `factorial(-5)` → `-1` (Erreur gérée).
- ✅ **T3 (Overflow)** : `factorial(20)` → `-2` (Détection de dépassement).
- ✅ **T4 (Chaîne vide)** : Inversion de `""` → `""`.
- ✅ **T5 (Tableau vide)** : Somme de `[]` → `0`.

---

## 🐞 Débogage & Logs (Logcat)
Pour suivre l'exécution côté natif, filtrez par le tag **`JNI_DEMO`** dans Logcat.
Les erreurs courantes comme `UnsatisfiedLinkError` indiquent généralement une faute dans le nom de la bibliothèque ou la signature de la fonction.

---

## 🚀 Pourquoi utiliser JNI ?
1.  **Performance** : Traitement d'images, calculs intensifs, moteurs de jeux.
2.  **Réutilisation** : Utiliser des bibliothèques C/C++ existantes (OpenCV, FFmpeg).
3.  **Accès Bas Niveau** : Interaction directe avec le matériel ou les APIs système.
4.  **Sécurité** : Le code natif est beaucoup plus difficile à décompiler.

---

## 📂 Structure du Projet
```text
app/
 ├── src/
 │   └── main/
 │       ├── java/.../MainActivity.java  # Déclarations native & Logique UI
 │       ├── cpp/
 │       │    ├── native-lib.cpp         # Code source C++ (Natif)
 │       │    └── CMakeLists.txt         # Configuration CMake
 │       └── res/layout/
 │            └── activity_main.xml      # UI Interactive (CardViews)
```

---
*Réalisé avec passion pour explorer les performances natives sous Android. Lab 22 validé.* 🛠️⚙️
