package print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

//import javax.print.attribute.HashPrintRequestAttributeSet;
//import javax.print.attribute.PrintRequestAttributeSet;
//import javax.print.attribute.Size2DSyntax;
//import javax.print.attribute.standard.MediaSize;
//import javax.print.attribute.standard.MediaSizeName;
//import javax.print.attribute.standard.OrientationRequested;
//import javax.print.attribute.standard.PrinterResolution;

public class PrintGraphicalPage implements Printable {
	PrinterJob printJob;
	PageFormat format;
	Paper paper;
	//PrinterResolution resolution;
	//PrintRequestAttributeSet aset;
	//MediaSize myMediaSize;
	
  public PrintGraphicalPage() {
	printJob = PrinterJob.getPrinterJob();   
	format = printJob.getPageFormat(null);
	paper = format.getPaper();
	paper.setImageableArea(0.0, 0.0, format.getPaper().getWidth(), format.getPaper().getHeight());
	format.setPaper(paper);
	
	
	//aset = new HashPrintRequestAttributeSet();
	//MediaSizeName newMediaSizeName = new MediaSizeName(300);
	//myMediaSize=new MediaSize(80,230,Size2DSyntax.MM, MediaSizeName.PERSONAL_ENVELOPE);
	//aset.add(myMediaSize);
    //aset.add(MediaSizeName.ISO_A4);
	//resolution = new PrinterResolution(100, 100, PrinterResolution.DPI);
    //aset.add(resolution);
    //aset.add(OrientationRequested.PORTRAIT);
  }

  public void print() throws PrinterException{
	  printJob.setPrintable(this, format);
	  printJob.print();
	  
  }
  /**
   * Method: print
   * 
   * This class is responsible for rendering a page using the provided
   * parameters. The result will be a grid where each cell will be half an
   * inch by half an inch.
   * 
   * @param g
   *            a value of type Graphics
   * @param pageFormat
   *            a value of type PageFormat
   * @param page
   *            a value of type int
   * @return a value of type int
   */
  public int print(Graphics g, PageFormat pageFormat, int page) throws PrinterException {
    Graphics2D g2d;
    if (page == 0) {
      g2d = (Graphics2D) g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      //g2d.setFont(Font..SANS_SERIF);
      int textHeight = g2d.getFontMetrics().getHeight();
      g2d.drawString("02-06-2013       ORDINE NUMERO:           609789000", 0, textHeight);
      for (int i = 2; i <= 10; i++) {
      	g2d.drawString("1 casoncelli                                   3,00", 0, i*textHeight);
		}
      return (PAGE_EXISTS);
    } else
      return (NO_SUCH_PAGE);
  }

}