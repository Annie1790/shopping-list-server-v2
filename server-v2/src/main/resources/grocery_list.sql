CREATE TABLE IF NOT EXISTS public.grocery_list
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    is_completed boolean DEFAULT false
);