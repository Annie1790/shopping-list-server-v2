CREATE TABLE IF NOT EXISTS grocery_list
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name text COLLATE "default" NOT NULL,
    is_completed boolean DEFAULT false
);
