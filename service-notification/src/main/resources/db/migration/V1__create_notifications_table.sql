CREATE TABLE notifications (
                               id SERIAL PRIMARY KEY,
                               client_id BIGINT NOT NULL,
                               message TEXT NOT NULL,
                               sent BOOLEAN DEFAULT FALSE,        -- true si envoyé
                               created_at TIMESTAMP DEFAULT NOW(),
                               updated_at TIMESTAMP DEFAULT NOW()
);
