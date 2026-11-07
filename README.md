# Serveur d'autorisation OAuth 2

Cette application est un serveur d'autorisation basé sur Spring Authorization Server.
Elle permet d'émettre des jetons JWT signés et de gérer les clients OAuth2 ainsi que les scopes associés.

## Objectif

Le projet simule un serveur d'authentification / d'autorisation utilisé par une application cliente pour obtenir un access token, puis l'utiliser pour accéder à des ressources protégées.

## Prérequis

- Java 21
- Un navigateur ou un outil HTTP comme `curl`

## Démarrage

### Avec IntelliJ IDEA

1. Ouvrir le projet.
2. Démarrer la classe principale Spring Boot via IntelliJ.

### Avec la ligne de commande

```bash
./gradlew bootRun
```

Sous le capot, l'application démarre avec :

- Port HTTP : `http://localhost:9000`

Le serveur expose le point d'entrée OAuth2 utilisé pour la génération de jetons.

## Obtenir un JWT

Pour demander un token, envoyez une requête `POST` vers :

```text
http://localhost:9000/oauth2/token
```

### Paramètres requis

Dans la configuration actuelle, le client principal est `messaging-client` et il accepte les deux modes d'authentification suivants :

- `client_secret_basic` via l'en-tête HTTP `Authorization`
- `client_secret_post` via le corps de la requête

Un second client est aussi présent pour la méthode `client_secret_jwt` :

- `client_id` : `messaging-client2`
- `client_secret` : `mon-secret-local-exercise-1234-ok`
- algorithme de signature : `HS256`

Dans ce mode, le `client_secret` n'est pas envoyé directement dans le corps de la requête. Il est intégré dans un JWT que le client signe lui-même avec ce secret, puis envoie dans le paramètre `client_assertion`. Le serveur OAuth vérifie la signature du JWT pour confirmer l'identité du client.

### Exemple avec curl (client_secret_jwt)

```bash
curl -X POST "http://localhost:9000/oauth2/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=allo.droit&client_id=messaging-client2&client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer&client_assertion=<jwt_assertion>"
```

### Exemple avec curl (Basic Auth)

Comme le client OAuth2 est authentifié en Basic Auth, il faut envoyer le `username` et le `password` dans l'en-tête HTTP d'authentification.

```bash
curl -X POST "http://localhost:9000/oauth2/token" \
  -u "messaging-client:secret" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=allo.droit"
```

Le `client_id` et le `client_secret` ne sont pas envoyés dans le corps de la requête lorsqu'on utilise la Basic Auth ; ils sont fournis via `-u`.

### Exemple avec curl (client_secret_post)

```bash
curl -X POST "http://localhost:9000/oauth2/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=allo.droit&client_id=messaging-client&client_secret=secret"
```

### Exemple de réponse

```json
{
  "access_token": "******",
  "scope": "allo.droit",
  "token_type": "Bearer",
  "expires_in": 299
}
```

Le champ `access_token` contient le JWT signé que votre client pourra ensuite transmettre dans l'en-tête `Authorization`.

## Clients enregistrés

La configuration actuelle enregistre deux clients OAuth2 :

- `messaging-client`
  - secret : `secret`
  - méthodes d'authentification : `client_secret_basic`, `client_secret_post`
  - grant type : `client_credentials`
  - scope : `allo.droit`

- `messaging-client2`
  - secret : `mon-secret-local-exercise-1234-ok`
  - méthode d'authentification : `client_secret_jwt`
  - algorithme de signature : `HS256`
  - grant type : `client_credentials`
  - scope : `allo.droit`