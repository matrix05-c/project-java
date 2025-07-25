CREATE TABLE public.membre (
	id int NOT NULL,
	nom varchar NOT NULL,
	sexe varchar NOT NULL,
	age int NOT NULL,
	contact varchar NOT NULL,
	CONSTRAINT membre_pk PRIMARY KEY (id)
);

CREATE TABLE public.livre (
	id varchar NOT NULL,
	titre varchar NOT NULL,
	description varchar NOT NULL,
	auteur varchar NULL,
	exemplaire int DEFAULT 0 NOT NULL,
	CONSTRAINT livre_pk PRIMARY KEY (id),
	CONSTRAINT livre_unique UNIQUE (titre)
);

CREATE TABLE public.preter (
	id int NOT NULL,
	membre int NOT NULL,
	livre varchar NOT NULL,
	datepret timestamp NOT NULL,
	dateretour timestamp NULL,
	CONSTRAINT preter_pk PRIMARY KEY (id),
	CONSTRAINT preter_unique UNIQUE (datepret),
	CONSTRAINT preter_membre_fk FOREIGN KEY (membre) REFERENCES public.membre(id),
	CONSTRAINT preter_livre_fk FOREIGN KEY (livre) REFERENCES public.livre(id)
);

CREATE TABLE public.rendre (
	id int NOT NULL,
	membre int NOT NULL,
	livre varchar NOT NULL,
	daterendu timestamp NOT NULL,
	CONSTRAINT rendre_pk PRIMARY KEY (id),
	CONSTRAINT rendre_unique UNIQUE (daterendu),
	CONSTRAINT rendre_livre_fk FOREIGN KEY (livre) REFERENCES public.livre(id),
	CONSTRAINT rendre_membre_fk FOREIGN KEY (membre) REFERENCES public.membre(id)
);

CREATE TABLE public.compte (
	id int NOT NULL,
	nom varchar NOT NULL,
	mdp varchar NULL,
	CONSTRAINT compte_pk PRIMARY KEY (id),
	CONSTRAINT compte_unique UNIQUE (nom)
);

ALTER TABLE public.livre DROP COLUMN description;

ALTER TABLE public.rendre DROP CONSTRAINT rendre_livre_fk;
ALTER TABLE public.rendre ALTER COLUMN livre TYPE int USING livre::int;

ALTER TABLE public.preter DROP CONSTRAINT preter_livre_fk;
ALTER TABLE public.preter ALTER COLUMN livre TYPE int USING livre::int;

ALTER TABLE public.livre ALTER COLUMN id TYPE int USING id::int;
ALTER TABLE public.preter ADD CONSTRAINT preter_livre_fk FOREIGN KEY (livre) REFERENCES public.livre(id);
ALTER TABLE public.rendre ADD CONSTRAINT rendre_livre_fk FOREIGN KEY (livre) REFERENCES public.livre(id);

ALTER TABLE membre alter column id add generated always as identity;

ALTER TABLE livre alter column id add generated always as identity;

ALTER TABLE preter alter column id add generated always as identity;
