/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import excel.excel;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Micaela Pujol Higueras
 * @author Silvia Matilla García
 */
public class xml {
    
    String rutaArchivo = "./src/resources/SistemasInformacionII.xlsx";
    XSSFWorkbook excel;
    XSSFSheet hoja;
    excel exc;
    
    public xml() {
        
        exc = new excel();
        excel = exc.getExcel();
        
    }
    
    public void creaFicheroErrores() {
        
        try {
            ArrayList<ArrayList<String>> trabajadores = new ArrayList(); // un arraylist para cada trabajador
        
            // coge la hoja de trabajadores
            hoja = excel.getSheetAt(0);
            
            ArrayList<ArrayList<String>> nifs = new ArrayList(); // mejor que un string (no sabemos la longitud)
            
            //Busca las celdas en blanco 
            for(int i = 1; i < hoja.getLastRowNum(); i++){ // empieza en la 2a fila
                
                Row fila = hoja.getRow(i);
                Cell celda = fila.getCell(7); // selecciona la casilla correspondiente al NIF/NIE
                                
                if((celda == null || celda.getCellType() == CellType.BLANK || StringUtils.isBlank(celda.toString())) && !exc.filaVacia(fila)){
     
                    trabajadores.add(new ArrayList());
                    
                    trabajadores.get(trabajadores.size()-1).add(Integer.toString(i+1));
                    
                    Cell aux = fila.getCell(4); // Nombre
                    
                    if(aux != null){
                        trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        trabajadores.get(trabajadores.size()-1).add("");
                    }
                    
                    aux = fila.getCell(5); // Apellido 1
                    
                    if(aux != null){
                        trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        trabajadores.get(trabajadores.size()-1).add("");
                    }
                    
                    aux = fila.getCell(6); // Apellido 2
                    
                    if(aux != null){
                        trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        trabajadores.get(trabajadores.size()-1).add("");
                    }
                    
                    aux = fila.getCell(1); // Empresa
                    
                    if(aux != null){
                        trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        trabajadores.get(trabajadores.size()-1).add("");
                    }
                    
                    aux = fila.getCell(2); // Categoría
                    
                    if(aux != null){
                        trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        trabajadores.get(trabajadores.size()-1).add("");
                    }
                    
                } else if(!exc.filaVacia(fila)){
                    
                    nifs.add(new ArrayList());
                    nifs.get(nifs.size()-1).add(celda.getStringCellValue());
                    nifs.get(nifs.size()-1).add(Integer.toString(i+1));
                    nifs.get(nifs.size()-1).add("");
                }
            }
            
            //Busca duplicados
            for(int i = 0; i < nifs.size()-1; i++) {
                for(int j = 0; j < nifs.size()-1; j++){
                    
                    if(i != j && nifs.get(i).get(0).equals(nifs.get(j).get(0)) && nifs.get(i).get(2).equals("duplicado") && nifs.get(j).get(2).equals("")){
                        
                        nifs.get(j).set(2, "duplicado");
                        
                    }else if(i != j && nifs.get(i).get(0).equals(nifs.get(j).get(0)) && nifs.get(i).get(2).equals("") && nifs.get(j).get(2).equals("")){ //  && !blanco
                                                
                        Row fila = hoja.getRow(Integer.parseInt(nifs.get(i).get(1))-1);
                        
                        trabajadores.add(new ArrayList());

                        trabajadores.get(trabajadores.size()-1).add(nifs.get(i).get(1));

                        Cell aux = fila.getCell(4); // Nombre

                        if(aux != null){
                            trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            trabajadores.get(trabajadores.size()-1).add("");
                        }

                        aux = fila.getCell(5); // Apellido 1

                        if(aux != null){
                            trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            trabajadores.get(trabajadores.size()-1).add("");
                        }

                        aux = fila.getCell(6); // Apellido 2

                        if(aux != null){
                            trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            trabajadores.get(trabajadores.size()-1).add("");
                        }

                        aux = fila.getCell(1); // Empresa

                        if(aux != null){
                            trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            trabajadores.get(trabajadores.size()-1).add("");
                        }

                        aux = fila.getCell(2); // Categoría

                        if(aux != null){
                            trabajadores.get(trabajadores.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            trabajadores.get(trabajadores.size()-1).add("");
                        }
                        
                        nifs.get(i).set(2, "duplicado");
                        nifs.get(j).set(2, "duplicado");
                    }
                }
            }
            
            // crea el archivo xml
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document documento = docBuilder.newDocument();
            
            //Elemento raíz
            Element raiz = documento.createElement("trabajadores");
            documento.appendChild(raiz);
            
            // crea los elementos trabajadores
            for(ArrayList<String> t: trabajadores){
                
                Element trabajador = documento.createElement("trabajador");
                raiz.appendChild(trabajador);

                Attr id = documento.createAttribute("id");
                id.setValue(t.get(0));
                trabajador.setAttributeNode(id);

                Element nombre = documento.createElement("nombre");
                nombre.appendChild(documento.createTextNode(t.get(1)));
                trabajador.appendChild(nombre);
                
                String apell = "";
                if(!t.get(2).equals("") && t.get(3).equals("")){
                    apell = t.get(2);
                }
                else if(t.get(2).equals("") && !t.get(3).equals("")){
                    apell = t.get(3);
                }
                else if(!t.get(2).equals("") && !t.get(3).equals("")){
                    apell = t.get(2) + " " + t.get(3);
                }
                
                Element apellidos = documento.createElement("apellidos");
                apellidos.appendChild(documento.createTextNode(apell));
                trabajador.appendChild(apellidos);
                
                Element empresa = documento.createElement("empresa");
                empresa.appendChild(documento.createTextNode(t.get(4)));
                trabajador.appendChild(empresa);
                
                Element categoria = documento.createElement("categoria");
                categoria.appendChild(documento.createTextNode(t.get(5)));
                trabajador.appendChild(categoria);
            }
            
            //Se escribe el contenido del xml en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File("./src/resources/Errores.xml"));    
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
    
    public void creaFicheroErroresCCC() {
        
        try {
            ArrayList<ArrayList<String>> cuentas = new ArrayList();
        
            hoja = excel.getSheetAt(0);
            
            ArrayList<ArrayList<String>> cccs = new ArrayList();
            
            //Busca las celdas en blanco 
            for(int i = 1; i < hoja.getLastRowNum(); i++){
                
                Row fila = hoja.getRow(i);
                Cell celda = fila.getCell(9); // selecciona la casilla correspondiente al ccc
                                
                if((celda == null || celda.getCellType() == CellType.BLANK || StringUtils.isBlank(celda.toString())) && !exc.filaVacia(fila)){
     
                    cuentas.add(new ArrayList());
                    
                    cuentas.get(cuentas.size()-1).add(Integer.toString(i+1));
                    
                    Cell aux = fila.getCell(4); //Nombre
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                    aux = fila.getCell(5); //Apellido 1
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                    aux = fila.getCell(6); //Apellido 2
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                    aux = fila.getCell(1); //Empresa
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                    aux = fila.getCell(9); //CCC
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                    aux = fila.getCell(11); //IBAN
                    
                    if(aux != null){
                        cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                    }
                    else{
                        cuentas.get(cuentas.size()-1).add("");
                    }
                    
                } else if(!exc.filaVacia(fila)){
                    
                    cccs.add(new ArrayList());
                    cccs.get(cccs.size()-1).add(celda.getStringCellValue());
                    cccs.get(cccs.size()-1).add(Integer.toString(i+1));
                    cccs.get(cccs.size()-1).add("");
                }
            }
            
            //Busca duplicados
            for(int i = 0; i < cccs.size()-1; i++) {
                for(int j = 0; j < cccs.size()-1; j++){
                    
                    if(i != j && cccs.get(i).get(0).equals(cccs.get(j).get(0)) && cccs.get(i).get(2).equals("duplicado") && cccs.get(j).get(2).equals("")){
                        
                        cccs.get(j).set(2, "duplicado");
                        
                    }else if(i != j && cccs.get(i).get(0).equals(cccs.get(j).get(0)) && cccs.get(i).get(2).equals("") && cccs.get(j).get(2).equals("")){
                                                
                        Row fila = hoja.getRow(Integer.parseInt(cccs.get(i).get(1))-1);
                        
                        cuentas.add(new ArrayList());

                        cuentas.get(cuentas.size()-1).add(cccs.get(i).get(1));

                        Cell aux = fila.getCell(4); //Nombre

                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }

                        aux = fila.getCell(5); //Apellido 1

                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }

                        aux = fila.getCell(6); //Apellido 2

                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }

                        aux = fila.getCell(1); //Empresa

                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }

                        aux = fila.getCell(9); //CCC

                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }
                        
                        aux = fila.getCell(11); //IBAN
                        
                        if(aux != null){
                            cuentas.get(cuentas.size()-1).add(aux.getStringCellValue());
                        }
                        else{
                            cuentas.get(cuentas.size()-1).add("");
                        }
                        
                        cccs.get(i).set(2, "duplicado");
                        cccs.get(j).set(2, "duplicado");
                    }
                }
            }
            
            //Crea el archivo xml
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document documento = docBuilder.newDocument();
            
            //Elemento raíz
            Element raiz = documento.createElement("cuentas");
            documento.appendChild(raiz);
            
            // crea los elementos trabajadores
            for(ArrayList<String> t: cuentas){
                
                Element cuenta = documento.createElement("cuenta");
                raiz.appendChild(cuenta);

                Attr id = documento.createAttribute("id");
                id.setValue(t.get(0));
                cuenta.setAttributeNode(id);

                Element nombre = documento.createElement("nombre");
                nombre.appendChild(documento.createTextNode(t.get(1)));
                cuenta.appendChild(nombre);
                
                String apellido = "";
                if(!t.get(2).equals("") && t.get(3).equals("")){
                    apellido = t.get(2);
                }
                else if(t.get(2).equals("") && !t.get(3).equals("")){
                    apellido = t.get(3);
                }
                else if(!t.get(2).equals("") && !t.get(3).equals("")){
                    apellido = t.get(2) + " " + t.get(3);
                }
                
                Element apellidos = documento.createElement("apellidos");
                apellidos.appendChild(documento.createTextNode(apellido));
                cuenta.appendChild(apellidos);
                
                Element empresa = documento.createElement("empresa");
                empresa.appendChild(documento.createTextNode(t.get(4)));
                cuenta.appendChild(empresa);
                
                Element ccc = documento.createElement("ccc");
                ccc.appendChild(documento.createTextNode(t.get(9)));
                cuenta.appendChild(ccc);
                
                Element iban = documento.createElement("IBAN");
                iban.appendChild(documento.createTextNode(t.get(11)));
                cuenta.appendChild(iban);
            }
            
            //Se escribe el contenido del xml en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File("./src/resources/ErroresCCC.xml"));    
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
