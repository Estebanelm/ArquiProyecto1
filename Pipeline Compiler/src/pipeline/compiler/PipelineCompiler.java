/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline.compiler;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Pelo
 */
public class PipelineCompiler {
        
        private static String agregarCeros(String dato, int largo)
        {
            for (int i = dato.length(); i < largo; i++)
                {
                    dato = "0".concat(dato);
                }
            return dato;
        }
    
        public static void main( String args[] ) throws IOException {
            
            String fileString = "C:\\Users\\Pelo\\Google Drive\\2017 Semestre 2\\Arquitectura de Computadoras 1\\Proyectos\\Repositorio Proyecto 1\\Pipeline Compiler\\Ejemplos\\assembly.asm";
            FileReader file = new FileReader(fileString);
            String codeString = "C:\\Users\\Pelo\\Google Drive\\2017 Semestre 2\\Arquitectura de Computadoras 1\\Proyectos\\Repositorio Proyecto 1\\Pipeline Compiler\\Ejemplos\\code.mcn";
            FileWriter codedFile = new FileWriter(codeString);
            BufferedReader br1 = new BufferedReader(file);
            String line = null;
            Map<String, Integer> tags = new HashMap<String, Integer>();
            int contadorTags = 0;
            while ((line = br1.readLine()) != null)
            {
                String pattern = "[a-zA-Z]+:";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(line);
                if (m.find())
                {
                    tags.put(line.split(":")[0], contadorTags);
                }
                contadorTags++;
            }
            br1.close();
            BufferedReader br2 = new BufferedReader(new FileReader(fileString));
            while ((line = br2.readLine()) != null)
            {
                String codedInstruction = "";
                String[] instruction1 = line.split(" ");
                if (instruction1[0].equals("lb"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[3])),4);
                   codedInstruction = codedInstruction.concat("1000").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
                   codedFile.write(codedInstruction);
                }
                if (instruction1[0].equals("add"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[3])),4);
                   codedInstruction = codedInstruction.concat("0001").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
                   codedFile.write(codedInstruction);
                }
                if (instruction1[0].equals("sub"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[3])),4);
                   codedInstruction = codedInstruction.concat("0010").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
                   codedFile.write(codedInstruction);
                }
                if (instruction1[0].equals("mul"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[3])),4);
                   codedInstruction = codedInstruction.concat("0011").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
                   codedFile.write(codedInstruction);
                }
                if (instruction1[0].equals("bne"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(tags.get(instruction1[3])),7);
                   codedInstruction = codedInstruction.concat("0100").concat(dato1).concat(dato2).concat(dato3).concat("\n");
                   codedFile.write(codedInstruction);
                }
                if (instruction1[0].equals("addi"))
                {
                   String dato1 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[1])),4);
                   String dato2 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   String dato3 = agregarCeros(Integer.toBinaryString(Integer.parseInt(instruction1[2])),4);
                   codedInstruction = codedInstruction.concat("1010").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
                   codedFile.write(codedInstruction);
                }
            }
            codedFile.close();

          

/*
// String to be scanned to find the pattern.
      String line = "ADD -1 2 4"
              +"\nMOV 1 2";
      String pattern = "ADD -?[0-9]+ -?[0-9]+ -?[0-9]+";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);

      // Now create matcher object.
      Matcher m = r.matcher(line);
      if (m.find( )) {
         System.out.println("Found value: " + m.group(0) );
         String reemplazado = m.replaceAll("1001");
         System.out.println(reemplazado);
      }else {
         System.out.println("NO MATCH");
      }
*/
   } 
}
