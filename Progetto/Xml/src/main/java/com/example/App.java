package com.example;
import java.io.File;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
        System.out.println("------------");
        Classe c1 = new Classe("pippo");
        XmlMapper xmlMapper = new XmlMapper();
        
        // Serializzazione        
        xmlMapper.writeValue(new File("test.xml"), c1);
        String myXml = xmlMapper.writeValueAsString(c1);
        System.out.println("Oggetto serializzato:" + myXml);
        
        // Deserializzazione        
        Classe c2 = xmlMapper.readValue(myXml, Classe.class);
        System.out.println("Oggetto deserializzato da stringa:" + c2.getNome());

        Classe c3 = xmlMapper.readValue(new File("test.xml"), Classe.class);
        System.out.println("Oggetto deserializzato da file: " + c3.getNome());
    }


}
