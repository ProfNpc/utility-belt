package com.professornpc.ubelt;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;

public class FormatadorSQL {
	
	public static void main(String[] args) {
		
        Options options = new Options();
        
        options.addOption("q", "fsql", false, "Formata o texto da area de transferência como se fosse um sql");
		
	    CommandLineParser parser = new DefaultParser();
	    try {
	        CommandLine cmd = parser.parse(options, args);
	        System.out.println("fsql1");
	        if (cmd.hasOption("fsql")) {
	        	System.out.println("fsql2");
	        	colarTextoCopiadoFormatoComoSQL();
	        }
	        System.out.println("fsql3");

	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.err.println("Error parsing command line arguments: " + e.getMessage());
	        HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp("myapp", options); // Display help on error
	    }
		
		
	}


	private static void colarTextoCopiadoFormatoComoSQL() {
		ClipboardUtility clipboardUtility = new ClipboardUtility();
		
        String clipboardText = clipboardUtility.getClipboardData();
        if (clipboardText != null) {
            System.out.println("Data from clipboard: " + clipboardText);
            
    		FormatadorSQL formatador = new FormatadorSQL();
    		String sqlFormatado;
    		try {
    			//sqlFormatado = formatador.formatar("SELECT p.codpes, p.numseqsrv, p.dtainiperaqs, p.dtafimperaqs,p.totdia, p.star_x, p.medadcntu, p.medadcprl, p.medadcisl, p.staferppc, p.nummesppcrsc, p.numdiapagrsc FROM FPCPERAQFERIAS p WHERE '2025-03-11 18:35' BETWEEN p.dtainivigddo AND p.dtafimvigddo and p.numdiapagrsc > 0 AND p.numseqsrv = CONVERT(INT, '1') AND p.codpes = CONVERT(INT, '3055380')");
    			sqlFormatado = formatador.formatar(clipboardText);
    			
    			//System.out.println(sqlFormatado);
    			
    		    Path path = Paths.get("out.txt");
    		    byte[] strToBytes = sqlFormatado.getBytes();

    		    Files.write(path, strToBytes);
    			
    			
    			clipboardUtility.setClipboardContents(sqlFormatado);
    			
    			enviarCtrlV();
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            
        } else {
            System.out.println("Clipboard does not contain plain text or is empty.");
        }
	}
	

	public String formatar(String sql) throws Exception {
		
	  FormatConfig formatConfig = FormatConfig.builder()
	    .indent("\t") // Defaults to two spaces
	    .uppercase(true) // Defaults to false (not safe to use when SQL dialect has case-sensitive identifiers)
	    .linesBetweenQueries(2) // Defaults to 1
	    .maxColumnLength(100) // Defaults to 50
	    .params(Arrays.asList("a", "b", "c")) // Map or List. See Placeholders replacement.
	    .build();
		
		String formattedSql = SqlFormatter.format(sql, formatConfig);
		return formattedSql;
	}
	
	

	
	public static void enviarCtrlV() {
        try {
            Robot robot = new Robot();

            // Simulate pressing Ctrl key
            robot.keyPress(KeyEvent.VK_CONTROL);

            // Simulate pressing V key
            robot.keyPress(KeyEvent.VK_V);

            // Simulate releasing V key
            robot.keyRelease(KeyEvent.VK_V);

            // Simulate releasing Ctrl key
            robot.keyRelease(KeyEvent.VK_CONTROL);

            System.out.println("Ctrl+V simulated successfully.");

        } catch (AWTException e) {
            e.printStackTrace();
        }
	}
	
	

}
