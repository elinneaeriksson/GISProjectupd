import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ImageComboBoxRenderer extends DefaultListCellRenderer {
    private final HashMap<String, Icon> itemImageMap;

    public ImageComboBoxRenderer(HashMap<String, Icon> itemImageMap) {
        this.itemImageMap = itemImageMap;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (itemImageMap.containsKey(value)) {
            ImageIcon icon = (ImageIcon) itemImageMap.get(value);
            label.setIcon(icon);
            label.setText(null);  // Clear the text to only display the icon
        }
        return label;
    }
}
