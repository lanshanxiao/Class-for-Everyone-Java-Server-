package com.wanli.swing.frame.listener;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.service.DBService;
import com.wanli.utils.StaticVariable;

public class TabFordlerListener extends SelectionAdapter {

	private TabFolder tabFolder;// 选项卡
	private Combo historyCombo;// 显示历史成绩表的下拉框
	private DBService dbService;
	private List<String[]> records;
	private int columnNum = 0;
	
	public TabFordlerListener(TabFolder tabFolder, Combo historyCombo) {
		this.tabFolder = tabFolder;
		this.historyCombo = historyCombo;
		dbService = new DBService();
	}
	
	@Override
    public void widgetSelected(final SelectionEvent e) { // 匿名类
        handle_tabFolder_widgetSelected(e);
    }
	
	protected void handle_tabFolder_widgetSelected(final SelectionEvent e) {
		
		if (tabFolder.getSelectionIndex() == 1) {
			StaticVariable.refresh.setEnabled(false);
			StaticVariable.scoreChartBtn.setEnabled(false);
			if (StaticVariable.scoreTab.getColumnCount() != 0) {
				int deleteColumn = StaticVariable.scoreTab.getColumnCount();
				int deleteRow = StaticVariable.scoreTab.getItemCount();
				for (int i = 0; i < deleteRow; i++) {
					StaticVariable.scoreTab.remove(0);
				}
				for (int i = 0; i < deleteColumn; i++) {
					StaticVariable.scoreTab.getColumn(0).dispose();
				}
			}
			if (StaticVariable.tableName != null) {
				StaticVariable.refresh.setEnabled(true);
				StaticVariable.scoreChartBtn.setEnabled(true);
				columnNum = dbService.getTableColumn(StaticVariable.tableName);
				records = dbService.getScoreData(StaticVariable.tableName);
				for (int i = 0; i < columnNum; i++) {
					if (i == 0) {
						TableColumn Column_name = new TableColumn(StaticVariable.scoreTab, SWT.NONE);
						Column_name.setText("用户名");
						Column_name.setWidth(100);						
					} else {
						TableColumn Column_name = new TableColumn(StaticVariable.scoreTab, SWT.NONE);
						Column_name.setText("题" + i);
						Column_name.setWidth(100);
					}
				}
				for (String[] record: records) {
					new TableItem(StaticVariable.scoreTab, SWT.NONE).setText(record);
				}
			}
			
		}
		
		if (tabFolder.getSelectionIndex() == 2) {
			List<String> tableList = dbService.getTableList();
			String[] tables = tableList.toArray(new String[tableList.size()]);
			historyCombo.setItems(tables);
		}
		
	}

}
