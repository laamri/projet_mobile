# Lab 8 : Programmation Asynchrone sur Android (Java)

Ce projet est une application Android démontrant l'utilisation des **Threads**, des **Handlers** et des **AsyncTasks** pour maintenir une interface utilisateur (UI) fluide et réactive pendant l'exécution de tâches lourdes.

## 🎯 Objectifs du Lab
- Comprendre la distinction entre le **UI Thread** (Thread principal) et les **Worker Threads** (Threads de fond).
- Apprendre à exécuter des tâches en arrière-plan sans bloquer l'interface.
- Maîtriser le retour sur le UI Thread via `Handler` et `Looper.getMainLooper()`.
- Utiliser `AsyncTask` (approche pédagogique) pour gérer le cycle complet d'une tâche asynchrone (Préparation, Exécution, Progression, Résultat).

## 🚀 Fonctionnalités
L'application propose trois actions principales pour tester la réactivité de l'UI :

1.  **Charger image (Thread + Handler)** : Simule le chargement d'une image depuis le réseau ou le disque dans un thread séparé.
2.  **Calcul lourd (AsyncTask)** : Exécute une boucle de calcul intensive avec une mise à jour en temps réel d'une barre de progression.
3.  **Afficher Toast** : Un test de réactivité. Ce bouton doit fonctionner instantanément, même pendant qu'un calcul lourd est en cours.

## ✨ "Ma Touche" (Améliorations apportées)
Pour aller plus loin que les consignes de base, j'ai ajouté plusieurs améliorations professionnelles :
- **Design Material 3** : Utilisation de composants modernes (`MaterialCardView`, `MaterialButton`, `ShapeableImageView`) pour une interface élégante.
- **Sécurité Mémoire (Best Practices)** : L' `AsyncTask` est implémentée en tant que classe `static` avec une `WeakReference` vers l'activité pour éviter les fuites de mémoire (Memory Leaks).
- **Animations Fluides** : Ajout d'une animation de fondu (`alpha`) lors de l'apparition de l'image chargée.
- **Gestion d'État** : Les boutons sont désactivés intelligemment pendant les traitements pour éviter les lancements multiples accidentels.
- **Design Adaptatif** : Support complet du mode **Edge-to-Edge** et utilisation d'un `ScrollView` pour la compatibilité avec tous les écrans.

## 🛠️ Concepts Techniques Clés
### 1. Pourquoi des Threads ?
Par défaut, tout le code s'exécute sur le **Main Thread**. Si on effectue un calcul de 5 secondes dessus, l'écran "gèle". En utilisant un `new Thread()`, on déporte le travail.

### 2. Le rôle du Handler
Un thread secondaire ne peut PAS modifier l'interface utilisateur. Le `Handler(Looper.getMainLooper())` sert de pont pour envoyer les commandes de mise à jour (comme `setImageBitmap`) au thread principal.

### 3. Cycle de l'AsyncTask
- `onPreExecute()` : Préparation de l'UI (ex: afficher la barre).
- `doInBackground()` : Travail lourd (Thread séparé).
- `onProgressUpdate()` : Mise à jour de la barre pendant le travail.
- `onPostExecute()` : Résultat final et nettoyage de l'UI.

## 📱 Aperçu des tests
- Lancez le **Calcul lourd**.
- Pendant que la barre progresse, cliquez sur **Afficher Toast**.
- **Résultat attendu** : Le Toast s'affiche sans aucun délai, prouvant que le thread principal est libre.

---
*Réalisé dans le cadre du cours de Programmation Mobile Android.*
