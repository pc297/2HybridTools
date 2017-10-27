/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cimlpflab
 */
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
public class SequenceFilter extends FileFilter {

//Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.txt) ||
                extension.equals(Utils.seq) ||
                extension.equals(Utils.fa) ||
                extension.equals(Utils.fasta))
                
                 {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Sequence files  (*.txt;*.seq;*.fa;*.fasta";
    }
}
