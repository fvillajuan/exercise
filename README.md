# Serveur d'autorisation OAuth 2

Cette application est un serveur d'autorisation basé sur Spring Authorization Server.
Elle permet d'émettre des jetons JWT signés et de gérer les clients OAuth2 ainsi que les scopes associés.

## Objectif

Le projet simule un serveur d'authentification / d'autorisation utilisé par une application cliente pour obtenir un access token, puis l'utiliser pour accéder à des ressources protégées.

## Prérequis

- Java 21
- Gradle (ou le wrapper `gradlew` inclus dans le projet)
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

En mode `client_credentials`, le client s'authentifie avec Basic Auth :

- username : `messaging-client`
- password : `secret`

Et la requête contient aussi :

```text
grant_type=client_credentials
scope=allo.droit
```

### Exemple avec curl

Comme le client OAuth2 est authentifié en Basic Auth, il faut envoyer le `username` et le `password` dans l'en-tête HTTP d'authentification.

```bash
curl -X POST "http://localhost:9000/oauth2/token" \
  -u "messaging-client:secret" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=allo.droit"
```

Le `client_id` et le `client_secret` ne sont pas envoyés dans le corps de la requête lorsqu'on utilise la Basic Auth ; ils sont fournis via `-u`.

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

## Scopes disponibles

Le client configuré pour l'exercice possède les scopes suivants :

- `allo.droit`
- `openid`
- `profile`

## Points importants

- Le client OAuth2 est configuré avec l'identifiant `messaging-client`.
- Le secret attendu est `secret`.
- L'authorization server utilise le mode `client_credentials` pour l'émission de jetons dans ce cas d'usage.
- Les tokens sont signés et peuvent être décodés en inspectant le payload JWT.

## Dépannage

### Le serveur ne démarre pas

Vérifier que Java 21 est bien installé et que le projet n'est pas déjà en cours d'exécution sur les ports `9000` ou `9443`.

### La requête OAuth2 échoue

Vérifier que :

- le `client_id` est exactement `messaging-client`
- le `client_secret` est exactement `secret`
- le `grant_type` est bien `client_credentials`
- le `scope` est bien renseigné et conforme aux scopes configurés
