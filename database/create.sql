CREATE ROLE inf4375 LOGIN PASSWORD 'inf4375';

DROP DATABASE IF EXISTS tp;

CREATE DATABASE tp WITH OWNER=inf4375 ENCODING='UTF8';
\connect tp
CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;

create table foodtruck (

	id serial PRIMARY KEY,
	coordinate geometry(POINT,4326) NOT NULL,
	name varchar(500),
	datePresent varchar(50),
	Heure_debut varchar(50),
	Heure_fin varchar(50),
	Lieu varchar(50),
	Camion varchar(50),
	Truckid varchar(50),
	description varchar(5000)
);
ALTER TABLE foodtruck OWNER TO inf4375;

create table bixi (

	id serial PRIMARY KEY,
	coordinate geometry(POINT,4326) NOT NULL,
	name varchar(500),
	nbBikes INTEGER,
	nbEmptyDocks INTEGER

);
ALTER TABLE bixi OWNER TO inf4375;
