package tree.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

public class ComponentPrinter implements Printable{

	private Component comp;
	  
	
	
	  /**
	   * querformat
	   */
	  private boolean landscape = false;
	  
	  public ComponentPrinter(Component comp)
	  {
	    this(comp, false);
	  }
	  
	  public ComponentPrinter(Component comp, boolean landscape)
	  {
		
	    if(comp == null)
	    {
	      throw new NullPointerException("comp may not be null");
	    }
	    
	    this.comp = comp;
	    this.landscape = landscape;
	  }
	  
	  /**
	   * (non-Javadoc)
	   * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	   */
	  public int print(Graphics g, PageFormat pF, int pageIndex)
	    throws PrinterException
	  {
	    double paperHeight = pF.getImageableHeight();
	    double paperWidth = pF.getImageableWidth();
	    int compHeight = (int) comp.getPreferredSize().getHeight();
	    int compWidth = (int) comp.getPreferredSize().getWidth();
	    int maxHeight = (int) Math.ceil(compHeight / (double) paperHeight);
	    int maxWidth = (int) Math.ceil(compWidth / (double) paperWidth);
	    int heightCounter = pageIndex % maxHeight;
	    int widthCounter = pageIndex / maxHeight;
	    
	    // Pruefen obs auf der Seite noch etwas zu drucken gibt 
	    if( widthCounter > maxWidth)
	    {
	      return (NO_SUCH_PAGE);
	    }
	 
	    RepaintManager currentManager = RepaintManager.currentManager(comp);
	    
	    try
	    {
	      currentManager.setDoubleBufferingEnabled(false);
	      
	      
	      System.out.println("Comp Pref Height " + compHeight + " Comp Pref Width " + 
	      compWidth);
	      System.out.println("Pageheight " + paperHeight + " PageWidth " + paperWidth + " Heightcounter " + heightCounter);
	      
	      g.translate((int)pF.getImageableX(),(int) pF.getImageableY());
	      
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setBackground(Color.WHITE);
	      g.translate((int) - (paperWidth*widthCounter), (int) - (paperHeight*heightCounter) );
	      comp.paint(g);
	      return PAGE_EXISTS;
	    }
	    finally
	    {
	      currentManager.setDoubleBufferingEnabled(true);
	    }
	  }
	  
	  public void doPrint() throws PrinterException
	  {
	    PrinterJob printJob = PrinterJob.getPrinterJob();
	    PageFormat pF;
	    // Querformat
	    if(landscape)
	    {
	      pF = new PageFormat();
	      pF.setOrientation(PageFormat.LANDSCAPE);
	      printJob.setPrintable(this, pF);
	    }
	    else
	    {
	      printJob.setPrintable(this);
	     
	    }
	    
	   
	    
	    if (printJob.printDialog())
	    {
	      printJob.print();
	    }
	  }
	  
	  public static void doPrint(Component comp, boolean landscape) throws PrinterException
	  {
	    new ComponentPrinter(comp, landscape).doPrint();
	  }
	  
	  public static void doPrint(Component comp) throws PrinterException
	  {
	    new ComponentPrinter(comp).doPrint();
	  }
}
