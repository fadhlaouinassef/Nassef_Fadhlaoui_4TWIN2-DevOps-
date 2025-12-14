# Guide de Configuration - Student Management Application

## 🎯 Problème rencontré
L'application ne peut pas démarrer car MySQL n'est pas disponible localement.

**Erreur:** `java.net.UnknownHostException: mysql-application.devops.svc.cluster.local`

## ✅ Solutions disponibles

### **Option 1 : Utiliser Docker (Recommandé)**

#### Prérequis
- Docker Desktop installé et en cours d'exécution

#### Étapes
1. Ouvrir PowerShell dans le dossier du projet
2. Lancer MySQL avec Docker Compose :
   ```powershell
   docker-compose -f docker-compose-local.yml up -d
   ```
3. Attendre que MySQL démarre (~30 secondes)
4. Relancer l'application Spring Boot depuis IntelliJ

Pour arrêter MySQL :
```powershell
docker-compose -f docker-compose-local.yml down
```

---

### **Option 2 : Installer MySQL localement**

#### Télécharger et installer MySQL
1. Télécharger MySQL Community Server : https://dev.mysql.com/downloads/mysql/
2. Installer MySQL avec les paramètres par défaut
3. Configurer le mot de passe root (laisser vide ou noter le mot de passe)
4. Démarrer le service MySQL

#### Configuration si mot de passe défini
Si vous avez défini un mot de passe pour root, modifiez `application.properties` :
```properties
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

#### Créer la base de données (si nécessaire)
```sql
CREATE DATABASE IF NOT EXISTS springdb;
```

---

### **Option 3 : Utiliser XAMPP**

1. Télécharger XAMPP : https://www.apachefriends.org/
2. Installer XAMPP
3. Lancer le panel XAMPP
4. Démarrer MySQL depuis le panel
5. Relancer l'application Spring Boot

---

## 📁 Fichiers de configuration créés

### 1. `application.properties` (par défaut)
Configuration pour **développement local** avec localhost:3306

### 2. `application-local.properties`
Configuration explicite pour le développement local (identique au défaut)

### 3. `application-k8s.properties`
Configuration pour **Kubernetes** avec le service cluster

Pour l'utiliser :
```bash
java -jar app.jar --spring.profiles.active=k8s
```

### 4. `docker-compose-local.yml`
Fichier Docker Compose pour lancer MySQL localement

---

## 🚀 Démarrer l'application

### Depuis IntelliJ IDEA
1. S'assurer que MySQL est en cours d'exécution (Docker, service, ou XAMPP)
2. Clic droit sur `StudentManagementApplication`
3. Sélectionner "Run 'StudentManagementApplication'"

### Depuis la ligne de commande
```powershell
# Compiler
mvn clean package

# Lancer avec le profil par défaut (local)
java -jar target/student-management-0.0.1-SNAPSHOT.jar

# Ou lancer avec Maven
mvn spring-boot:run
```

---

## 🔍 Vérifier que MySQL fonctionne

### Via PowerShell
```powershell
Test-NetConnection -ComputerName localhost -Port 3306
```

### Via Docker
```powershell
docker ps | Select-String mysql
```

### Via MySQL Client
```bash
mysql -u root -h localhost -P 3306
```

---

## 📌 Endpoints de l'application

Une fois l'application démarrée :

- **Base URL**: `http://localhost:8089/student`
- **Swagger UI**: `http://localhost:8089/student/swagger-ui.html`
- **Get All Students**: `GET http://localhost:8089/student/students/getAllStudents`

---

## 🐛 Dépannage

### Erreur : Port 3306 déjà utilisé
```powershell
# Trouver le processus utilisant le port 3306
netstat -ano | findstr :3306

# Arrêter le processus (remplacer PID par l'ID du processus)
Stop-Process -Id PID -Force
```

### MySQL ne démarre pas avec Docker
```powershell
# Vérifier les logs
docker logs student-mysql-local

# Nettoyer et relancer
docker-compose -f docker-compose-local.yml down -v
docker-compose -f docker-compose-local.yml up -d
```

### Erreur de connexion après installation MySQL
Vérifier :
1. Le service MySQL est démarré
2. Le port 3306 est accessible
3. Le mot de passe dans `application.properties` correspond

---

## 📝 Notes importantes

- La configuration par défaut pointe maintenant vers **localhost**
- Pour déployer sur Kubernetes, utiliser le profil `k8s`
- Le mot de passe root MySQL est vide par défaut
- La base de données `studentdb` sera créée automatiquement

