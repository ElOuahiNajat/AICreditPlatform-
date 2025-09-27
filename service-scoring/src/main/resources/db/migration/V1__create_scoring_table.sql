CREATE TABLE scoring (
                         id BIGSERIAL PRIMARY KEY,
                         credit_id BIGINT NOT NULL,                -- référence à la demande de crédit
                         score INT NOT NULL,                       -- score calculé pour la demande
                         statut VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE', -- EN_ATTENTE, APPROUVE, REFUSE
                         date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

