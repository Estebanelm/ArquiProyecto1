/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline.compiler;
import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Pelo
 */
public class PipelineCompiler {
      public static void main( String args[] ) {
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
   }    
}
