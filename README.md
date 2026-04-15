# 📚 Student Management App — PHP Web Service + Android

> A full-stack mobile application that connects an **Android app** to a **PHP 8 REST API** using **Volley** and **Gson**, backed by a **MySQL** database.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────┐
│              Android App (Java)                 │
│         Volley HTTP  ──  Gson Parser            │
└────────────────────┬────────────────────────────┘
                     │ HTTP (JSON)
┌────────────────────▼────────────────────────────┐
│           PHP 8 Web Service (XAMPP)             │
│   /ws/createEtudiant.php                        │
│   /ws/loadEtudiant.php                          │
└────────────────────┬────────────────────────────┘
                     │ PDO
┌────────────────────▼────────────────────────────┐
│           MySQL Database (school1)              │
│              Table: Etudiant                    │
└─────────────────────────────────────────────────┘
```

---

## ✨ Features

- ➕ Add a student via an Android form
- 📋 Fetch and display all students as JSON
- ✅ Input validation on the Android side
- 🎨 Material Design UI with cards and Snackbar feedback
- 🔒 Android network security config for local HTTP
- 🧱 Clean PHP architecture: `Connexion` / `Etudiant` / `IDao` / `EtudiantService`

---

</div>

---

## 🎬 Demo Video



> 🎬 **Watch the full walkthrough below**



---

## 🗂️ Project Structure

```
📁 PHP Web Service (C:\xampp\htdocs\projet\)
├── classes/
│   └── Etudiant.php          # Student model
├── connexion/
│   └── Connexion.php         # PDO connection handler
├── dao/
│   └── IDao.php              # DAO interface (CRUD)
├── service/
│   └── EtudiantService.php   # Business logic
└── ws/
    ├── createEtudiant.php    # POST endpoint
    └── loadEtudiant.php      # GET endpoint

📁 Android App (app/src/main/)
├── java/com/example/projetws/
│   ├── beans/
│   │   └── Etudiant.java     # Student model
│   ├── AddEtudiant.java      # Add student activity
│   └── MainActivity.java
└── res/
    ├── layout/
    │   └── activity_add_etudiant.xml
    ├── values/
    │   ├── strings.xml
    │   └── themes.xml
    └── xml/
        └── network_security_config.xml
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Mobile | Android (Java), API 26+ |
| HTTP Client | Volley 1.2.1 |
| JSON Parser | Gson 2.10.1 |
| UI | Material Components 1.11.0 |
| Backend | PHP 8 |
| Database | MySQL via PDO |
| Local Server | XAMPP (Apache + MySQL) |

---

## 🚀 Getting Started

### Prerequisites

- [XAMPP](https://www.apachefriends.org/) with Apache + MySQL
- [Android Studio](https://developer.android.com/studio) (Flamingo or later)
- Android Emulator or physical device (API 26+)

---

### 1️⃣ Database Setup

Open **phpMyAdmin** at `http://localhost/phpmyadmin` and run:

```sql
CREATE DATABASE school1;

USE school1;

CREATE TABLE Etudiant (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(50),
  prenom VARCHAR(50),
  ville VARCHAR(50),
  sexe VARCHAR(10)
);

INSERT INTO Etudiant (nom, prenom, ville, sexe) VALUES
  ('sayf', 'Mohamed', 'Rabat', 'homme'),
  ('Safi', 'Amine', 'Marrakech', 'homme');
```

---

### 2️⃣ PHP Web Service Setup

Copy the `projet/` folder to:
```
C:\xampp\htdocs\projet\
```

Test endpoints in your browser or Postman:

| Method | URL | Description |
|--------|-----|-------------|
| `GET` | `http://localhost/projet/ws/loadEtudiant.php` | Get all students |
| `POST` | `http://localhost/projet/ws/createEtudiant.php` | Add a student |

**POST body (x-www-form-urlencoded):**
```
nom=Dupont
prenom=Sara
ville=Casablanca
sexe=femme
```

**Expected JSON response:**
```json
{
  "message": "Étudiant ajouté avec succès",
  "etudiants": [
    { "id": "1", "nom": "sayf", "prenom": "Mohamed", "ville": "Rabat", "sexe": "homme" },
    { "id": "2", "nom": "Dupont", "prenom": "Sara", "ville": "Casablanca", "sexe": "femme" }
  ]
}
```

---

---

## 🔌 API Reference

### `GET /ws/loadEtudiant.php`

Returns all students.

```json
{
  "count": 2,
  "etudiants": [...]
}
```

---

### `POST /ws/createEtudiant.php`

Creates a new student.

**Request params:**

| Parameter | Type | Required |
|-----------|------|----------|
| `nom` | string | ✅ |
| `prenom` | string | ✅ |
| `ville` | string | ✅ |
| `sexe` | string | ✅ |

**Response codes:**

| Code | Meaning |
|------|---------|
| `201` | Student created successfully |
| `400` | Missing required fields |
| `500` | Server/database error |

---

## ⚙️ Android Dependencies

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.android.volley:volley:1.2.1'
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'com.google.android.material:material:1.11.0'
}
```

---

## 🔐 Network Security

For Android 9+ (Pie), HTTP traffic is blocked by default. This project includes a `network_security_config.xml` that allows cleartext traffic to local development IPs only.

```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
    </domain-config>
</network-security-config>
```

> ⚠️ **Production note**: Always use HTTPS in production. This config is for local development only.

---

## 🧩 Future Improvements (Challenge)

- [ ] 📋 `ListEtudiant` activity with `RecyclerView`
- [ ] ✏️ Edit student with pre-filled form
- [ ] 🗑️ Delete with confirmation dialog
- [ ] 🔍 Search / filter by city or name
- [ ] 🌐 Deploy PHP backend to a live server

---

---
