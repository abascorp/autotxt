/*
 *  Copyright (C) 2011  ANDRES DOMINGUEZ, CHRISTIAN DÍAZ

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los términos de la Licencia Pública General GNU publicada 
    por la Fundación para el Software Libre, ya sea la versión 3 
    de la Licencia, o (a su elección) cualquier versión posterior.

    Este programa se distribuye con la esperanza de que sea útil, pero 
    SIN GARANTÍA ALGUNA; ni siquiera la garantía implícita 
    MERCANTIL o de APTITUD PARA UN PROPÓSITO DETERMINADO. 
    Consulte los detalles de la Licencia Pública General GNU para obtener 
    una información más detallada. 

    Debería haber recibido una copia de la Licencia Pública General GNU 
    junto a este programa. 
    En caso contrario, consulte <http://www.gnu.org/licenses/>.
 */


function proc(){
	if(document.getElementById("formGentxt:anio").value==""){
		document.getElementById("jsmsj").innerHTML= "Debe seleccionar año";
        document.getElementById("jsmsj").className = 'error';
        document.getElementById("formGentxt:anio").focus();
    }else if(document.getElementById("formGentxt:mes").value==""){
        document.getElementById("jsmsj").innerHTML= "Debe selecionar mes";
        document.getElementById("jsmsj").className = 'error';
        document.getElementById("formGentxt:mes").focus();
    }else if(document.getElementById("formGentxt:usuario").value==""){
        document.getElementById("jsmsj").innerHTML= "Debe selecionar usuario";
        document.getElementById("jsmsj").className = 'error';
        document.getElementById("formGentxt:usuario").focus();     
    }else if(document.getElementById("formGentxt:clave").value==""){
        document.getElementById("jsmsj").innerHTML= "Debe selecionar clave";
        document.getElementById("jsmsj").className = 'error';
        document.getElementById("formGentxt:clave").focus();   
    }else{
    	// Limpiamos valores   
    	document.getElementById("jsmsj").innerHTML= "";
        document.getElementById("jsmsj").className = "";
    } 
}



function limpiar()
{  //Llamado por el boton limpiar
	document.getElementById("formGentxt:anio").value = "";
	document.getElementById("formGentxt:mes").value = "";
	document.getElementById("formGentxt:usuario").value = "";
	document.getElementById("formGentxt:clave").value = "";
    document.getElementById("jsmsj").innerHTML = "";
    document.getElementById("jsmsj").className = "";
    document.getElementById("formGentxt:mensaje").innerHTML = "";
    document.getElementById("formGentxt:mensaje").className = "";
    document.getElementById('upload').style.display='none';
}


function mostrarForm(){
    jQuery(document).ready(function(){
    	jQuery(document).ready(function () {
    		jQuery('#upload').fadeIn(1200);
        });
    });
    document.getElementById("formGentxt:anio").value = "";
	document.getElementById("formGentxt:mes").value = "";
	document.getElementById("formGentxt:usuario").value = "";
	document.getElementById("formGentxt:clave").value = "";
    document.getElementById("jsmsj").innerHTML = "";
    document.getElementById("jsmsj").className = "";

}

