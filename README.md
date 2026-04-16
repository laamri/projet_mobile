

Uploading lab-15 (1).mp4…

# 🎓 Gestion des Étudiants — Application Android SQLite


Application Android minimale de gestion des étudiants basée sur une base de données **SQLite embarquée**, développée avec Android Studio en Java.

---

## 📋 Table des matières

- [Aperçu](#-aperçu)
- [Démonstration vidéo](#-démonstration-vidéo)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture du projet](#-architecture-du-projet)
- [Technologies utilisées](#-technologies-utilisées)
- [Installation](#-installation)
- [Structure des fichiers](#-structure-des-fichiers)
- [Utilisation](#-utilisation)
- [Résolution des erreurs courantes](#-résolution-des-erreurs-courantes)

---

## 📱 Aperçu

Cette application permet de gérer une liste d'étudiants stockée localement sur l'appareil Android. Elle couvre trois couches distinctes :

| Couche | Classe | Rôle |
|--------|--------|------|
| **Métier** | `Etudiant.java` | POJO avec id, nom, prénom |
| **BDD** | `MySQLiteHelper.java` | Création et migration de la base SQLite |
| **Service** | `EtudiantService.java` | Opérations CRUD centralisées |
| **Présentation** | `MainActivity.java` + XML | Interface utilisateur |

---

## 🎬 Démonstration vidéo

> **Regardez la démonstration complète de l'application :**




### Ce que montre la vidéo

| # | Scénario | Durée |
|---|----------|-------|
| 1 | Lancement de l'app et affichage de l'UI | 0:00 |
| 2 | Ajout d'un étudiant (Nom + Prénom → Toast) | 0:15 |
| 3 | Recherche par ID → affichage du résultat | 0:35 |
| 4 | Suppression par ID → vérification via recherche | 0:55 |
| 5 | Observation des logs dans Logcat | 1:15 |

---

## ✨ Fonctionnalités

- ➕ **Ajouter** un étudiant (nom + prénom) avec confirmation via Toast
- 🔍 **Chercher** un étudiant par son ID
- 🗑️ **Supprimer** un étudiant par son ID
- 📋 **Lister** tous les étudiants (visible en Logcat)
- ✏️ **Modifier** un étudiant existant (via le service)
- 💾 Persistance locale avec **SQLite embarqué** (aucune connexion réseau requise)

---

## 🏗️ Architecture du projet

```
app/src/main/
├── java/com/example/gestionetudiants/
│   ├── model/
│   │   └── Etudiant.java          # Modèle métier (POJO)
│   ├── db/
│   │   ├── MySQLiteHelper.java    # Initialisation de la base SQLite
│   │   └── EtudiantService.java   # Service CRUD
│   └── MainActivity.java          # Interface utilisateur
│
└── res/
    └── layout/
        └── activity_main.xml      # Layout de l'écran principal
```

### Diagramme des dépendances

```
MainActivity
    └──> EtudiantService
              └──> MySQLiteHelper
                        └──> SQLiteOpenHelper (Android SDK)
              └──> Etudiant (modèle)
```

---

## 🛠️ Technologies utilisées

- **Android Studio** Hedgehog / Iguana (ou supérieur)
- **Java** (API Android)
- **SQLite** — base embarquée via `SQLiteOpenHelper`
- **Min SDK** : API 21 (Android 5.0 Lollipop)
- **Target SDK** : API 34 (Android 14)

---

## ⚙️ Installation

### Prérequis

- Android Studio installé ([télécharger ici](https://developer.android.com/studio))
- JDK 11 ou supérieur
- Un émulateur Android ou un appareil physique (Android 5.0+)

### Étapes

```bash
# 1. Cloner le dépôt
git clone https://github.com/laamri/LAB15_SQLite_et_Android.git

# 2. Ouvrir le projet dans Android Studio
#    File > Open > sélectionner le dossier du projet

# 3. Synchroniser Gradle
#    Cliquer sur "Sync Now" dans la bannière qui apparaît

# 4. Lancer l'application
#    Run > Run 'app'  ou  Maj+F10
```

---

## 📂 Structure des fichiers

### `Etudiant.java` — Modèle métier

```java
public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    // Constructeurs, getters, setters, toString()
}
```

### `MySQLiteHelper.java` — Base de données

```
Base : etudiants.db
Table : etudiants
Colonnes :
  - id      INTEGER PRIMARY KEY AUTOINCREMENT
  - nom     TEXT NOT NULL
  - prenom  TEXT NOT NULL
```

### `EtudiantService.java` — Opérations CRUD

| Méthode | Description | Retour |
|---------|-------------|--------|
| `ajouter(Etudiant e)` | Insère un nouvel étudiant | `long` (id généré) |
| `findById(int id)` | Recherche par identifiant | `Etudiant` ou `null` |
| `findAll()` | Retourne tous les étudiants | `List<Etudiant>` |
| `modifier(Etudiant e)` | Met à jour nom et prénom | `int` (lignes affectées) |
| `supprimer(int id)` | Supprime par identifiant | `int` (lignes affectées) |

---

## 🚀 Utilisation

### Ajouter un étudiant
1. Renseigner le champ **Nom**
2. Renseigner le champ **Prénom**
3. Appuyer sur **Valider**
4. Un Toast confirme l'ajout avec l'ID généré

### Chercher un étudiant
1. Saisir un **ID** dans le champ prévu
2. Appuyer sur **Chercher**
3. Le résultat s'affiche sous la forme `Nom Prénom`

### Supprimer un étudiant
1. Saisir l'**ID** de l'étudiant à supprimer
2. Appuyer sur **Supprimer**
3. Confirmation via Toast ; vérifiable en cherchant à nouveau cet ID

---

## 🐛 Résolution des erreurs courantes

### ❌ `Cannot resolve symbol 'model'` ou `'db'`

**Cause :** Les classes sont dans le même package mais le code utilise des sous-packages.

**Solution :**

```java
// ✅ Corriger les imports dans MainActivity.java et EtudiantService.java
// Si toutes les classes sont au même niveau :
import com.example.gestionetudiants.Etudiant;
import com.example.gestionetudiants.EtudiantService;

// Si les classes sont dans des sous-packages :
import com.example.gestionetudiants.model.Etudiant;
import com.example.gestionetudiants.db.EtudiantService;
```

---



### ❌ `NullPointerException` lors d'une recherche

**Cause :** L'ID saisi n'existe pas en base, `findById()` retourne `null`.

**Solution :** Vérifier le retour avant d'utiliser l'objet :
```java
Etudiant e = service.findById(id);
if (e == null) {
    tvResultat.setText("Aucun étudiant trouvé pour id=" + id);
} else {
    tvResultat.setText(e.getNom() + " " + e.getPrenom());
}
```

---

### ❌ IDs inattendus (autoincrement)

**Cause :** SQLite continue d'incrémenter les IDs entre les exécutions.

**Solution :** Désinstaller l'app pour repartir d'une base vide :
```bash
adb uninstall com.example.gestionetudiants
```

---

## 📝 Auteur

> Projet réalisé dans le cadre d'un TP Android — Gestion de base de données SQLite embarquée.

---

