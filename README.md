# LAB 23 : JNI & Protection Anti-Debug Native (Android)

Ce projet est un laboratoire pratique sur l'utilisation du **NDK (Native Development Kit)** et de **JNI** pour implémenter des mécanismes de protection contre l'analyse dynamique (anti-débogage) sous Android.

## 🎯 Objectifs Pédagogiques
- Intégrer un contrôle de sécurité dans une bibliothèque native (`.so`).
- Comprendre les primitives système Linux utilisées pour le débogage (`ptrace`, `/proc/self/status`).
- Apprendre à communiquer un état de sécurité du code C++ vers l'interface Java.
- Adapter le comportement de l'application selon l'intégrité de l'environnement.

# Demo video 



https://github.com/user-attachments/assets/617593ad-d3f8-4eb7-90e1-6223dcac83b1




## 📱 Ce que l'application fait
L'application intègre une méthode native `isDebugDetected()` qui exécute plusieurs contrôles côté C++ :
- Un contrôle de type **trace/debug** attaché.
- Une **inspection mémoire** pour rechercher des signatures caractéristiques dans les bibliothèques chargées.

Si une situation suspecte est détectée, l'application réagit de manière pédagogique :
- Affichage d'un **statut visuel d'alerte** (rouge).
- **Blocage logique** des fonctionnalités natives sensibles (ex: calculs).

---

## 🛡️ Mécanismes de Protection Implémentés
L'application exécute trois types de contrôles au niveau natif :

1.  **Vérification du `TracerPid`** : Inspection du fichier `/proc/self/status`. Si le PID est différent de 0, un debugger est présent.
2.  **Test `ptrace`** : Utilisation de `PTRACE_TRACEME`. Sous Linux, un processus ne peut être tracé que par un seul parent.
3.  **Inspection de `/proc/self/maps`** : Analyse de la mémoire pour détecter des signatures d'outils comme **Frida**, **Xposed**, **Magisk** ou **GDB**.

### 🔍 Aperçu du Code de Protection (C++)

#### A. Détection du TracerPid
```cpp
static bool isTracerAttached() {
    FILE* status = fopen("/proc/self/status", "r");
    if (!status) return false;
    char line[256];
    bool detected = false;
    while (fgets(line, sizeof(line), status)) {
        if (strncmp(line, "TracerPid:", 10) == 0) {
            int pid = atoi(&line[10]);
            if (pid != 0) {
                detected = true;
            }
            break;
        }
    }
    fclose(status);
    return detected;
}
```

#### B. Détection d'outils d'injection (Maps)
```cpp
static bool isHackToolPresent() {
    FILE* maps = fopen("/proc/self/maps", "r");
    if (!maps) return false;
    char line[512];
    bool detected = false;
    while (fgets(line, sizeof(line), maps)) {
        if (strstr(line, "frida") || strstr(line, "xposed") || strstr(line, "magisk")) {
            detected = true;
            break;
        }
    }
    fclose(maps);
    return detected;
}
```

#### C. Test ptrace (Exclusivité)
```cpp
static bool isPtraceWorking() {
    errno = 0;
    if (ptrace(PTRACE_TRACEME, 0, 0, 0) == -1) {
        if (errno == EACCES || errno == EPERM) {
            return false; // Bloqué par SELinux
        }
        return true; // Déjà tracé
    }
    return false;
}
```

---

## 🚀 Pourquoi le natif est utilisé ici ? (Étape 12)
Le code natif n'est pas "magique", mais il présente des intérêts stratégiques majeurs :
- **Hors du Bytecode Java** : Le comportement critique n'est pas uniquement visible dans la couche Java, rendant l'analyse par décompilation plus difficile.
- **Intégration directe** : Le C/C++ permet d'exécuter des contrôles bas niveau (appels système, fichiers `/proc`) plus naturellement.
- **Cloisonnement défensif** : On peut centraliser les contrôles sensibles dans une bibliothèque dédiée, séparée du reste de l'application.

---

## ✅ Bonnes Pratiques à retenir (Étape 13)
- **Ne pas dépendre d'un seul contrôle** : Un seul indicateur est fragile. La robustesse vient de la combinaison de plusieurs signaux.
- **Réactions graduelles** : Préférer l'affichage d'un statut et la désactivation logique de fonctions plutôt qu'une fermeture brutale (crash), surtout en phase d'apprentissage.
- **API JNI Minimale** : Limiter les transitions Java/Native pour rester organisé et performant.
- **Journalisation (Logs)** : Les logs natifs (`Logcat`) sont précieux pour comprendre le comportement en temps réel.
- **Séparation Code Métier / Code Défensif** : Créer des fonctions dédiées pour garder un code maintenable.

---

## ⚠️ Limites Pédagogiques (Étape 14)
- **Détection Imparfaite** : Aucun contrôle simple ne garantit une détection exhaustive face à un attaquant expert.
- **Faux Positifs** : Certains environnements (émulateurs, agents Android Studio) peuvent déclencher des signaux inattendus.
- **Contre-mesures** : Un attaquant expérimenté peut tenter de neutraliser ou modifier ces contrôles natifs.
- **Coût de Maintenance** : La multiplication des protections complexifie la base de code.

---

## 🚀 Variantes d’amélioration (Étape 15)
- **Variante A** : Séparer les méthodes JNI pour chaque contrôle (`isBeingTracedNative()`, etc.).
- **Variante B** : Retourner un **code d'état** entier (0 = OK, 1 = Trace, 2 = Maps suspect, etc.) au lieu d'un booléen.
- **Variante C** : Ajouter un **écran de diagnostic** détaillant précisément quel signal a déclenché l'alerte.
- **Variante D** : Créer un `NativeSecurityManager` en Java pour mieux structurer l'architecture défensive.

---

## 🧪 Scénarios de Test & Résumé
### Flux du TP
`MainActivity (Java)` ➔ Appelle `isDebugDetected()` ➔ `libnative-lib.so` (C++) exécute les contrôles ➔ Renvoi d'un booléen ➔ Java adapte l'UI.

### Validation
1.  **Run Standard** (Triangle vert) : L'application affiche **"État sécurité : OK"**.
2.  **Debug Mode** (🪲) : L'application détecte le debugger et affiche **"Environnement Suspect"**.

---
*Projet réalisé dans le cadre du cours : Programmation Mobile : Android avec Java.*
