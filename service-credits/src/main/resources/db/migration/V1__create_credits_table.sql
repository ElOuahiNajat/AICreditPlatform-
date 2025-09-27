CREATE TABLE credits (
                         id SERIAL PRIMARY KEY,
                         client_id BIGINT NOT NULL,
                         montant NUMERIC(15,2) NOT NULL,
                         duree_mois INT NOT NULL,
                         date_demande DATE,
                         date_approbation DATE,
                         statut VARCHAR(20) DEFAULT 'EN_ATTENTE',
                         montant_rembourse NUMERIC(15,2) DEFAULT 0
);
