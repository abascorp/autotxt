package org.autotxt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.sql.DataSource;



import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class Controller {
	//Contructor
	
	public Controller() {
       
	}
	
	private static final int BUFFER_SIZE = 6124;
	protected static final String RUTA_EXTERNO = File.separator + "querys";
	ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext(); //Toma ruta real de la aplicación
	private String msj = "";
	private String claseResult = "";
	private String archivo = "";
	private String anio = "";
	private String mescal = "";
	private String pool  = "jdbc/spi";
	private String opc = "0";
	protected String usuario = "";
	protected String clave = "";
	private String detalle = "";
	private String codcia = "";
	
	//Coneccion a base de datos
	//Pool de conecciones JNDI
	
	private DataSource ds;
	//Coneccion a base de datos
	//Pool de conecciones JNDIFARM
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet r;

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the mescal
	 */
	public String getMescal() {
		return mescal;
	}

	/**
	 * @param mescal the mescal to set
	 */
	public void setMescal(String mescal) {
		this.mescal = mescal;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	

	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	
	
	/**
	 * @return the codcia
	 */
	public String getCodcia() {
		return codcia;
	}

	/**
	 * @param codcia the codcia to set
	 */
	public void setCodcia(String codcia) {
		this.codcia = codcia;
	}

	public void handleFileUpload(FileUploadEvent event) {
		File ruta = new File(extContext.getRealPath(RUTA_EXTERNO) + File.separator +  event.getFile().getFileName());
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(ruta);

				byte[] buffer = new byte[BUFFER_SIZE];

				int bulk;
				InputStream inputStream = event.getFile().getInputstream();
				while (true) {
					bulk = inputStream.read(buffer);
					if (bulk < 0) {
						break;
					}
					fileOutputStream.write(buffer, 0, bulk);
					fileOutputStream.flush();
				}

				fileOutputStream.close();
				inputStream.close();

				msj = "Archivo subido";
				claseResult = "exito";

			} catch (IOException e) {
				e.printStackTrace();
				msj = "Error Subiendo el archivo, no consigue archivo o directorio";
				claseResult = "error";
			}
	}
	
	 /**
    Lee un archivo de texto. Utilizado para query de nomina externa. Completa el query internamente con los parametros,
    de compañia, nomina y periodo.
    @param ruta Directorio de lectura del archivo
    @param archivo Nombre del archivo que desea leer
    @param anio año calendario
    @param mescal mes calendario
    @return Retorna string completo query generado en el archivo .sql
     **/
    public  String readFile(String ruta, String archivo, String anio, String mescal) throws IOException {
        String lineSep = System.getProperty("line.separator");
        String queryResult = "";
        try {
            FileReader fr = new FileReader(ruta + File.separator + archivo + ".sql");
            @SuppressWarnings("resource")
			BufferedReader bf = new BufferedReader(fr);
            String nextLine = "";
            String paramQuery = "";
          //Valida los archivos a buscar
            if(procSelected.equals("N")){
            	
            	imescal = "";
        		if(mescal.equals("1")){
        			imescal = "01";
        		}
        		if(mescal.equals("2")){
        			imescal = "02";
        		}
        		if(mescal.equals("3")){
        			imescal = "03";
        		}
        		if(mescal.equals("4")){
        			imescal = "04";
        		}
        		if(mescal.equals("5")){
        			imescal = "05";
        		}
        		if(mescal.equals("6")){
        			imescal = "06";
        		}
        		if(mescal.equals("7")){
        			imescal = "07";
        		}
        		if(mescal.equals("8")){
        			imescal = "08";
        		}
        		if(mescal.equals("9")){
        			imescal = "09";
        		} 
        		if(mescal.equals("10")){
        			imescal = "10";
        		} 
        		if(mescal.equals("11")){
        			imescal = "11";
        		} 
        		if(mescal.equals("12")){
        			imescal = "12";
        		}
        		paramQuery = " AND HIS.CIA_CODCIA ='" + codcia + "' AND HIS.FPRO_ANOCAL like '" + anio + "%' AND LPAD(TO_CHAR(HIS.MESCAL),2,'00') LIKE '" + imescal + "%' ORDER BY HIS.CIA_CODCIA, HIS.TNOM_TIPNOM, HIS.TRAB_FICTRA, HIS.FPRO_ANOCAL, HIS.MESCAL, HIS.FPRO_NUMPER";
        	}
        	if(procSelected.equals("V")){
        		paramQuery = " and a.cia_codcia ='" + codcia.toUpperCase() + "'";
        		paramQuery += " GROUP BY H.TNOM_TIPNOM, C.DESNOM, H.FICTRA, H.CEDULA, H.FECING, H.APELL1, H.APELL2, H.NOMBR1";
        		paramQuery += " ,H.NOMBR2, H.CGO_CAROCU, D.DESCAR, H.LOCA_CODLOC, E.DESLO1, H.SCS_CODSUC, F.DESSUC, H.DPTO_CODDEP";
        		paramQuery += " , G.DESDEP, subquery1.cto,subquery1.canvac,  A.CTO_CODCTO, I.DESCTO, a.tipreg, h.sueld1, H.NOMCI2, A.CIA_CODCIA";
        		paramQuery += " order by h.fictra,  a.cto_codcto";
        	}
        	if(procSelected.equals("P")){
        		
            	imescal = "";
        		if(mescal.equals("1")){
        			imescal = "01";
        		}
        		if(mescal.equals("2")){
        			imescal = "02";
        		}
        		if(mescal.equals("3")){
        			imescal = "03";
        		}
        		if(mescal.equals("4")){
        			imescal = "04";
        		}
        		if(mescal.equals("5")){
        			imescal = "05";
        		}
        		if(mescal.equals("6")){
        			imescal = "06";
        		}
        		if(mescal.equals("7")){
        			imescal = "07";
        		}
        		if(mescal.equals("8")){
        			imescal = "08";
        		}
        		if(mescal.equals("9")){
        			imescal = "09";
        		} 
        		if(mescal.equals("10")){
        			imescal = "10";
        		} 
        		if(mescal.equals("11")){
        			imescal = "11";
        		} 
        		if(mescal.equals("12")){
        			imescal = "12";
        		}
        		
        		paramQuery  = " LEFT OUTER JOIN NM_TRABAJADOR TRA ON ABO.CIA_CODCIA = TRA.CODCIA AND ";
        		paramQuery += "	                                     ABO.TNOM_TIPNOM = TRA.TIPNOM AND ";
        		paramQuery += "	                                     ABO.TRAB_FICTRA = TRA.FICTRA ";             
        		paramQuery += "	LEFT OUTER JOIN NMT027 CTO        ON ABO.CIA_CODCIA = CTO.CIA_CODCIA AND ";
        		paramQuery += "	                                     ABO.TNOM_TIPNOM = CTO.TNOM_TIPNOM AND "; 
        		paramQuery += "	                                     ABO.CTO_CODCTO = CTO.CODCTO "; 
        		paramQuery += "	LEFT OUTER JOIN NMT002 LOC        ON TRA.CODCIA = LOC.CIA_CODCIA AND ";
        		paramQuery += "	                                     TRA.LOCALIDAD = LOC.CODLOC ";                                                     
        		paramQuery += "	LEFT OUTER JOIN (SELECT ";
        		paramQuery += "	                 CTOIN.CIA_CODCIA, CTOIN.TNOM_TIPNOM, CTOIN.TRAB_FICTRA FICTRA, CTOIN.CTO_CODCTO CODCTO, CTOIN.SALDOT SALDO ";
        		paramQuery += "	                 FROM NMM013 CTOIN "; 
        		paramQuery += "	                 WHERE CTOIN.CTO_CODCTO IN (409) AND ";
        		paramQuery += "	                 (CTOIN.FECMOV,CTOIN.CONSEC) IN (SELECT ";
        		paramQuery += "	                                                 MAX(FECMOV),MAX(CONSEC) ";
        		paramQuery += "	                                                 FROM NMM013 P ";
        		paramQuery += "	                                                WHERE ";
        		paramQuery += "	                                                 P.CIA_CODCIA = CTOIN.CIA_CODCIA AND ";
        		paramQuery += "	                                                 P.TNOM_TIPNOM = CTOIN.TNOM_TIPNOM AND ";
        		paramQuery += "	                                                 P.CTO_CODCTO = CTOIN.CTO_CODCTO AND ";
        		paramQuery += "	                                                 P.TRAB_SUBTIP = CTOIN.TRAB_SUBTIP AND ";
        		paramQuery += "	                                                 P.TRAB_FICTRA = CTOIN.TRAB_FICTRA AND ";
        		paramQuery += "	                                                 TO_CHAR(P.FECMOV,'YYYY')||TO_CHAR(P.FECMOV,'MM') <= '" + anio + imescal + "' AND ";
        		paramQuery += "	                                                 P.FECMOV IN (SELECT ";
        		paramQuery += "	                                                              MAX(FECMOV) ";
        		paramQuery += "	                                                              FROM ";
        		paramQuery += "	                                                              NMM013 P2 ";
        		paramQuery += "	                                                              WHERE ";
        		paramQuery += "	                                                              P2.CIA_CODCIA = P.CIA_CODCIA AND ";
        		paramQuery += "	                                                              P2.TNOM_TIPNOM = P.TNOM_TIPNOM AND  ";
        		paramQuery += "	                                                              P2.CTO_CODCTO = P.CTO_CODCTO AND  ";
        		paramQuery += "	                                                              P2.TRAB_SUBTIP = P.TRAB_SUBTIP AND  ";
        		paramQuery += "	                                                              P2.TRAB_FICTRA = P.TRAB_FICTRA AND ";
        		paramQuery += "	                                                              TO_CHAR(P2.FECMOV,'YYYY')||TO_CHAR(P2.FECMOV,'MM') <= '" + anio + imescal + "'))) ANT ON ABO.CIA_CODCIA = ANT.CIA_CODCIA AND ";
        		paramQuery += "	                                                                                                                                                    ABO.TNOM_TIPNOM = ANT.TNOM_TIPNOM AND  ";
        		paramQuery += "	                                                                                                                                                    ABO.TRAB_FICTRA = ANT.FICTRA  ";
        		paramQuery += "	LEFT OUTER JOIN (SELECT CIA_CODCIA, TNOM_TIPNOM, TRAB_FICTRA FICTRA, SUM(MONABO) CAPBRU ";
        		paramQuery += "	                 FROM NMM036 ";
        		paramQuery += "	                 WHERE ABOPAG='0' ";
        		paramQuery += "	                 AND TO_CHAR(FECABO,'YYYY')||TO_CHAR(FECABO,'MM') <= '" + anio + imescal + "'";
        		paramQuery += "	                 GROUP BY CIA_CODCIA, TNOM_TIPNOM, TRAB_FICTRA ";
        		paramQuery += "	                 ORDER BY 1,2,3) CAB ON ABO.CIA_CODCIA = CAB.CIA_CODCIA AND ";
        		paramQuery += "	                                        ABO.TNOM_TIPNOM = CAB.TNOM_TIPNOM AND  ";
        		paramQuery += "	                                        ABO.TRAB_FICTRA = CAB.FICTRA ";
        		paramQuery += "	WHERE ";
        		paramQuery += "	CTO.NOIMPR = '0' AND  ";
        		paramQuery += "	ABO.ABOPAG = '0' AND  ";
        		paramQuery += "	ABO.CIA_CODCIA = '" + codcia + "' AND ";
        		paramQuery += "	TO_CHAR(ABO.FECABO,'YYYY') = '" + anio + "' AND ";
        	    paramQuery += " TO_CHAR(ABO.FECABO,'MM') = '" + imescal + "'";
        	}
        	if(procSelected.equals("I")){
        		paramQuery = "";
        	}
        	if(procSelected.equals("PR")){
        		String imescal = "";
        		if(mescal.equals("1")){
        			imescal = "01";
        		}
        		if(mescal.equals("2")){
        			imescal = "02";
        		}
        		if(mescal.equals("3")){
        			imescal = "03";
        		}
        		if(mescal.equals("4")){
        			imescal = "04";
        		}
        		if(mescal.equals("5")){
        			imescal = "05";
        		}
        		if(mescal.equals("6")){
        			imescal = "06";
        		}
        		if(mescal.equals("7")){
        			imescal = "07";
        		}
        		if(mescal.equals("8")){
        			imescal = "08";
        		}
        		if(mescal.equals("9")){
        			imescal = "09";
        		} 
        		if(mescal.equals("10")){
        			imescal = "10";
        		} 
        		if(mescal.equals("11")){
        			imescal = "11";
        		} 
        		if(mescal.equals("12")){
        			imescal = "12";
        		} 
        		
        		paramQuery = " and to_char(fecmov,'mmyyyy') =" +imescal+anio;
        	}
        	if(procSelected.equals("U")){
        		
        		paramQuery = " and anocal like '" + anio + "%' and trim(mescal) like '" + mescal + "%'";
        		paramQuery += "  and a.cia_codcia ='" + codcia.toUpperCase() + "'";
        		paramQuery += " group by H.TNOM_TIPNOM, C.DESNOM, H.FICTRA, H.CEDULA, H.FECING, H.APELL1, H.APELL2, H.NOMBR1, H.NOMBR2,";
        		paramQuery += " H.CGO_CAROCU, D.DESCAR, H.LOCA_CODLOC, E.DESLO1, H.SCS_CODSUC, F.DESSUC, H.DPTO_CODDEP, G.DESDEP, H.SUELD1,";
        		paramQuery += " anocal, mescal, b.nomci2, a.cia_codcia";
        		
        	}
            StringBuilder sb = new StringBuilder();
            while ((nextLine = bf.readLine()) != null) {
                sb.append(nextLine);
                sb.append(lineSep);
            }
            queryResult = sb.toString().replace("XXXX", anio).replace("XX", mescal) + paramQuery;
            System.out.println(queryResult);
            //System.out.println("Proceso seleccionado:" + procSelected);
        } catch (Exception e) {
        	msj = "No existe el fichero " + archivo + ".sql. Súbalo al servidor";
        	claseResult = "error";
            //System.out.println("No existe el fichero " + archivo + ".sql. Súbalo al servidor");
        }
        return queryResult;
    }

    /**
    Genera un archivo de texto. Clase generica para crear archivos txt a partir de una consulta .sql.
    la lectura del txt es completada por los parametros en la clase readFile, los cuales son compañia y nomina (anio)
    y periodo (mescal).
    Una vez que lee del fichero, ejecuta una consulta utilizando la clase PntGenerica y devuelve el resultado en un arreglo.
    Para ello internamente se define una variable String[][] tabla. Se recorre la busqueda y se toma el valor de la tabla[i][0],
    posteriormente se genera un fichero txt con esos valores.
    @param ruta Directorio de lectura del archivo
    @param archivo Nombre del archivo que desea leer
    @param anio Año calendario
    @param mescal Mes calendario
    @param nombrTxt Nombre de salida del fichero txt
    @param pool Pool de conexiones para la consulta generica
    @param opc Número de opcion para saber si archivo lleva encabezado o solo detalle. 0-detalle,1-encabezado y detalle, 
    2-encabezado-detalle-pie, 3-detalle y pie
     **/
    public void writeFile() throws IOException, NamingException {
    	//Valida que no existan campos nulos
    	if(valida()){//No hace nada si son nulos
    	//Valida que el usuario y la clave coincidan
    	if(!usuario.equals(readUser(extContext.getRealPath(RUTA_EXTERNO)))){
    		msj = "Usuario inválido";
    		claseResult = "error";
    		//System.out.println("Usuario inválido");
    	} else if (!clave.equals(readPass(extContext.getRealPath(RUTA_EXTERNO)))){
    		msj = "Contraseña inválida";
    		claseResult = "error";
    		//System.out.println("Contraseña inválida");
    	} else {
    	//Valida los archivos a buscar
    	if(procSelected.equals("N")){
    		archivo = "NOMINA";
    	}
    	if(procSelected.equals("V")){
    		archivo = "VACACION";
    	}
    	if(procSelected.equals("P")){
    		archivo = "PRESTACION";
    	}
    	if(procSelected.equals("I")){
    		archivo = "INTERESES";
    	}
    	if(procSelected.equals("PR")){
    		archivo = "PRESTAMOS";
    	}
    	if(procSelected.equals("U")){
    		archivo = "UTILIDADES";
    	}
        
        try {
        	//Creamos nuestro String que vamos a escribir en el fichero, para ello leemos el archivo .sql con el metodo (readFile)
            String myQuery = readFile(extContext.getRealPath(RUTA_EXTERNO), archivo, anio, mescal);
            //Hacemos la consulta en la tabla
            PntGenerica pntGen = new PntGenerica();
            pntGen.selectPntGenerica(myQuery, pool);
            String[][] tabla = pntGen.getArray();
            int rows = pntGen.getRows();
            //Creamos un Nuevo objeto FileWriter dandole
            //como parametros la ruta y nombre del fichero            
            FileWriter fichero = new FileWriter(extContext.getRealPath(RUTA_EXTERNO) + File.separator + procSelected+ anio+mescal + ".txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(extContext.getRealPath(RUTA_EXTERNO) + File.separator +anio+mescal + ".txt", true));
            //Si existe encabezado lo leemos. Opcion 1. El txt lleva encabezado y detalle

            if (opc.equals("1")) {
                String myQueryEncab = readFile(extContext.getRealPath(RUTA_EXTERNO), archivo + "_encab", anio, mescal);
                //Hacemos la consulta en la tabla
                PntGenerica pntGenEncab = new PntGenerica();
                pntGenEncab.selectPntGenerica(myQueryEncab, pool);
                String[][] tablaEncab = pntGenEncab.getArray();
                //Insertamos el texto creado y si trabajamos
                //en Windows terminaremos cada linea con "\r\n", recorriendo la consulta sql
                out.write(tablaEncab[0][0] + "\r\n");
                for (int i = 0; i < rows; i++) {
                    out.write(tabla[i][0] + "\r\n");

                } // fin del loop for

                //Opcion 2. El txt lleva encabezado, detalle y pie de pagina    
            } else if (opc.equals("2")) {
                String myQueryEncab = readFile(extContext.getRealPath(RUTA_EXTERNO), archivo + "_encab", anio, mescal);
                //Hacemos la consulta en la tabla
                PntGenerica pntGenEncab = new PntGenerica();
                pntGenEncab.selectPntGenerica(myQueryEncab, pool);
                String[][] tablaEncab = pntGenEncab.getArray();
                //Pie
                String myQueryPie = readFile(extContext.getRealPath(RUTA_EXTERNO), archivo + "_pie", anio, mescal);
                //Hacemos la consulta en la tabla
                PntGenerica pntGenPie = new PntGenerica();
                pntGenPie.selectPntGenerica(myQueryPie, pool);
                String[][] tablaPie = pntGenPie.getArray();
                //Insertamos el texto creado y si trabajamos
                //en Windows terminaremos cada linea con "\r\n", recorriendo la consulta sql
                out.write(tablaEncab[0][0] + "\r\n");
                for (int i = 0; i < rows; i++) {
                    out.write(tabla[i][0] + "\r\n");

                } // fin del loop for
                out.write(tablaPie[0][0] + "\r\n");
                //Opcion 3. El txt lleva detalle y pie de pagina    

            } else if (opc.equals("3")) {
                //Pie
                String myQueryPie = readFile(extContext.getRealPath(RUTA_EXTERNO), archivo + "_pie", anio, mescal);
                //Hacemos la consulta en la tabla
                PntGenerica pntGenPie = new PntGenerica();
                pntGenPie.selectPntGenerica(myQueryPie, pool);
                String[][] tablaPie = pntGenPie.getArray();
                //Insertamos el texto creado y si trabajamos
                //en Windows terminaremos cada linea con "\r\n", recorriendo la consulta sql
                for (int i = 0; i < rows; i++) {
                    out.write(tabla[i][0] + "\r\n");

                } // fin del loop for
                out.write(tablaPie[0][0] + "\r\n");

            } else { //De lo contrario solo detalle...............
                //Insertamos el texto creado y si trabajamos
                //en Windows terminaremos cada linea con "\r\n", recorriendo la consulta sql
                for (int i = 0; i < rows; i++) {
                    fichero.write(tabla[i][0] + "\r\n");
                } // Fin del loop for
            }
            //cerramos el fichero
            out.close();
            fichero.close();
            //System.out.println("Archivo generado con exito en: " + extContext.getRealPath(RUTA_EXTERNO) + ". " + rows + " registros");
            if(rows==0){
            	msj = "No se generó ningún archivo, no existen registros en la consulta" ;
                claseResult = "error";
                detalle = "";	
            } else {
                msj = "Archivo generado con exito. "  + rows + " registros, pulse el botón descargar para llevarlos a su equipo" ;
                claseResult = "exito";
                detalle = procSelected+anio+mescal + ".txt";
            }
        } catch (Exception e) {
            //System.out.println("No se pudo generar el archivo de texto, no existe la ruta");
            msj = "No se pudo generar el archivo de texto, no existe la ruta";
            claseResult = "error";
        }
    	}//Fin valida clave
    	}//Fin de valida
    }
    
	   
	   /**
		 * Metodo de captura de mensajes, sera utilizado para la impresion de
		 * errores
		 **/
		public String getMsj() {
			final Timer tmr = new Timer();
			tmr.schedule(new TimerTask() {

				@Override
				public void run() {
					msj = "";
					tmr.cancel();
				}
			}, 150);
	        
			return msj;
		}

		/**
		 * Metodo que dara tipo de clase a la hora de mostrar un resultado en un
		 * insert, update o delete
		 **/
		public String getClaseResult() {
			
			final Timer tmr = new Timer();
			tmr.schedule(new TimerTask() {

				@Override
				public void run() {
					claseResult = "";
					tmr.cancel();
				}
			}, 150);
			
			return claseResult;

		}
		
//////////////////////////////////////////////////////////////LISTA//////////////////////////////////////////////////////////////////////
		//Selecciona las opciones de idioma en el login. Para manejo de idiomas
		 private SelectItem[] procesos = new SelectItem[]{
				    new SelectItem("N", "NOMINA"),
			        new SelectItem("V", "VACACIONES"),
			        new SelectItem("P", "PRESTACIONES"),
				    new SelectItem("I", "INTERESES"),
				    new SelectItem("PR", "PRESTAMOS ACTIVOS"),
				    new SelectItem("U", "UTILIDADES")};

		 private String procSelected = "N";


		    public SelectItem[] getProcesos() {
		        return procesos;
		    }
		    
	        //Toma el valor del idioma
			public String getProcSelected() {
				return procSelected;
			}

			public void setProcSelected(String procSelected) {
				this.procSelected = procSelected;
			}
			
//////////////////////////////////////////////////////////LECTURA DE USUARIO DESDE XML/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			/**
		     * Lee Archivo desde un XML. 
		     * <p>
		     * Parámetros del Método: Ruta del XML.
		     * @return Usuario
		     **/
		    @SuppressWarnings("rawtypes")
			protected
			static String readUser(String ruta) throws  IOException{
		     String indicator = "";
		     FileReader fr = new FileReader(ruta + File.separator + "segusr.xml");
		     BufferedReader bf = new BufferedReader(fr);

		     //A partir de aquí trabajamos con el JDOM para lectura del xml
		     try {
		        SAXBuilder builder=new SAXBuilder(false);
		        //usar el parser Xerces y no queremos
		        //que valide el documento
		        //Abre el documento, el método build que lee de un InputStreamReader
		        Document doc=builder.build(bf);
		        //construyo el arbol en memoria desde el fichero
		        // que se lo pasaré por parametro.
		        Element raiz=doc.getRootElement();
		        //cojo el elemento raiz
		        //todos los hijos que tengan como nombre plantilla
		        List load=raiz.getChildren("load");
		        Iterator i = load.iterator();
		        //
		        while (i.hasNext()){
		            Element e= (Element)i.next();
		            //primer hijo que tenga como nombre club
		            Element act =e.getChild("user");
		           // System.out.println( "Esta activa (0 - Activa, 1 - Inactiva): " + act.getText() );
		            //Convierto a String el elemento
		            String parseAct = act.getText();
		            //Convierto en integer el parse para retornar int
		            indicator = parseAct;
		        }
		        // Dejamos de mano del lector el sacar el nombre
		        //de los arbitros, animate!!
		     }catch (Exception e){
		        e.printStackTrace();
		     }
		     return indicator;
		}	
		    
		    /**
		     * Lee Archivo desde un XML. 
		     * <p>
		     * Parámetros del Método: Ruta del XML.
		     * @return Clave
		     **/
		    @SuppressWarnings("rawtypes")
			protected
			static String readPass(String ruta) throws  IOException{
		     String indicator = "";
		     FileReader fr = new FileReader(ruta + File.separator + "segusr.xml");
		     BufferedReader bf = new BufferedReader(fr);

		     //A partir de aquí trabajamos con el JDOM para lectura del xml
		     try {
		        SAXBuilder builder=new SAXBuilder(false);
		        //usar el parser Xerces y no queremos
		        //que valide el documento
		        //Abre el documento, el método build que lee de un InputStreamReader
		        Document doc=builder.build(bf);
		        //construyo el arbol en memoria desde el fichero
		        // que se lo pasaré por parametro.
		        Element raiz=doc.getRootElement();
		        //cojo el elemento raiz
		        //todos los hijos que tengan como nombre plantilla
		        List load=raiz.getChildren("load");
		        Iterator i = load.iterator();
		        //
		        while (i.hasNext()){
		            Element e= (Element)i.next();
		            //primer hijo que tenga como nombre club
		            Element act =e.getChild("pass");
		           // System.out.println( "Esta activa (0 - Activa, 1 - Inactiva): " + act.getText() );
		            //Convierto a String el elemento
		            String parseAct = act.getText();
		            //Convierto en integer el parse para retornar int
		            indicator = parseAct;
		        }
		        // Dejamos de mano del lector el sacar el nombre
		        //de los arbitros, animate!!
		     }catch (Exception e){
		        e.printStackTrace();
		     }
		     return indicator;
		}	
		    
		    
		    /**
		     * Valida que no existan campos nulos en el formulario
		     * @return Boolean
		     **/
		    private Boolean valida(){
		    	Boolean retorno = true;
		    	//Chequea que las variables no sean nulas 
		    	if (anio.equals("") || mescal.equals("") || usuario.equals("") || clave.equals("")){
		    		retorno = false;
		    	}
		    	return retorno;
		    }
		    
		    
		    /**
		     * Valida que no existan campos nulos en el formulario
		     * @return Boolean
		     **/
		    private Boolean valida1(){
		    	Boolean retorno = true;
		    	//Chequea que las variables no sean nulas 
		    	if (usuario.equals("") || clave.equals("")){
		    		retorno = false;
		    	}
		    	return retorno;
		    }
/////////////////////////////////////////////////////////INTEGRACION SPI ORACLE ESTEE LAUDER///////////////////////////////////////////////////////////////
	public void data_oracle() throws NamingException, IOException {
		//Valida que no existan campos nulos
    	if(valida1()){//No hace nada si son nulos
    	//Valida que el usuario y la clave coincidan
    	if(!usuario.equals(readUser(extContext.getRealPath(RUTA_EXTERNO)))){
    		msj = "Usuario inválido";
    		claseResult = "error";
    		//System.out.println("Usuario inválido");
    	} else if (!clave.equals(readPass(extContext.getRealPath(RUTA_EXTERNO)))){
    		msj = "Contraseña inválida";
    		claseResult = "error";
    		//System.out.println("Contraseña inválida");
    	} else {
		try {
			con = ds.getConnection();
			CallableStatement proc = null;
			// Class.forName(getDriver());
			// con = DriverManager.getConnection(
			// getUrl(), getUsuario(), getClave());
			try {
				// Avisando
				proc = con.prepareCall("{CALL ACT_DATA_ORACLE}");
				proc.execute();
				claseResult = "exito";
				msj = "Inserción o actualización de registros ejecutada con éxito";
			} catch (SQLException e) {
				e.printStackTrace();
				msj = e.getMessage();
				claseResult = "error";
			}
			//
			proc.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    	}
    	} else {
    		msj = "Debe colocar usuario y contraseña para ejecutar la integración";
    		claseResult = "error";
    	}
	}



	public List<Listdataoracle> select() throws SQLException {

		con = ds.getConnection();

		// Consulta paginada
		String query = "  select cia_codcia, tnom_tipnom, fictra, apell1, nombr1, decode(FLG_TIPO_PROCESO,'N','NUEVO INGRESO','ACTUALIZACION')";
		query += " from data_oracle";
		query += " WHERE FLG_PROCESADO IS NULL";
		pstmt = con.prepareStatement(query);
		// System.out.println(query);

		ResultSet r = pstmt.executeQuery();

		List<Listdataoracle> list = new ArrayList<Listdataoracle>();

		while (r.next()) {
			Listdataoracle select = new Listdataoracle();
			select.setCodcia(r.getString(1));
			select.setTipnom(r.getString(2));
			select.setFictra(r.getString(3));
			select.setApell1(r.getString(4));
			select.setNombr1(r.getString(5));
			select.setProcesar(r.getString(6));
			// Agrega la lista
			list.add(select);
			// rows = list.size();
		}
		// Cierra las conecciones
		pstmt.close();
		con.close();

		return list;
	}
		    
///////////////////////////////////////////////////////////DOWNLOAD//////////////////////////////////////////
		    
		    private StreamedContent file;
private String imescal;  
		    
		    public StreamedContent getFile() throws FileNotFoundException {  
		        InputStream stream = new FileInputStream(extContext.getRealPath(RUTA_EXTERNO) + File.separator + procSelected+anio+mescal + ".txt");  
		        file = new DefaultStreamedContent(stream, "application/txt", procSelected+anio+mescal + ".txt");  
		  
		        return file;  
		    }  
		  
		    public void setFile(StreamedContent file) {  
		        this.file = file;  
		    }

	  
		   }


