# Jakarta EE – Gestion des produits

Application web développée avec Jakarta EE et Apache Tomcat dans le cadre de travaux pratiques à l'ENIAD.

## Technologies utilisées

- Java 25
- Jakarta Servlet 6.0
- JSP
- JSTL 3.0
- Apache Tomcat 10.1
- Maven
- HTML / CSS

## Fonctionnalités

- Authentification avec gestion de session.
- Protection des pages avec un filtre d'authentification.
- Gestion des produits (CRUD).
- Gestion des cookies.
- Thème clair et sombre.
- Navigation avec Servlets et JSP.

## Architecture du projet

```text
src/
└── main/
    ├── java/
    │   └── ma/app/
    │       ├── model/
    │       ├── web/
    │       ├── filter/
    │       └── listener/
    └── webapp/
        ├── index.jsp
        └── WEB-INF/
            ├── web.xml
            └── views/
                ├── login.jsp
                ├── dashboard.jsp
                └── produits/
                    ├── list.jsp
                    └── form.jsp
```

## Installation et exécution

### Prérequis
- JDK 25
- Maven
- Apache Tomcat 10.1

### Compilation

```bash
mvn clean package
```

### Déploiement

Copier le fichier généré :

```text
target/app-1.0.war
```

dans le dossier `webapps` de Tomcat.

Démarrer Tomcat et accéder à :

```text
http://localhost:8080/app-1.0/
```

## Identifiants de test

| Username | Password |
|---|---|
| admin | password |

## Auteur

Projet académique réalisé à l'ENIAD – École Nationale de l'Intelligence Artificielle et du Digital.
