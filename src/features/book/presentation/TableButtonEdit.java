package features.book.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TableButtonEdit extends DefaultTableCellRenderer {
    private final BooksScreen view;

    public TableButtonEdit(BooksScreen view) {
        super();
        this.view = view;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JButton button = new JButton("Editar");
        System.out.println("Clico aqui" + row);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> view.editBook(row));
        return button;
    }
}