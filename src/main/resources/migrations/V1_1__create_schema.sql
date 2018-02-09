CREATE TABLE IF NOT EXISTS income_category
(
  id bigserial NOT NULL,
  name character varying(25) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wallet
(
  id bigserial NOT NULL,
  name character varying(25) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS income
(
  id bigserial NOT NULL,
  amount numeric(15,2) NOT NULL CHECK (amount > 0),
  comment character varying(225),
  category bigint NOT NULL REFERENCES income_category,
  wallet bigint NOT NULL REFERENCES wallet,
  created_at timestamp DEFAULT NOW(),
  PRIMARY KEY (id)
);
