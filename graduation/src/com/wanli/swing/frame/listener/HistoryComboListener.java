package com.wanli.swing.frame.listener;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.service.DBService;
import com.wanli.utils.StaticVariable;

public class HistoryComboListener implements SelectionListener {

	private Combo historyCombo;
	private DBService dbService;
	
	public HistoryComboListener(Combo historyCombo) {
		this.historyCombo = historyCombo;
		dbService = new DBService();
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		String tableName = historyCombo.getText();
		if (StaticVariable.historyTab.getColumnCount() != 0) {
			int deleteColumn = StaticVariable.historyTab.getColumnCount();
			int deleteRow = StaticVariable.historyTab.getItemCount();
			for (int i = 0; i < deleteRow; i++) {
				StaticVariable.historyTab.remove(0);
			}
			for (int i = 0; i < deleteColumn; i++) {
				StaticVariable.historyTab.getColumn(0).dispose();
			}
		}
		int columnNum = dbService.getTableColumn(tableName);
		List<String[]> records = dbService.getScoreData(tableName);
		for (int i = 0; i < columnNum; i++) {
			if (i == 0) {
				TableColumn Column_name = new TableColumn(StaticVariable.historyTab, SWT.NONE);
				Column_name.setText("用户名");
				Column_name.setWidth(100);						
			} else {
				TableColumn Column_name = new TableColumn(StaticVariable.historyTab, SWT.NONE);
				Column_name.setText("题" + i);
				Column_name.setWidth(100);
			}
		}
		for (String[] record: records) {
			new TableItem(StaticVariable.historyTab, SWT.NONE).setText(record);
		}
	}

}
