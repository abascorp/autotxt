/*
 *  Copyright (C) 2011  ANDRES DOMINGUEZ, CHRISTIAN DiAZ

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los terminos de la Licencia Pública General GNU publicada 
    por la Fundacion para el Software Libre, ya sea la version 3 
    de la Licencia, o (a su eleccion) cualquier version posterior.

    Este programa se distribuye con la esperanza de que sea útil, pero 
    SIN GARANTiA ALGUNA; ni siquiera la garantia implicita 
    MERCANTIL o de APTITUD PARA UN PROPoSITO DETERMINADO. 
    Consulte los detalles de la Licencia Pública General GNU para obtener 
    una informacion mas detallada. 

    Deberia haber recibido una copia de la Licencia Pública General GNU 
    junto a este programa. 
    En caso contrario, consulte <http://www.gnu.org/licenses/>.
 */

package org.autotxt.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 * Crea Usuarios y clave de Base de Datos para todos los programas
 */
@ManagedBean
@RequestScoped
public class Utils {
    // Constructor

    public Utils() {

    }
    //Declaracion de variables para manejo de mensajes multi idioma y pais
    private String lenguaje = "es";
    private String pais = "VEN";
    private Locale  localidad = new Locale(lenguaje, pais);
    //ResourceBundle recursos =  ResourceBundle.getBundle("org.opennomina.util.MessagesBundle",localidad);
    @SuppressWarnings("unused")
	private Locale OsLang = Locale.getDefault();

    
      /**
     * Recursos de lenguaje. Archivos Properties
     **/
   // public String getMessage(String mensaje) {
   //     Message = recursos.getString(mensaje);
   //     return Message;
    //}



    //Variables para coneccion con BD
    private String user = "infocent";
    private String pass = "infocent";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String driver = "oracle.jdbc.driver.OracleDriver"; //Driver
    private static final String JNDI = "jdbc/opennomina"; //Nombre del JNDI

    //Declaracion de variables y manejo de mensajes
    private int session = 900; //Reflejado en segundos. Son quince minutos 15*60=900
    private int tBlanq = 5000; //Segundos para eliminar mensajes(Segundos)
    String userLang = System.getProperty("user.language");//Identifica el lenguaje el OS
    String userCountry = System.getProperty("user.country");//Identifica el pais el OS
    Locale locale = new Locale(userLang, userCountry);//Identifica idioma y pais, por defecto le colocamos ven
    
    java.util.Date fecact = new java.util.Date();
    //Fecha para todas las pistas de auditoria
    java.text.SimpleDateFormat sdfecha = new java.text.SimpleDateFormat("dd/MMM/yyyy", locale );
    //Formato fecha para insertar aquellas tablas que tienen fechas
    java.text.SimpleDateFormat sdfDefautl = new java.text.SimpleDateFormat("dd/MMM/yyyy");

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, d MMM yyyy", localidad);
    String fechaTop = sdf.format(fecact);//Fecha a mostrar en top
    String fecha = sdfecha.format(fecact); //Fecha formateada para insertar en tablas


    public String getPass() {
        return pass;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }
    
    public int getSession() {
        return session;
    }
    
 /**
* Crea driver unico para todas las conecciones. De ser necesario cambiarlo, no
* hace falta recurrir a todos los metodos.
*/
    public String getDriver() {
        return driver;
    }


    public int gettBlanq() {
        return tBlanq;
    }
 
     /**
     * Retorna el nombre de la conexion JNDI
     **/
    public String getJNDI() {
        return JNDI;
    }

    public String getFechaTop() {
        return fechaTop;
    }

/**
     * Obtiene la fecha del dia, ya que se va a utilizar en todas la tablas
     * se crea el metodo.
     */
    public String getFecha() {

        return fecha;
    }
    
    /**
     * Valida que el valor en el parámetro sea entero.
     * Retorna cero(0) si es numérico, retorna (1) si no lo és
     * @return Integer
     */
    public int IsInt (String Valor){
     try {
      Integer.parseInt(Valor);
    	return 0;
    	} catch (Exception e) {
    	return 1;
    	}
    }
    
    /**
     * Valida que el valor en el parámetro sea numérico.
     * Retorna cero(0) si es numérico, retorna (1) si no lo és
     * @return Integer
     */
    public int IsFloat (String Valor){
     try {
      Float.parseFloat(Valor);
    	return 0;
    	} catch (Exception e) {
    	return 1;
    	}
    }
    
    
    public int isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            //System.out.println("[" + mat.group() + "]");
            return 0;
        }else{
            return 1;
        }        
    }
    
    
   
 
// public static void main (String [] args) {
//Utils a = new Utils();
//      System.out.println(a.IsInt("p") );
  //Bd.reActivaLic("c/temp");
  //System.out.println(Bd.getMsjLic());
  //System.out.println(Bd.getClaseResultLic());
 //System.out.println(Bd.getFecha());
 //}

}
