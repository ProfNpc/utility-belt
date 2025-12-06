package com.professornpc.ubelt;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUtility implements ClipboardOwner {

    public void setClipboardContents(String string) {
        // Wrap the string in a StringSelection object, which implements the Transferable interface
        StringSelection selection = new StringSelection(string);
        
        // Get the system clipboard instance
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // Set the clipboard's content. The 'owner' is the class itself (this), 
        // which handles the lostOwnership event (though typically this can be null).
        clipboard.setContents(selection, this);
        
        System.out.println("Text copied to clipboard: \"" + string + "\"");
    }

    @Override
    public void lostOwnership(Clipboard clipboard, java.awt.datatransfer.Transferable contents) {
        // This method is called when another application or process 
        // overwrites the clipboard content. An empty implementation is often sufficient.
    }
    
    public String getClipboardData() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            // Check if the clipboard contains data in the string flavor (plain text)
            //if (((Transferable) clipboard).isDataFlavorSupported(DataFlavor.stringFlavor)) {
                // Get the transferable object from the clipboard
                Transferable transferable = clipboard.getContents(null);
                if (transferable != null) {
                    // Retrieve the actual data as a String
                    return (String) transferable.getTransferData(DataFlavor.stringFlavor);
                }
            //}
        } catch (UnsupportedFlavorException | IOException e) {
            // Handle exceptions (e.g., data is not in the expected format, or I/O error)
            System.err.println("Error accessing clipboard data: " + e.getMessage());
            e.printStackTrace();
        } catch (HeadlessException e) {
            // Handle cases where there is no display, keyboard, or mouse
            System.err.println("Cannot access clipboard in a headless environment: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        ClipboardUtility utility = new ClipboardUtility();
        String textToCopy = "Hello from Java Clipboard!";
        utility.setClipboardContents(textToCopy);
        
        // You can now go to any text editor (like Notepad) and press Ctrl+V to paste the text.
    }
}
