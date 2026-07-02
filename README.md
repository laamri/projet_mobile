<div align="center">

# 📱 Android Mobile Development Labs

### Collection de projets pratiques consacrés au développement Android moderne

![Android](https://img.shields.io/badge/Android-Mobile%20Development-3DDC84?logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-Primary%20Language-ED8B00?logo=openjdk&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?logo=kotlin&logoColor=white)
![C++](https://img.shields.io/badge/C++-JNI%20%26%20NDK-00599C?logo=cplusplus&logoColor=white)
![PHP](https://img.shields.io/badge/PHP-REST%20APIs-777BB4?logo=php&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)

</div>

---

## Présentation

Ce dépôt rassemble **15 laboratoires et travaux pratiques Android** réalisés dans le cadre du module de programmation mobile. Il retrace une progression complète, depuis les composants fondamentaux d'Android jusqu'aux architectures modernes, à la communication client-serveur, aux capteurs embarqués et à l'intégration de code natif.

Chaque projet est isolé dans son propre dossier et possède son code source, sa configuration Gradle, sa documentation et, pour la majorité des laboratoires, une démonstration vidéo.

## Projets disponibles

| Projet | Sujet principal | Technologies et concepts |
|---|---|---|
| [LAB 7 — Stars Gallery](./LAB_7__Galerie_de_Stars) | Galerie interactive avec recherche et notation | Java, RecyclerView, Glide, RatingBar, SearchView, MVC |
| [LAB 8 — Programmation asynchrone](./LAB8_Threads_AsyncTask_et_Handler) | Exécution de tâches lourdes sans bloquer l'interface | Threads, Handler, Looper, AsyncTask |
| [LAB 9 — Web Service PHP](./lab9_Consommer_un_Web_Service_PHP_8_depuis_une_apk) | Gestion d'étudiants avec backend distant | Android Java, Volley, Gson, PHP 8, REST, MySQL |
| [LAB 10 — Navigation Drawer](./LAB10_Demo_Navigation_Drawer_et_Fragments) | Navigation entre plusieurs écrans | Kotlin, Fragments, Navigation Drawer, View Binding |
| [TP 11 — Géolocalisation](./TP11_Localisation) | Collecte et transmission de coordonnées GPS | LocationManager, permissions, Volley, PHP, MySQL |
| [LAB 14 — Secure Storage](./LAB14_Sauvegarde) | Persistance et protection des données locales | SharedPreferences, Keystore, JSON |
| [LAB 15 — SQLite](./LAB15_SQLite) | Application CRUD de gestion d'étudiants | Java, SQLiteOpenHelper, SQLite |
| [LAB 16 — Android Services](./LAB16_Services) | Chronomètre fonctionnant en arrière-plan | Foreground Service, Bound Service, notifications |
| [LAB 17 — BroadcastReceiver](./LAB17_BroadcastReceiver) | Réaction aux événements système et applicatifs | Receivers dynamiques, statiques et personnalisés |
| [LAB 18 — ViewModel & LiveData](./LAB18_ViewModel_LiveData) | Gestion réactive de l'état de l'interface | Jetpack, ViewModel, LiveData, MVVM |
| [LAB 19 — Room & MVVM](./LAB19_Room_MVVM) | Application de notes avec architecture moderne | Room, Repository, ViewModel, LiveData, RecyclerView |
| [TP 20 — Number Book](./TP20_Number_Book) | Synchronisation des contacts avec un serveur | ContentResolver, Retrofit, Gson, PHP, MySQL |
| [LAB 21 — Capteurs embarqués](./LAB21_Capteurs) | Exploration des capteurs du smartphone | SensorManager, Canvas, boussole, podomètre |
| [LAB 22 — JNI & NDK](./LAB22_JNI) | Communication entre Java et du code natif | JNI, NDK, C++, CMake |
| [LAB 23 — Sécurité native](./LAB23_AntiDebug) | Étude pédagogique de protections natives | JNI, C++, NDK, contrôles système |

## Compétences développées

- Conception d'interfaces Android modernes avec XML, Material Components et View Binding.
- Gestion du cycle de vie, de l'état et des changements de configuration.
- Mise en œuvre des architectures MVC, MVVM et Repository.
- Persistance locale avec SharedPreferences, fichiers, SQLite et Room.
- Communication réseau avec Volley et Retrofit.
- Développement de services REST en PHP avec stockage MySQL.
- Utilisation des services, notifications, BroadcastReceivers et tâches asynchrones.
- Exploitation du GPS, des contacts et des capteurs matériels du smartphone.
- Intégration de code natif C++ avec JNI, NDK et CMake.

## Stack technique

| Catégorie | Technologies |
|---|---|
| Mobile | Android SDK, Android Studio, Java, Kotlin |
| Interface | XML, Material Components, RecyclerView, Fragments, Navigation Drawer |
| Architecture | MVC, MVVM, Repository, ViewModel, LiveData |
| Données locales | SharedPreferences, Android Keystore, JSON, SQLite, Room |
| Réseau | Volley, Retrofit, Gson, HTTP, API REST |
| Backend | PHP 7/8, PDO, MySQL, XAMPP |
| Code natif | C++, JNI, NDK, CMake |

## Organisation du dépôt

```text
projet_mobile/
├── README.md
├── LAB_7__Galerie_de_Stars/
├── LAB8_Threads_AsyncTask_et_Handler/
├── lab9_Consommer_un_Web_Service_PHP_8_depuis_une_apk/
├── LAB10_Demo_Navigation_Drawer_et_Fragments/
├── TP11_Localisation/
├── LAB14_Sauvegarde/
├── LAB15_SQLite/
├── LAB16_Services/
├── LAB17_BroadcastReceiver/
├── LAB18_ViewModel_LiveData/
├── LAB19_Room_MVVM/
├── TP20_Number_Book/
├── LAB21_Capteurs/
├── LAB22_JNI/
└── LAB23_AntiDebug/
```

## Exécution d'un projet

```bash
git clone https://github.com/laamri/projet_mobile.git
```

1. Ouvrir dans Android Studio le dossier du laboratoire souhaité.
2. Lancer la synchronisation Gradle.
3. Consulter le `README.md` du projet pour sa configuration spécifique.
4. Exécuter l'application sur un émulateur ou un appareil Android.

> Les projets utilisant PHP et MySQL nécessitent un environnement local tel que XAMPP ainsi que la configuration de l'adresse du serveur dans l'application.

## Objectif du dépôt

Ce monorepo constitue à la fois un support d'apprentissage et un portfolio technique. Il met en évidence une progression à travers des problématiques concrètes : performance de l'interface, persistance, communication réseau, architectures applicatives, accès au matériel et intégration native.

---

<div align="center">

**Réalisé par [Sayf Eddine Laamri](https://github.com/laamri)**

</div>
