INSERT INTO PLAYER VALUES (0);
INSERT INTO PLAYER VALUES (1);

INSERT INTO SPACE_WORLD VALUES (0,0,0)

-- worldid, segmentid, naturalorder, content
insert into segment values (0 ,0 , 0, '............M.M.M.M...P' )
insert into segment values (0 ,1 , 1, '.MM.............WM....' )
insert into segment values (0 ,2 , 2, '..WMW...............R.W' )

--  segmentdoorid, worldid,sourcesegment,targetsegment ,sourcestep ,targetstep
insert into segment_door values (0, 0, 0, 1, 7, 10)
insert into segment_door values (1, 0, 1, 2, 9, 5)
insert into segment_door values (2, 0, 2, 0, 10, 9)