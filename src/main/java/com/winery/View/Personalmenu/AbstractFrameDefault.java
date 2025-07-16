package com.winery.View.Personalmenu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import org.apache.commons.lang3.math.Fraction;

import com.winery.Utility.Components;

public abstract class AbstractFrameDefault extends JFrame {

    private static final long serialVersionUID = 8481707000166074215L;
    private static final Dimension DIMENSION_DEFAULT_SCREEN = new Dimension(1920, 1080);
    private static final Dimension SCREENDIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Fraction FRACTIONWIDTH = Fraction.getFraction(SCREENDIMENSION.width,
            DIMENSION_DEFAULT_SCREEN.width);
    private static final Fraction FRACTIONHEIGHT = Fraction.getFraction(SCREENDIMENSION.height,
            DIMENSION_DEFAULT_SCREEN.height);
    protected static final String EXIT_MESSAGE = "Vuoi uscire ?";

    
    public AbstractFrameDefault(final int width, final int height) {
        super();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(new Dimension((int) (FRACTIONWIDTH.doubleValue() * width),
                (int) (FRACTIONHEIGHT.doubleValue() * height)));
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
            	closeAction();
            }
        });
        this.pack();
        this.setIconImage(Components.createImage("/ico/grape-icon.png"));
    }
    
    protected abstract void closeAction() ;
    
    public final void display() {
        this.setVisible(true);
    }
    
    protected void rebuildGui() {
		this.revalidate();
		this.repaint();
	}
}
