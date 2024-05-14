import myLang.Interpreter;
import myLang.Parser;
import myLang.Tokenizer;
import myLang.value.Value;
import util.CellUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;

import static util.CellUtil.intToColumnHeader;

public class Main {
    /**
     * Main program, see README.md for docs.
     */
    public static void main(String[] args) {
        final int numberOfRows = 100;
        final int numberOfColumns = 100;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Assignment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTable table = spreadsheet(numberOfColumns, numberOfRows);

            JList<String> rowHeader = rowSetup(table);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setRowHeaderView(rowHeader);
            frame.add(scrollPane);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JList<String> rowSetup(JTable table) {
        JList<String> rowHeader = new JList<>(new AbstractListModel<>() {
            @Override
            public int getSize() {
                return 100;
            }

            @Override
            public String getElementAt(int index) {
                return String.valueOf(index + 1);
            }
        });
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        rowHeader.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        return rowHeader;
    }

    private static JTable spreadsheet(int width, int height) {
        String[] columns = new String[width];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = intToColumnHeader(i);
        }
        Value[][] cells = CellUtil.initEmpty(height, width);
        String[][] data = new String[height][width];
        DefaultTableModel model = new DefaultTableModel(data, columns);
        TableModelListener listener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object newValue = model.getValueAt(row, column);
                    Value updatedValue = cells[row][column];
                    try {
                        updatedValue = interp(String.valueOf(newValue), cells);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
                    }
                    model.removeTableModelListener(this);
                    cells[row][column] = updatedValue;
                    model.setValueAt(updatedValue.showVal(), row, column);
                    model.addTableModelListener(this);
                }
            }
        };
        model.addTableModelListener(listener);

        JTable table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int columnCount = table.getColumnModel().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setMinWidth(100);
            column.setMaxWidth(100);
            column.setPreferredWidth(100);
        }

        return table;
    }

    private static Value interp(String in, Value[][] cells) {
        Parser p = new Parser(Tokenizer.tokenize(in));
        return Interpreter.interpret(p.getOutput(), cells);
    }
}
