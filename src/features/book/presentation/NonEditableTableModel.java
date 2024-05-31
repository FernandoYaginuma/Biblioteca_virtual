package features.book.presentation;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[] objects, int i) {
        super(objects, i);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
