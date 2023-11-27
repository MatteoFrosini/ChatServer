package GUI.CostumJComponents;

import javax.swing.*;

public class costumJLabel extends JLabel {
    public costumJLabel(String text, JPanel panel, int sizeX, int sizeY, int x, int y) {
        super(text);
        setSize(sizeX,sizeY);
        setLocation(x,y);
        panel.add(this);
    }
}
