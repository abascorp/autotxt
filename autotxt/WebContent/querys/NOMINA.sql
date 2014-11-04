SELECT 
''''||TRA.CODCIA
||''','''||TRA.DESCIA
||''','''||TRA.TIPNOM
||''','''||TRA.DESNOM
||''','''||TRIM(TRA.FICTRA)
||''','''||TRIM(TRA.CEDULA)
||''','''||TO_CHAR(TRA.FECING, 'DD/MM/YYYY')
||''','''||TRA.APELLIDO1
||''','''||TRA.APELLIDO2
||''','''||TRA.NOMBRE1
||''','''||TRA.NOMBRE2
||''','''||TRA.CODCAR
||''','''||TRA.DESCAR
||''','''||TRA.LOCALIDAD
||''','''||LOC.DESLO1
||''','''||TRA.CODSUC
||''','''||TRA.DESSUC
||''','''||'01'
||''','''||'UNICA'
||''','''||TRA.CODDEP
||''','''||TRA.DESDEP
||''','''||REPLACE(TRA.SUELD1*30, ',','.')
||''','''||HIS.FUNCTO
||''','''||HIS.CTO_CODCTO
||''','''||CTO.DESCTO
||''','''||REPLACE(HIS.CANCTO, ',','.')
||''','''||REPLACE(HIS.MONCTO,',','.')
||''','''||REPLACE(HIS.SALDOT,',','.')
||''','''||REPLACE(HEA.TOASIG,',','.')||''','''
||REPLACE(HEA.TODEDU,',','.')
||''','''||REPLACE(HEA.TONETO,',','.')
||''','''||HIS.FPRO_ANOCAL||''','''
||trim(HIS.MESCAL)
||''','''||HIS.FPRO_NUMPER
||''','''||UPPER(PER.DESPER)
||''','''||DECODE(TRA.FECRET,NULL,'0','1')
||''','''||0 --secautosrv.nextval  ---***** SECUENCIA DE AUTOSERVICIO
||''','''||'NA'
||''','''||'NA'
||''','''||'0'
||''','''||DECODE(TRA.SEXO,'1','M','F')
||''''  
FROM NMM024 HIS LEFT OUTER JOIN NM_TRABAJADOR TRA ON HIS.CIA_CODCIA  = TRA.CODCIA AND
                                                     HIS.TNOM_TIPNOM = TRA.TIPNOM AND 
                                                     HIS.TRAB_FICTRA = TRA.FICTRA 
                LEFT OUTER JOIN NMT033 PER        ON HIS.CIA_CODCIA  = PER.CIA_CODCIA AND
                                                     HIS.TNOM_TIPNOM = PER.TNOM_TIPNOM AND 
                                                     HIS.PROC_TIPPRO = PER.PROC_TIPPRO AND
                                                     HIS.SUBPRO      = PER.SUBPRO AND 
                                                     HIS.FPRO_ANOCAL = PER.ANOCAL AND 
                                                     HIS.FPRO_NUMPER = PER.NUMPER 
                LEFT OUTER JOIN NMT027 CTO        ON HIS.CIA_CODCIA  = CTO.CIA_CODCIA AND 
                                                     HIS.TNOM_TIPNOM = CTO.TNOM_TIPNOM AND 
                                                     HIS.CTO_CODCTO  = CTO.CODCTO 
                LEFT OUTER JOIN NMM023 HEA        ON HIS.CIA_CODCIA  = HEA.CIA_CODCIA AND
                                                     HIS.TNOM_TIPNOM = HEA.TNOM_TIPNOM AND 
                                                     HIS.PROC_TIPPRO = HEA.PROC_TIPPRO AND
                                                     HIS.SUBPRO      = HEA.SUBPRO AND 
                                                     HIS.FPRO_ANOCAL = HEA.FPRO_ANOCAL AND 
                                                     HIS.FPRO_NUMPER = HEA.FPRO_NUMPER AND
                                                     HIS.TRAB_FICTRA = HEA.TRAB_FICTRA
                LEFT OUTER JOIN NMT002 LOC        ON TRA.CODCIA = LOC.CIA_CODCIA AND
                                                     TRA.LOCALIDAD = LOC.CODLOC
WHERE
HIS.NOIMPR = '0' 
AND HIS.PROC_TIPPRO = 1