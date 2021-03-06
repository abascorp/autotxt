SELECT ''''||'02'||''','''||'Elca Cosmeticos'||''','''||H.TNOM_TIPNOM||''','''||C.DESNOM||''','''||TRIM(H.FICTRA)||''','''||TRIM(H.CEDULA)
||''','''||TO_CHAR(H.FECING, 'DD/MM/YYYY')||''','''||H.APELL1||''','''||H.APELL2||''','''||H.NOMBR1||''','''||H.NOMBR2
||''','''||H.CGO_CAROCU||''','''||D.DESCAR||''','''||H.LOCA_CODLOC||''','''||E.DESLO1||''','''||H.SCS_CODSUC||''','''||F.DESSUC
||''','''||'01'||''','''||'UNICA'||''','''||H.DPTO_CODDEP||''','''||G.DESDEP
||''','''||TRIM(TO_CHAR(H.SUELD1*30,'99999999.99'))||''','''||TO_CHAR(A.FECFIN,'YYYY')
||''','''||DECODE(TO_CHAR(A.FECFIN,'MM'),'01','1',DECODE(TO_CHAR(A.FECFIN,'MM'),'02','2',DECODE(TO_CHAR(A.FECFIN,'MM'),'03','3',DECODE(TO_CHAR(A.FECFIN,'MM'),'04','4',DECODE(TO_CHAR(A.FECFIN,'MM'),'05','5',DECODE(TO_CHAR(A.FECFIN,'MM'),'06','6',DECODE(TO_CHAR(A.FECFIN,'MM'),'07','7',DECODE(TO_CHAR(A.FECFIN,'MM'),'08','8',DECODE(TO_CHAR(A.FECFIN,'MM'),'09','9',TO_CHAR(A.FECFIN,'MM'))))))))))
||''','''||TO_CHAR(A.FECFIN,'DD/MM/YYYY')
||''','''||A.CTO_CODCTO||''','''||I.DESCTO||''','''||TRIM(A.FACINT*100)||''','''||TRIM(TO_CHAR(A.MONCAP,'99999999.99'))
||''','''||TRIM(TO_CHAR(A.INTCAL,'99999999.99'))||'''' 
FROM NMM001 H, NMT001 B, NMT003 C, NMT004 D, NMT002 E, NMT038 F, NMT019 G, NMM037 A, NMT027 I
WHERE H.CIA_CODCIA=B.CODCIA
AND H.CIA_CODCIA=C.CIA_CODCIA
AND H.TNOM_TIPNOM=C.TIPNOM
AND H.CIA_CODCIA=D.CIA_CODCIA
AND H.CGO_CAROCU=D.CODCAR
AND H.CIA_CODCIA=E.CIA_CODCIA
AND H.LOCA_CODLOC=E.CODLOC
AND H.CIA_CODCIA=F.CIA_CODCIA
AND H.SCS_CODSUC=F.CODSUC
AND H.CIA_CODCIA=G.CIA_CODCIA
AND H.DPTO_CODDEP=G.CODDEP
AND H.CIA_CODCIA=A.CIA_CODCIA
AND H.FICTRA=A.TRAB_FICTRA
AND A.CIA_CODCIA=I.CIA_CODCIA
AND A.TNOM_TIPNOM=I.TNOM_TIPNOM
AND A.CTO_CODCTO=I.CODCTO
--AND I.FUNCTO IN ('1','2')
--AND TRIM(A.TRAB_FICTRA)='0369'
AND I.NOIMPR='0'
AND A.INTPAG=0

