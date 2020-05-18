/*
 * Person 1: fnr = 00000000001, tpforhold = []
 * Person 2: fnr = 00000000002, tpforhold = [1111]
 * Person 3: fnr = 00000000003, tpforhold = [2222, 1111]
 * Person 4: fnr = 00000000004, tpforhold = [1111(ugyldig)]
 * Person 5: fnr = 00000000005, tpforhold = [1111, 2222(ugyldig)]
 * Person 6: fnr = 00000000006, tpforhold = [3333(utlands)]
 * Person 7: fnr = 00000000006, tpforhold = [2222, 3333(utlands)]
 */

-- Opprett personer
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000001', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000002', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000003', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000004', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000005', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000006', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000007', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000008', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);
INSERT INTO T_PERSON VALUES (T_PERSON_SEQ.NEXTVAL, '00000000009', '2001-01-01', 'INIT', '2001-01-01', 'INIT', 0);

-- Opprett TPNR
INSERT INTO T_TSS_TP VALUES ('11111111111', '1111', '000000000', 'TP1');
INSERT INTO T_TSS_TP VALUES ('22222222222', '2222', '000000000', 'TP2');
INSERT INTO T_TSS_TP VALUES ('33333333333', '3333', '000000000', 'TP3');
INSERT INTO T_TSS_TP VALUES ('44444444444', '4444', '111111111', 'TP4');

-- Opprett ytelse typer
INSERT INTO T_K_YTELSE_T VALUES ('ALDER',       'DESC', '2001-01-01', NULL, '2001-01-01', 'INIT', '2001-01-01', 'INIT', '1');
INSERT INTO T_K_YTELSE_T VALUES ('UFORE',       'DESC', '2001-01-01', NULL, '2001-01-01', 'INIT', '2001-01-01', 'INIT', '1');
INSERT INTO T_K_YTELSE_T VALUES ('GJENLEVENDE', 'DESC', '2001-01-01', NULL, '2001-01-01', 'INIT', '2001-01-01', 'INIT', '1');
INSERT INTO T_K_YTELSE_T VALUES ('BARN',        'DESC', '2001-01-01', NULL, '2001-01-01', 'INIT', '2001-01-01', 'INIT', '1');
INSERT INTO T_K_YTELSE_T VALUES ('AFP',         'DESC', '2001-01-01', NULL, '2001-01-01', 'INIT', '2001-01-01', 'INIT', '1');

-- Sett forhold
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 2, NULL, '11111111111', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '1', 'N', '0', 2, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 3, NULL, '11111111111', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '1', 'N', '0', 3, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 3, NULL, '22222222222', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '1', 'N', '0', 3, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 4, NULL, '11111111111', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '0', 'N', '0', 4, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 5, NULL, '11111111111', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '1', 'N', '0', 5, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 5, NULL, '22222222222', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '0', 'N', '0', 5, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 6, NULL, '33333333333', '2001-01-01', 'DATA', '2001-01-01', '1', 'DATA', 'TPLEV', '0', '1', 'N', '0', 6, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 7, NULL, '22222222222', '2001-01-01', 'DATA', '2001-01-01', '0', 'DATA', 'TPLEV', '0', '1', 'N', '0', 7, '2001-01-01', NULL);
INSERT INTO T_FORHOLD VALUES (T_FORHOLD_SEQ.NEXTVAL, 7, NULL, '33333333333', '2001-01-01', 'DATA', '2001-01-01', '1', 'DATA', 'TPLEV', '0', '1', 'N', '0', 7, '2001-01-01', NULL);

-- Sett ytelser
INSERT INTO T_YTELSE
    VALUES (T_YTELSE_SEQ.NEXTVAL, '1900-01-01', 'AFP', NULL, '1900-01-01', '1900-01-01', '2001-01-01', 'DATA', '2001-01-01', 'DATA', '0', '1', T_YTELSE_SEQ.CURRVAL, '2001-01-01', NULL);
INSERT INTO T_YTELSE
    VALUES (T_YTELSE_SEQ.NEXTVAL, '1900-01-01', 'AFP', NULL, '1900-01-01', '1900-01-01', '2001-01-01', 'DATA', '2001-01-01', 'DATA', '0', '1', T_YTELSE_SEQ.CURRVAL, '2001-01-01', NULL);
INSERT INTO T_YTELSE
    VALUES (T_YTELSE_SEQ.NEXTVAL, '1900-01-01', 'AFP', NULL, '1900-01-01', '1900-01-01', '2001-01-01', 'DATA', '2001-01-01', 'DATA', '0', '1', T_YTELSE_SEQ.CURRVAL, '2001-01-01', NULL);

INSERT INTO T_FORHOLD_YTELSE_HISTORIKK VALUES (2, 1);
INSERT INTO T_FORHOLD_YTELSE_HISTORIKK VALUES (2, 2);
INSERT INTO T_FORHOLD_YTELSE_HISTORIKK VALUES (1, 3);