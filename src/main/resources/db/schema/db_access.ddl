-- *********************************************
-- * SQL Microsoft Access generation
-- *--------------------------------------------
-- * DB-MAIN version: 10.0.3
-- * Generator date: Aug 17 2017
-- * Generation date: Sat Jan  5 19:24:33 2019
-- * Schema: Schema_Raffinamento/1-1
-- *********************************************


-- Database Section
-- ________________


-- Tables Section
-- _____________

create table [Acquisto] (
     [ID_Acquisto] counter not null,
     [Data] datetime not null,
     [ID_Cliente] long not null,
     [Prezzo_Totale] single not null,
     [ID_Aziendale] long not null,
     constraint [ID_Acquisto_ID] primary key ([ID_Acquisto]),
     constraint [SID_Acquisto] unique ([ID_Cliente], [Data]));

create table [Assunzione] (
     [ID_Operaio] long not null,
     [Data] datetime not null,
     [ID_Proprietario] long not null,
     constraint [FKOperaio_ID] primary key ([ID_Operaio]));

create table [Botte] (
     [ID_Botte] short not null,
     [Capacita] single not null,
     [Cantina] short not null,
     constraint [ID_Botte] primary key ([ID_Botte]));

create table [Cliente] (
     [ID_Cliente] counter not null,
     [Nome] varchar(20) not null,
     [Cognome] varchar(20) not null,
     [Ind_Via] varchar(20) not null,
     [Ind_Civico] short not null,
     [Ind_Citta] varchar(20) not null,
     [Telefono] varchar(10) not null,
     constraint [ID_Cliente_ID] primary key ([ID_Cliente]),
     constraint [SID_Cliente] unique ([Nome], [Cognome]));

create table [Dettagli_Acquisto] (
     [ID_Acquisto] long not null,
     [ID_Vino] long not null,
     [Numero_Bottiglie] short,
     [Prezzo_Totale_Bottiglia] single,
     [Numero_Litri] single,
     [Prezzo_Totale_Damigiana] single,
     constraint [ID_Dettagli_Acquisto] primary key ([ID_Vino], [ID_Acquisto]));

create table [Feccia] (
     [ID_Sfecciatura] long not null,
     [Quantita] single not null,
     [Botte] short not null,
     constraint [FKPro_Fec_ID] primary key ([ID_Sfecciatura]));

create table [Gruppo_Pigiatura] (
     [ID_Operaio] long not null,
     [ID_Pigiatura] long not null,
     [Ore_Lavoro] short,
     constraint [ID_Gruppo_Pigiatura] primary key ([ID_Operaio], [ID_Pigiatura]));

create table [Gruppo_Raccolta] (
     [ID_Operaio] long not null,
     [ID_Raccolta] long not null,
     [Ore_Lavoro] short,
     [ID_Vend] long,
     constraint [ID_Gruppo_Raccolta] primary key ([ID_Operaio], [ID_Raccolta]));

create table [Gruppo_Sfecciatura] (
     [ID_Operaio] long not null,
     [ID_Sfecciatura] long not null,
     [Ore_Lavoro] short,
     constraint [ID_Gruppo_Sfecciatura] primary key ([ID_Operaio], [ID_Sfecciatura]));

create table [Gruppo_Svinatura] (
     [ID_Operaio] long not null,
     [ID_Svinatura] long not null,
     [Ore_Lavoro] short,
     constraint [ID_Gruppo_Svinatura] primary key ([ID_Operaio], [ID_Svinatura]));

create table [Login] (
     [ID_Aziendale] long not null,
     [Password] varchar(50) not null,
     [Ultimo_Accesso] datetime,
     constraint [FKRealizzazione_ID] primary key ([ID_Aziendale]));

create table [Mosto] (
     [ID_Pigiatura] long not null,
     [Quantita] single not null,
     [Botte] short not null,
     constraint [FKPro_Mos_ID] primary key ([ID_Pigiatura]));

create table [Persona_Azienda] (
     [ID] counter(3000) not null,
     [Nome] varchar(20) not null,
     [Cognome] varchar(20) not null,
     [Ind_Via] varchar(20) not null,
     [Ind_Civico] short not null,
     [Ind_Citta] varchar(20) not null,
     [Telefono] varchar(10) not null,
     [Data_di_Nascita] datetime not null,
     [Tipo_Login] varchar(20) not null,
     [Stipendio_Mensile] single,
     constraint [ID_Persona_Azienda_ID] primary key ([ID]),
     constraint [SID_Persona_Azienda] unique ([Nome], [Cognome]));

create table [Pigiatura] (
     [ID_Fase] counter not null,
     [Data] datetime not null,
     [Uva] varchar(20) not null,
     constraint [ID_Pigiatura_ID] primary key ([ID_Fase]),
     constraint [SID_Pigiatura] unique ([Uva], [Data]));

create table [Raccolta] (
     [ID_Fase] counter not null,
     [Data] datetime not null,
     [Uva] varchar(20) not null,
     [Quantita] single not null,
     constraint [ID_Raccolta_ID] primary key ([ID_Fase]),
     constraint [SID_Raccolta] unique ([Uva], [Data]));

create table [Raspi] (
     [ID_Pigiatura] long not null,
     [Quantita] single not null,
     constraint [FKPro_Ras_ID] primary key ([ID_Pigiatura]));

create table [Sfecciatura] (
     [ID_Fase] counter not null,
     [Data] datetime not null,
     [Uva] varchar(20) not null,
     constraint [ID_Sfecciatura_ID] primary key ([ID_Fase]),
     constraint [SID_Sfecciatura] unique ([Uva], [Data]));

create table [Svinatura] (
     [ID_Fase] counter not null,
     [Data] datetime not null,
     [Uva] varchar(20) not null,
     constraint [ID_Svinatura_ID] primary key ([ID_Fase]),
     constraint [SID_Svinatura] unique ([Uva], [Data]));

create table [Uva] (
     [Nome_Uva] varchar(20) not null,
     [Tipologia] varchar(20) not null,
     [Prezzo_al_Litro] single not null,
     [Prezzo_a_Bottiglia] single not null,
     constraint [ID_Uva_ID] primary key ([Nome_Uva]));

create table [Vendemmiatrice] (
     [ID_Vend] counter not null,
     [Marca] varchar(20) not null,
     [Modello] varchar(20) not null,
     [Costo] single,
     [Tariffa_oraria] single,
     constraint [ID_Vendemmiatrice_ID] primary key ([ID_Vend]),
     constraint [SID_Vendemmiatrice] unique ([Marca], [Modello]));

create table [Vinaccia] (
     [ID_Svinatura] long not null,
     [Quantita] single not null,
     constraint [FKPro_Vic_ID] primary key ([ID_Svinatura]));

create table [Vino] (
     [ID_Sfecciatura] long not null,
     [Quantita] single not null,
     [Quantita_Attuale] single not null,
     [Botte] short not null,
     constraint [FKPro_Vin_ID] primary key ([ID_Sfecciatura]));

create table [VNF] (
     [ID_Svinatura] long not null,
     [Quantita] single not null,
     [Botte] short not null,
     constraint [FKPro_VNF_ID] primary key ([ID_Svinatura]));


-- Constraints Section
-- ___________________

alter table [Acquisto] add constraint [FKGestione_FK]
     foreign key ([ID_Aziendale])
     references [Persona_Azienda];

alter table [Acquisto] add constraint [FKEffettuazione]
     foreign key ([ID_Cliente])
     references [Cliente];

alter table [Assunzione] add constraint [FKProprietario_FK]
     foreign key ([ID_Proprietario])
     references [Persona_Azienda];

alter table [Assunzione] add constraint [FKOperaio_FK]
     foreign key ([ID_Operaio])
     references [Persona_Azienda];

alter table [Dettagli_Acquisto] add constraint [FKDet_Vin]
     foreign key ([ID_Vino])
     references [Vino];

alter table [Dettagli_Acquisto] add constraint [FKDet_Acq_FK]
     foreign key ([ID_Acquisto])
     references [Acquisto];

alter table [Feccia] add constraint [FKPro_Fec_FK]
     foreign key ([ID_Sfecciatura])
     references [Sfecciatura];

alter table [Feccia] add constraint [FKCon_Fec_FK]
     foreign key ([Botte])
     references [Botte];

alter table [Gruppo_Pigiatura] add constraint [FKCom_Pig_FK]
     foreign key ([ID_Pigiatura])
     references [Pigiatura];

alter table [Gruppo_Pigiatura] add constraint [FKCom_Gru_Pig]
     foreign key ([ID_Operaio])
     references [Persona_Azienda];

alter table [Gruppo_Raccolta] add constraint [FKUtilizzo_FK]
     foreign key ([ID_Vend])
     references [Vendemmiatrice];

alter table [Gruppo_Raccolta] add constraint [FKCom_Rac_FK]
     foreign key ([ID_Raccolta])
     references [Raccolta];

alter table [Gruppo_Raccolta] add constraint [FKCom_Gru_Rac]
     foreign key ([ID_Operaio])
     references [Persona_Azienda];

alter table [Gruppo_Sfecciatura] add constraint [FKCom_Sfe_FK]
     foreign key ([ID_Sfecciatura])
     references [Sfecciatura];

alter table [Gruppo_Sfecciatura] add constraint [FKCom_Gru_Sfe]
     foreign key ([ID_Operaio])
     references [Persona_Azienda];

alter table [Gruppo_Svinatura] add constraint [FKCom_Svi_FK]
     foreign key ([ID_Svinatura])
     references [Svinatura];

alter table [Gruppo_Svinatura] add constraint [FKCom_Gru_Svi]
     foreign key ([ID_Operaio])
     references [Persona_Azienda];

alter table [Login] add constraint [FKRealizzazione_FK]
     foreign key ([ID_Aziendale])
     references [Persona_Azienda];

alter table [Mosto] add constraint [FKPro_Mos_FK]
     foreign key ([ID_Pigiatura])
     references [Pigiatura];

alter table [Mosto] add constraint [FKCon_Mos_FK]
     foreign key ([Botte])
     references [Botte];

alter table [Pigiatura] add constraint [FKRif_Pig]
     foreign key ([Uva])
     references [Uva];

alter table [Raccolta] add constraint [FKRif_Rac]
     foreign key ([Uva])
     references [Uva];

alter table [Raspi] add constraint [FKPro_Ras_FK]
     foreign key ([ID_Pigiatura])
     references [Pigiatura];

alter table [Sfecciatura] add constraint [FKRif_Sfe]
     foreign key ([Uva])
     references [Uva];


alter table [Svinatura] add constraint [FKRif_Svi]
     foreign key ([Uva])
     references [Uva];

alter table [Vinaccia] add constraint [FKPro_Vic_FK]
     foreign key ([ID_Svinatura])
     references [Svinatura];

alter table [Vino] add constraint [FKPro_Vin_FK]
     foreign key ([ID_Sfecciatura])
     references [Sfecciatura];

alter table [Vino] add constraint [FKCon_Vin_FK]
     foreign key ([Botte])
     references [Botte];

alter table [VNF] add constraint [FKPro_VNF_FK]
     foreign key ([ID_Svinatura])
     references [Svinatura];

alter table [VNF] add constraint [FKCon_VNF_FK]
     foreign key ([Botte])
     references [Botte];


-- Index Section
-- _____________

create index [FKGestione_IND]
     on [Acquisto] ([ID_Aziendale]);

create index [FKProprietario_IND]
     on [Assunzione] ([ID_Proprietario]);

create index [FKDet_Acq_IND]
     on [Dettagli_Acquisto] ([ID_Acquisto]);

create index [FKCon_Fec_IND]
     on [Feccia] ([Botte]);

create index [FKCom_Pig_IND]
     on [Gruppo_Pigiatura] ([ID_Pigiatura]);

create index [FKUtilizzo_IND]
     on [Gruppo_Raccolta] ([ID_Vend]);

create index [FKCom_Rac_IND]
     on [Gruppo_Raccolta] ([ID_Raccolta]);

create index [FKCom_Sfe_IND]
     on [Gruppo_Sfecciatura] ([ID_Sfecciatura]);

create index [FKCom_Svi_IND]
     on [Gruppo_Svinatura] ([ID_Svinatura]);

create index [FKCon_Mos_IND]
     on [Mosto] ([Botte]);

create index [FKCon_Vin_IND]
     on [Vino] ([Botte]);

create index [FKCon_VNF_IND]
     on [VNF] ([Botte]);
