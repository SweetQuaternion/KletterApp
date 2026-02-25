CREATE EXTENSION IF NOT EXISTS pg_trgm;

SELECT set_limit(0.2);

CREATE INDEX idx_hallen_search_trgm
ON hallen
USING gin ((name || ' ' || adresse) gin_trgm_ops);

CREATE INDEX idx_hallen_name_prefix
ON hallen (name text_pattern_ops);