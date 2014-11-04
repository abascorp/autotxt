SELECT case when TO_NUMBER(sum(a.canvac)-subquery1.canvac) = 0 
then ''''||'02'||''','''||'Elca Cosmeticos'||''','''||H.TNOM_TIPNOM||''','''||C.DESNOM||''','''||TRIM(A.TRAB_FICTRA)||''','''||TRIM(H.CEDULA)
||''','''||TO_CHAR(H.FECING, 'DD/MM/YYYY')||''','''||H.APELL1||''','''||H.APELL2||''','''||H.NOMBR1||''','''||H.NOMBR2
||''','''||H.CGO_CAROCU||''','''||D.DESCAR||''','''||H.LOCA_CODLOC||''','''||E.DESLO1||''','''||H.SCS_CODSUC||''','''||F.DESSUC
||''','''||'01'||''','''||'UNICA'||''','''||H.DPTO_CODDEP||''','''||G.DESDEP
||''','''||TRIM(TO_CHAR(H.SUELD1*30,'99999999.99'))||''','''||''
||''','''||''
||''','''||''||''','''||''||''','''||''||'''' 
else
''''||'02'||''','''||'Elca Cosmeticos'||''','''||H.TNOM_TIPNOM||''','''||C.DESNOM||''','''||TRIM(A.TRAB_FICTRA)||''','''||TRIM(H.CEDULA)
||''','''||TO_CHAR(H.FECING, 'DD/MM/YYYY')||''','''||H.APELL1||''','''||H.APELL2||''','''||H.NOMBR1||''','''||H.NOMBR2
||''','''||H.CGO_CAROCU||''','''||D.DESCAR||''','''||H.LOCA_CODLOC||''','''||E.DESLO1||''','''||H.SCS_CODSUC||''','''||F.DESSUC
||''','''||'01'||''','''||'UNICA'||''','''||H.DPTO_CODDEP||''','''||G.DESDEP
||''','''||TRIM(TO_CHAR(H.SUELD1*30,'99999999.99'))||''','''||max(to_char(a.feccon,'yyyy'))
||''','''||max(to_char(a.feccon,'dd/mm/yyyy'))
||''','''||A.CTO_CODCTO||''','''||I.DESCTO||''','''||TO_NUMBER(sum(a.canvac)-subquery1.canvac)||'''' end q 
FROM NMM001 H, NMT001 B, NMT003 C, NMT004 D, NMT002 E, NMT038 F, NMT019 G, nmm011 A, NMT027 I,
  (select trab_fictra, cto_codcto, sum(canvac) canvac, tipreg, max(to_char(feccon,'yyyy'))
    from nmm011
     where trim(trab_fictra) like '%' and tipreg='3'
      group by trab_fictra, cto_codcto, tipreg) subquery1
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
AND a.trab_fictra=subquery1.trab_fictra
and a.cto_codcto=subquery1.cto_codcto
and a.tipreg='1'
AND TRIM(A.TRAB_FICTRA) like '%'
--AND I.NOIMPR='0'
GROUP BY H.TNOM_TIPNOM, C.DESNOM,  H.CEDULA, H.FECING, H.APELL1, H.APELL2, H.NOMBR1, H.NOMBR2, I.DESCTO,
H.CGO_CAROCU, D.DESCAR, H.LOCA_CODLOC, E.DESLO1, H.SCS_CODSUC, F.DESSUC, H.DPTO_CODDEP, G.DESDEP, H.SUELD1,
a.trab_fictra, a.cto_codcto, subquery1.canvac

