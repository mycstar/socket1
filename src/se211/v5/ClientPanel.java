package se211.v5;

import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JPanel implements ListCellRenderer<String> {
    private JLabel label;

    public ClientPanel() {
        setLayout(new BorderLayout());
        label = new JLabel();
        label.setOpaque(true); // make label opaque to set background color
        add(label, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        label.setText(value);
        // set background color to selection color if the label is selected
        if (isSelected) {
            label.setBackground(list.getSelectionBackground());
        } else {
            label.setBackground(list.getBackground());
        }
        return this;
    }
}
