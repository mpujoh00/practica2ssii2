/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author maybeitsmica
 */
public class excel {
    
    String rutaArchivo = "./src/resources/SistemasInformacionII.xlsx";
    XSSFWorkbook excel;
    
    public excel(){
        
        try{ // leo el archivo excel
            FileInputStream archivo = new FileInputStream(new File(rutaArchivo));
            excel = new XSSFWorkbook(archivo);
               
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCategorias(){
        
        XSSFSheet hoja = excel.getSheetAt(1);
        
        Iterator<Row> iteradorFilas = hoja.iterator();
        
        Row fila;
        
        iteradorFilas.next(); // empieza en la segunda fila
        
        while(iteradorFilas.hasNext()){
            
            fila = iteradorFilas.next();
        }
    }
    
    public XSSFWorkbook getExcel(){
        
        return this.excel;
    }
}
