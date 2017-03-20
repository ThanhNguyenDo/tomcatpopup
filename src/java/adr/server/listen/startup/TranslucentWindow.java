package adr.server.listen.startup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TranslucentWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;

    public TranslucentWindow() {
        super("Test translucent window");
        setLayout(new FlowLayout());       
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 100);
            }
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        JLabel info = new JLabel("Your Tomcat server already startup successfully ");
        setFontLable(info);
        panel.add(info);
        add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setOpacity(0.70f);
    }
    
    private void setFontLable(JLabel label)
    {
    	Font labelFont = label.getFont();
    	String labelText = label.getText();

    	int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
    	int componentWidth = 400;

    	// Find out how much the font can grow in width.
    	double widthRatio = (double)componentWidth / (double)stringWidth;

    	int newFontSize = (int)(labelFont.getSize() * widthRatio);
    	int componentHeight = 100;

    	// Pick a new font size so it will not be larger than the height of label.
    	int fontSizeToUse = Math.min(newFontSize, componentHeight);

    	// Set the label's font size to the newly determined size.
    	label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    	label.setForeground(Color.CYAN);
    }

}
