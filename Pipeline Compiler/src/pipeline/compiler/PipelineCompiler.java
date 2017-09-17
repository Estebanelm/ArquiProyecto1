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
import javafx.util.Pair;
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
    
    private static String agregarNops(int cantidadNops)
    {
        String stringConNops = "";
        for (int i = 0; i < cantidadNops; i++) 
        {
            stringConNops = stringConNops.concat("0000000000000000000\n");
        }
        return stringConNops;
    }
    
    private static int contarNops(int[] array, int registroAfectado1, int registroAfectado2)
    {
        String stringConNops1 = "";
        String stringConNops2 = "";
        int contador1 = 0;
        int contador2 = 0;
        while(true)
        {
            if (array[contador1] == registroAfectado1)
            {
                break;
            }
            if (contador1 == array.length - 1)
            {
                stringConNops1 = "";
                contador1 = -1;
                break;
            }
            contador1++;
        }
        while(true)
        {
            if (array[contador2] == registroAfectado2)
            {
                break;
            }
            if (contador2 == array.length - 1)
            {
                stringConNops2 = "";
                contador2 = -1;
                break;
            }
            contador2++;
        }
        if (contador1>contador2)
        {
            return contador1+1;
        }
        else
        {
            return contador2+1;
        }
    }

    private static int[] shiftArray(int[] array, int nuevo)
    {
        for (int i = 0; i + 1 < array.length; i++)
        {
            array[i] = array[i+1];
        }
        array[array.length - 1] = nuevo;
        return array;
    }

    private static Pair<Integer, int[]> buscarNops(String line, int[] array)
    {
        String[] instruction = line.split(" ");
        int cantidadNops = 0;
        String codigo = instruction[0];
        if (codigo.equals("lbm") || codigo.equals("addr") || codigo.equals("subr") || codigo.equals("mulr") || codigo.equals("andr"))
        {
            int int1 = Integer.parseInt(instruction[1]);
            int int2 = Integer.parseInt(instruction[2]);
            int int3 = Integer.parseInt(instruction[3]);
            cantidadNops = contarNops(array, int2, int3);
            if (cantidadNops != 0)
            {
                array = new int[]{17,17,17};
            }
            array = shiftArray(array, int1);
        }
        if (codigo.equals("lim"))
        {
           int int1 = Integer.parseInt(instruction[1]);
           array = shiftArray(array, int1);
        }
        if (codigo.equals("bner") || codigo.equals("beqr"))
        {
           int int1 = Integer.parseInt(instruction[1]);
           int int2 = Integer.parseInt(instruction[2]);
           cantidadNops = contarNops(array, int1, int2);
           cantidadNops += 3;
           array = new int[]{17,17,17};
        }
        if (codigo.equals("beqzero") || codigo.equals("beqi"))
        {
           int int1 = Integer.parseInt(instruction[1]);
           cantidadNops = contarNops(array, int1, 16);
           cantidadNops += 3;
           array = new int[]{17,17,17};
        }
        if (codigo.equals("jump") || codigo.equals("nop"))
        {
           array = shiftArray(array, 17);
        }
        if (codigo.equals("addinm") || codigo.equals("sllr") || codigo.equals("divri") || codigo.equals("srlr"))
        {
           int int1 = Integer.parseInt(instruction[1]);
           int int2 = Integer.parseInt(instruction[2]);
           int int3 = Integer.parseInt(instruction[3]);
           cantidadNops = contarNops(array, int2, 16);
           if (cantidadNops != 0)
            {
                array = new int[]{17,17,17};
            }
            array = shiftArray(array, int1);
        }
        return new Pair<>(cantidadNops, array);
    }
    
    public static void main( String args[] ) throws IOException {

        int[] arrayNops = new int[]{17,17,17};

        String fileString = "C:\\Users\\Pelo\\Google Drive\\2017 Semestre 2\\Arquitectura de Computadoras 1\\Proyectos\\Repositorio Proyecto 1\\Pipeline Compiler\\Ejemplos\\assembly.asm";
        FileReader file = new FileReader(fileString);
        String codeString = "C:\\Users\\Pelo\\Google Drive\\2017 Semestre 2\\Arquitectura de Computadoras 1\\Proyectos\\Repositorio Proyecto 1\\Pipeline Compiler\\Ejemplos\\code.mcn";
        FileWriter codedFile = new FileWriter(codeString);
        BufferedReader br1 = new BufferedReader(file);
        String line = null;
        Map<String, Integer> tags = new HashMap<>();
        int contadorTags = 0;
        int totalTags = 0;
        int contadorNopsCicloTags = 0;
        int[] arrayCiclo1 = new int[]{17,17,17};
        while ((line = br1.readLine()) != null)
        {
            String pattern = "[a-zA-Z]+:";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);
            if (m.find())
            {
                tags.put(line.split(":")[0], contadorTags - totalTags);
                contadorNopsCicloTags = 0;
                totalTags++;
            }
            else
            {
                Pair<Integer, int[]> nopsArray =  buscarNops(line, arrayCiclo1);
                contadorNopsCicloTags = nopsArray.getKey();
                arrayCiclo1 = nopsArray.getValue();
            }
            contadorTags = contadorTags + contadorNopsCicloTags + 1;
        }
        br1.close();
        BufferedReader br2 = new BufferedReader(new FileReader(fileString));
        while ((line = br2.readLine()) != null)
        {
            String codedInstruction = "";
            String[] instruction = line.split(" ");
            if (instruction[0].equals("nop"))
            {
               arrayNops = shiftArray(arrayNops, 17);
               codedInstruction = codedInstruction.concat("0000000000000000000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("lbm"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, int3);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1000").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("lim"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("0101").concat(dato1).concat(dato2).concat("1100000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("addr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, int3);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("0001").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("andr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, int3);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1111").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("subr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, int3);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("0010").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("mulr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, int3);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("0011").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("bner"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int cantidadNops = contarNops(arrayNops, int1, int2);
               codedInstruction = agregarNops(cantidadNops);
               arrayNops = new int[]{17,17,17};
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(tags.get(instruction[3])),7);
               codedInstruction = codedInstruction.concat("0100").concat(dato1).concat(dato2).concat(dato3).concat("\n");
               codedInstruction = codedInstruction.concat("0000000000000000000\n0000000000000000000\n0000000000000000000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("beqr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int cantidadNops = contarNops(arrayNops, int1, int2);
               codedInstruction = agregarNops(cantidadNops);
               arrayNops = new int[]{17,17,17};
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(tags.get(instruction[3])),7);
               codedInstruction = codedInstruction.concat("1100").concat(dato1).concat(dato2).concat(dato3).concat("\n");
               codedInstruction = codedInstruction.concat("0000000000000000000\n0000000000000000000\n0000000000000000000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("beqzero"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int cantidadNops = contarNops(arrayNops, int1, 16);
               codedInstruction = agregarNops(cantidadNops);
               arrayNops = new int[]{17,17,17};
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato3 = agregarCeros(Integer.toBinaryString(tags.get(instruction[2])),7);
               codedInstruction = codedInstruction.concat("0110").concat(dato1).concat("0000").concat(dato3).concat("\n");
               codedInstruction = codedInstruction.concat("0000000000000000000\n0000000000000000000\n0000000000000000000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("beqi"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int cantidadNops = contarNops(arrayNops, int1, 16);
               codedInstruction = agregarNops(cantidadNops);
               arrayNops = new int[]{17,17,17};
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2), 4);
               String dato3 = agregarCeros(Integer.toBinaryString(tags.get(instruction[2])),7);
               codedInstruction = codedInstruction.concat("1101").concat(dato1).concat(dato2).concat(dato3).concat("\n");
               codedInstruction = codedInstruction.concat("0000000000000000000\n0000000000000000000\n0000000000000000000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("jump"))
            {
               arrayNops = shiftArray(arrayNops, 17);
               String dato1 = agregarCeros(Integer.toBinaryString(tags.get(instruction[1])),7);
               codedInstruction = codedInstruction.concat("0111").concat("00000000").concat(dato1).concat("\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("addinm"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, 16);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1010").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("sllr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, 16);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1001").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("srlr"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, 16);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1110").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
            if (instruction[0].equals("divri"))
            {
               int int1 = Integer.parseInt(instruction[1]);
               int int2 = Integer.parseInt(instruction[2]);
               int int3 = Integer.parseInt(instruction[3]);
               String dato1 = agregarCeros(Integer.toBinaryString(int1),4);
               String dato2 = agregarCeros(Integer.toBinaryString(int2),4);
               String dato3 = agregarCeros(Integer.toBinaryString(int3),4);
               int cantidadNops = contarNops(arrayNops, int2, 16);
               codedInstruction = agregarNops(cantidadNops);
               if (cantidadNops != 0)
               {
                   arrayNops = new int[]{17,17,17};
               }
               arrayNops = shiftArray(arrayNops, int1);
               codedInstruction = codedInstruction.concat("1011").concat(dato1).concat(dato2).concat(dato3).concat("000\n");
               codedFile.write(codedInstruction);
            }
        }
        codedFile.close();
   } 
}
