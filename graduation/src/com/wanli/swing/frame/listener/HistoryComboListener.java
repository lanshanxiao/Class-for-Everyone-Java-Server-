package com.wanli.swing.frame.listener;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.service.DBService;
import com.wanli.utils.HistoryChartTableUtil;
import com.wanli.utils.StaticVariable;

/**
 * ��ʷ��������ļ�����
 * @author wanli
 *
 */
public class HistoryComboListener implements SelectionListener {

	private Combo historyCombo;		// ѡ����ʷ���������
	private DBService dbService;	// �������ݿ�
	
	public HistoryComboListener(Combo historyCombo) {
		this.historyCombo = historyCombo;
		dbService = new DBService();
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// Ҫ���ҵı���
		String tableName = historyCombo.getText();
		// �ж����ݿ����Ƿ��б���ڣ�����ǰtable�Ѿ�����ʾ���ݣ��������������
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
		// ��ȡѡ��ı��������
		int columnNum = dbService.getTableColumn(tableName);
		// ��øñ����������
		List<String[]> records = dbService.getScoreData(tableName);
		// �������ݿ��б����е�����������tableҪ��ʾ����
		for (int i = 0; i < columnNum; i++) {
			if (i == 0) {
				TableColumn Column_name = new TableColumn(StaticVariable.historyTab, SWT.NONE);
				Column_name.setText("�û���");
				Column_name.setWidth(100);						
			} else {
				TableColumn Column_name = new TableColumn(StaticVariable.historyTab, SWT.NONE);
				Column_name.setText("��" + i);
				Column_name.setWidth(100);
				// �������У�Ϊ�������е�����ӵ��������
				Column_name.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						String column = Column_name.getText().substring(1);
						StaticVariable.statisticalData = dbService.getStatisticalData(StaticVariable.historyCombo.getText());							
						// ����ͼ��ͳ������
						new HistoryChartTableUtil(StaticVariable.parent.getShell(), Integer.parseInt(column)).open();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}
		}
		for (String[] record: records) {
			// ������������ʾ��table��
			new TableItem(StaticVariable.historyTab, SWT.NONE).setText(record);
		}
	}

}
