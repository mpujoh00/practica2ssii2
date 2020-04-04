/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import excel.excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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
    
    public xml() {
        
        excel exc = new excel();
        excel = exc.getExcel();
        
        ArrayList<ArrayList<String>> trabajadores = new ArrayList(); // un arraylist para cada trabajador
        
        try {
            // coge la hoja de trabajadores
            hoja = excel.getSheetAt(0);
            
            int numeroFilas = hoja.getLastRowNum();
            ArrayList<ArrayList<String>> nifs = new ArrayList(); // mejor que un string (no sabemos la longitud)
            
            // igual habría que eliminar las filas en blanco
            //Busca las celdas en blanco 
            for(int i = 1; i < numeroFilas; i++){ // empieza en la 2a fila
                
                Row fila = hoja.getRow(i);
                Cell celda = fila.getCell(7); // selecciona la casilla correspondiente al NIF/NIE
                                
                if((celda == null || celda.getCellType() == CellType.BLANK || StringUtils.isBlank(celda.toString())) && !filaVacia(fila)){
     
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
                    
                } else if(!filaVacia(fila)){
                    
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
            
            creaFicheroErrores(trabajadores);
       
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        System.out.println("terminao.");
    }
    
    public static void creaFicheroErrores(ArrayList<ArrayList<String>> trabajadores) {
        
        try {
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

    private static boolean filaVacia(Row fila){
        
        if (fila == null) {
            return true;
        }
        if (fila.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = fila.getFirstCellNum(); cellNum < fila.getLastCellNum(); cellNum++) {
            Cell celda = fila.getCell(cellNum);
            if (celda != null && celda.getCellType() != CellType.BLANK && StringUtils.isNotBlank(celda.toString())) {
                return false;
            }
        }
        return true;
    }
}
