create table PLAYER (
  playerId integer primary key GENERATED BY DEFAULT AS IDENTITY(START WITH 100),
);

create table  SPACE_WORLD(
  worldid integer,
  startstep integer,
  startsegment integer,
  content varchar(300)
);