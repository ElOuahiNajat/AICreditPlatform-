# AICreditPlatform
# AICreditPlatform

## Sujet
Développement et déploiement d’une plateforme de gestion des crédits bancaires avec microservices Java et pipeline DevOps, avec intégration d’un assistant AI.

---

## Vue métier

### Objectif
Permettre à une banque de gérer efficacement les demandes de crédits de ses clients, avec évaluation automatique du risque, suivi des remboursements et notifications.

### Fonctionnalités principales
- Gestion des demandes de crédits : enregistrement, suivi, approbation/refus.
- Scoring automatique : calcul du risque pour chaque demande.
- Notifications : email ou SMS pour informer les clients.
- Suivi des remboursements : consultation de l’historique des paiements.

### Avantages métier
- Décisions rapides et fiables pour chaque demande.
- Réduction des erreurs humaines.
- Communication instantanée avec le client.
- Solution moderne et scalable grâce aux microservices et au DevOps.

---

## Vue développement

### Architecture technique
- Microservices Java (Spring Boot) :
  - Service Clients
  - Service Crédits
  - Service Scoring
  - Service Notifications
- Communication :
  - API REST (synchrones)
  - RabbitMQ (asynchrones pour notifications)
- Pipeline DevOps :
  - Build → Tests → Packaging → Déploiement (Docker + CI/CD)
  - Optionnel : Kubernetes pour orchestrer plusieurs conteneurs

### Workflow général
1. Client crée une demande → Service Crédits.
2. Service Crédits récupère les informations du client via Feign (Service Clients).
3. Service Crédits appelle Service Scoring pour évaluer la demande.
4. Service Crédits publie un message RabbitMQ → Service Notifications envoie email ou SMS.

---

## Endpoints principaux

### Service Clients
| Méthode | Endpoint | Fonction |
|---------|---------|---------|
| POST    | /api/clients | Créer un client |
| GET     | /api/clients | Lister tous les clients |
| GET     | /api/clients/{id} | Récupérer un client par ID |
| PUT     | /api/clients/{id} | Modifier un client |
| DELETE  | /api/clients/{id} | Supprimer un client |

### Service Crédits
| Fonctionnalité | Méthode | Endpoint |
|----------------|---------|---------|
| Créer une demande | POST | /api/credits/demandes |
| Lister demandes d’un client | GET | /api/credits/client/{clientId} |
| Mettre à jour statut | PATCH | /api/credits/demandes/{id}/status |
| Enregistrer remboursement | POST | /api/credits/remboursements |
| Export CSV/Excel | GET | /api/credits/export |

### Service Scoring
| Méthode | Endpoint | Fonction |
|---------|---------|---------|
| POST    | /api/scoring/calculate | Calculer le score d’un client |
| GET     | /api/scoring/client/{clientId} | Dernier score du client |
| PATCH   | /api/scoring/{id}/update-status | Modifier le statut du score |

### Service Notifications
| Méthode | Endpoint | Fonction |
|---------|---------|---------|
| POST    | /api/notifications/send | Envoyer notification (email/SMS) |
| GET     | /api/notifications/client/{clientId} | Lister notifications d’un client |
| PATCH   | /api/notifications/{id}/status | Mettre à jour le statut |
| DELETE  | /api/notifications/{id} | Supprimer une notification |

---

## Frontend Angular
- Modules : clients, credits, scoring, notifications.
- Composants : list-clients, add-client, etc.
- Option recommandée : composants standalone avec `bootstrapApplication`.

---

## Installation et exécution

### Backend
```bash
cd service-clients
mvn spring-boot:run

cd service-credits
mvn spring-boot:run

cd service-scoring
mvn spring-boot:run

cd service-notifications
mvn spring-boot:run
