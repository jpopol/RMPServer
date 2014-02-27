# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "Politician" ("ID" BIGINT NOT NULL PRIMARY KEY,"Firstname" VARCHAR NOT NULL,"Lastname" VARCHAR NOT NULL,"Party" VARCHAR NOT NULL,"Constituency" VARCHAR NOT NULL,"Url" VARCHAR NOT NULL);

# --- !Downs

drop table "Politician";

