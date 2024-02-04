CREATE TABLE IF NOT EXISTS public.grocery_list
(
    grocery_id integer NOT NULL DEFAULT nextval('grocery_list_grocery_id_seq'::regclass),
    grocery_name text COLLATE pg_catalog."default" NOT NULL,
    is_completed boolean DEFAULT false,
    CONSTRAINT grocery_list_pkey PRIMARY KEY (grocery_id)
)
COMMENT ON TABLE public.grocery_list
    IS 'List of grocery items ';