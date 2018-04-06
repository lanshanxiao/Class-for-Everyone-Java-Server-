package com.wanli.meaure;

import java.text.Collator;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;


public class TreeApp {

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Sorting Trees");
		shell.setLayout(new FillLayout());
		shell.setSize(400, 300);

		Tree tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setHeaderVisible(true);

		TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
		column1.setText("TreeColumn0");
		column1.setWidth(200);
		column1.setAlignment(SWT.LEFT);
		column1.addSelectionListener(new SortTreeListener());

		TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
		column2.setText("TreeColumn1");
		column2.setWidth(200);
		column2.setAlignment(SWT.CENTER);
		column2.addSelectionListener(new SortTreeListener());

		Random generator = new Random();

		for (int i = 0; i < 5; i++) {
			TreeItem treeItem = new TreeItem(tree, 0);
			treeItem.setText(new String[] { "TreeItem" + i, Integer.toString(generator.nextInt()) });
			for (int j = 0; j < 5; j++) {
				TreeItem subTreeItem = new TreeItem(treeItem, SWT.NONE);
				subTreeItem.setText(new String[] { "SubTreeItem" + j, Integer.toString(generator.nextInt()) });
				for (int k = 0; k < 5; k++) {
					TreeItem subSubTreeItem = new TreeItem(subTreeItem, SWT.NONE);
					subSubTreeItem
							.setText(new String[] { "SubSubTreeItem" + k, Integer.toString(generator.nextInt()) });
				}
			}
		}

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}

class SortTreeListener implements SelectionListener {

    private void sortTree(SelectionEvent e) {
        TreeColumn column = (TreeColumn) e.widget;
        Tree tree = column.getParent();
        TreeItem[] treeItems = tree.getItems();
        TreeColumn sortColumn = tree.getSortColumn();
        TreeColumn columns[] = tree.getColumns();
        tree.setSortColumn(column);
        int numOfColumns = columns.length;
        int columnIndex = this.findColumnIndex(columns, column, numOfColumns);
        Collator collator = Collator.getInstance(Locale.getDefault());
        Boolean sort = false;
        Pattern pattern = Pattern.compile("([\\+]*|[\\-]*)\\d+");
        if ((column.equals(sortColumn)) && 
            (tree.getSortDirection() == SWT.UP)) {
            tree.setSortDirection(SWT.DOWN);
            for (int i = 1; i < treeItems.length; i++) {
                String value1 = treeItems[i].getText(columnIndex).trim();
                for (int j = 0; j < i; j++) {
                    String value2 = treeItems[j].getText(columnIndex).trim();
                    if (pattern.matcher(value1).matches()
                            && pattern.matcher(value2).matches()) {
                        double d1 = this.getDouble(value1);
                        double d2 = this.getDouble(value2);
                        if (d1 > d2) {
                            sort = true;
                        }
                    } else if (collator.compare(value1, value2) > 0) {
                        sort = true;
                    }
                    if (sort) {
                        String[] values = this.getColumnValues(treeItems[i],
                                numOfColumns);
                        TreeItem[] subItems = treeItems[i].getItems();
                        TreeItem item = new TreeItem(tree, SWT.NONE, j);
                        item.setText(values);
                        for (TreeItem si : subItems) {
                            TreeItem[] subSubItems = si.getItems();
                            TreeItem subItem = new TreeItem(item, SWT.NONE);
                            subItem.setText(this.getColumnValues(si, numOfColumns));
                            for (TreeItem ssi : subSubItems) {
                                TreeItem subSubItem = new TreeItem(subItem,
                                        SWT.NONE);
                                subSubItem.setText(this.getColumnValues(ssi,
                                        numOfColumns));
                            }
                        }
                        treeItems[i].dispose();
                        treeItems = tree.getItems();
                        sort = false;
                        break;
                    }
                }
            }
        } else {
            tree.setSortDirection(SWT.UP);
            for (int i = 1; i < treeItems.length; i++) {
                String value1 = treeItems[i].getText(columnIndex).trim();
                for (int j = 0; j < i; j++) {
                    String value2 = treeItems[j].getText(columnIndex).trim();
                    if (pattern.matcher(value1).matches()
                            && pattern.matcher(value2).matches()) {
                        double d1 = this.getDouble(value1);
                        double d2 = this.getDouble(value2);
                        if (d1 < d2) {
                            sort = true;
                        }
                    } else if (collator.compare(value1, value2) < 0) {
                        sort = true;
                    }
                    if (sort) {
                        String[] values = this.getColumnValues(treeItems[i],
                                numOfColumns);
                        TreeItem[] subItems = treeItems[i].getItems();
                        TreeItem item = new TreeItem(tree, SWT.NONE, j);
                        item.setText(values);
                        for (TreeItem si : subItems) {
                            TreeItem[] subSubItems = si.getItems();
                            TreeItem subItem = new TreeItem(item, SWT.NONE);
                            subItem.setText(this.getColumnValues(si, numOfColumns));
                            for (TreeItem ssi : subSubItems) {
                                TreeItem subSubItem = new TreeItem(subItem,
                                        SWT.NONE);
                                subSubItem.setText(this.getColumnValues(ssi,
                                        numOfColumns));
                            }
                        }
                        treeItems[i].dispose();
                        treeItems = tree.getItems();
                        sort = false;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Find the index of a column
     * 
     * @param columns
     * @param numOfColumns
     * @return int
     */
    private int findColumnIndex(TreeColumn[] columns, TreeColumn column,
           int numOfColumns) {
        int index = 0;
        for (int i = 0; i < numOfColumns; i++) {
            if (column.equals(columns[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Get the double value from a string
     * 
     * @param str
     * @return double
     */
    private double getDouble(String str) {
        double d;
        if (str.startsWith("+")) {
            d = Double.parseDouble(str.split("\\+")[1]);
        } else {
            d = Double.parseDouble(str);
        }
        return d;
    }

    /**
     * Get the array of string value from the provided TreeItem
     * 
     * @param treeItem
     * @param numOfColumns
     * @return String[]
     */
    private String[] getColumnValues(TreeItem treeItem, int numOfColumns) {
        String[] values = new String[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            values[i] = treeItem.getText(i);
        }
        return values;
    }

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		sortTree(arg0);
	}
}
