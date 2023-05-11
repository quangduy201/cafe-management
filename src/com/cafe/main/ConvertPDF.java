package com.cafe.main;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ConvertPDF {
    public static void main( String[] args )  {
//        License lic = new License();
//        lic.setLicense("GroupDocs.Conversion.lic");

        JFileChooser fc = new JFileChooser();
        fc.removeChoosableFileFilter(fc.getFileFilter());
        // set thu muc default, mày thay "transaction/bills" thành đường dẫn đến thư mục Excel của m để nó mở thư mục Excel luôn
        fc.setCurrentDirectory(new File("transaction/bills"));

        FileFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Converter converter = new Converter(file.getPath());
            PdfConvertOptions options = new PdfConvertOptions();
            converter.convert(file.getPath().replace("xlsx", "pdf"), options);

            System.out.println("Done");
        }


    }
}
