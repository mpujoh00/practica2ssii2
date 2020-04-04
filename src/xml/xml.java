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
import org.apache.poi.ss.usermodel.Cell;
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
    
    String rutaArchivo = "/resources/SistemasInformacionII.xlsx";
    XSSFWorkbook excel;
    XSSFSheet hoja;
    
    public xml() {
        
        //ArrayList duplicados = new ArrayList();
        ArrayList idFila = new ArrayList();
        ArrayList nombre = new ArrayList();
        ArrayList primerApellido = new ArrayList();
        ArrayList segundoApellido = new ArrayList();
        ArrayList empresa = new ArrayList();
        ArrayList categoria = new ArrayList();
        
        try {
            FileInputStream archivo = new FileInputStream(new File(rutaArchivo));
            excel = new XSSFWorkbook(archivo);
            
        } catch (Exception e) {}
            
            hoja = excel.getSheetAt(0);
            
            int numeroFilas = hoja.getLastRowNum();
            boolean blanco = false;
            
            String[] nif = new String[numeroFilas+1];
            
            //Busca las celdas en blanco
            for(int i = 0; i < numeroFilas; i++){
                
                Row fila = hoja.getRow(i);
                Cell celda = fila.getCell(7);
                
                if(celda == null){
     
                    idFila.add(Integer.toString(i+1));
                    nombre.add(fila.getCell(4).getStringCellValue());
                    primerApellido.add(fila.getCell(5).getStringCellValue());
                    segundoApellido.add(fila.getCell(6).getStringCellValue());
                    empresa.add(fila.getCell(1).getStringCellValue());
                    categoria.add(fila.getCell(2).getStringCellValue());
                    
                    blanco = true;
                    
                } else {
                    
                    nif[i] = celda.getStringCellValue();
                }
            }
            
            //Busca duplicados
            for(int i = 0; i < numeroFilas; i++) {
                for(int j = 0; j < numeroFilas; j++){
                    
                    if(i != j && nif[i].equals(nif[j]) && !blanco){
                        //duplicados.add(nif[i]);
                        idFila.add(Integer.toString(i+1));
                        
                        Row fila = hoja.getRow(i);
                        nombre.add(fila.getCell(4).getStringCellValue());
                        primerApellido.add(fila.getCell(5).getStringCellValue());
                        segundoApellido.add(fila.getCell(6).getStringCellValue());
                        empresa.add(fila.getCell(1).getStringCellValue());
                        categoria.add(fila.getCell(2).getStringCellValue());
                    }
                }
            }
            
            creaFicheroErrores(idFila, nombre, primerApellido, segundoApellido, empresa, categoria);
       
    }
    
    public static void creaFicheroErrores(ArrayList idFila, ArrayList nombre, ArrayList primerApellido, ArrayList segundoApellido, ArrayList empresa, ArrayList categoria) {
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //Elemento raíz
            Document documento = docBuilder.newDocument();
            
            Element raiz = documento.createElement("trabajadores");
            
            //Primer elemento
            Element trabajador = documento.createElement("trabajador");
            raiz.appendChild(trabajador);
            
            Attr id = documento.createAttribute("id");
            id.setValue("Trabajador" + idFila);
            trabajador.setAttributeNode(id);
            
            Attr nom = documento.createAttribute("nombre");
            nom.setValue("Nombre" + nombre);
            trabajador.setAttributeNode(nom);
            
            Attr apellido1 = documento.createAttribute("apellido1");
            apellido1.setValue("Primer apellido" + primerApellido);
            trabajador.setAttributeNode(apellido1);
            
            Attr apellido2 = documento.createAttribute("apellido2");
            apellido2.setValue("Segundo apellido" + segundoApellido);
            trabajador.setAttributeNode(apellido2);
            
            Attr emp = documento.createAttribute("empresa");
            emp.setValue("Empresa" + empresa);
            trabajador.setAttributeNode(emp);
            
            Attr cat = documento.createAttribute("categoria");
            emp.setValue("Categoria" + categoria);
            trabajador.setAttributeNode(cat);
            
            
            //Se escribe el contenido del xml en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File("/resources/Errores.xml"));
            
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
