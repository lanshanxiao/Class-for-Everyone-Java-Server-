package com.wanli.swing.frame.listener;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TabFolder;

import com.wanli.swing.service.DBService;
import com.wanli.utils.StaticVariable;

public class TabFordlerListener extends SelectionAdapter {

	private TabFolder tabFolder;// ѡ�
	private Combo historyCombo;// ��ʾ��ʷ�ɼ����������
	private DBService dbService;//��ѯ���ݿ⣬��ȡ������
	private StringBuffer showAnswer = new StringBuffer();	// ��ʾ��
	private StyleRange style;
	
	
	public TabFordlerListener(TabFolder tabFolder, Combo historyCombo) {
		this.tabFolder = tabFolder;
		this.historyCombo = historyCombo;
		dbService = new DBService();
		// ����һ��style����
		style = new StyleRange();
		// �ӵ�һ���ַ���ʼ
		style.start = 0;
		// ����ѡ����:�����Ƿ���:���������:������»��߲��ҼӴ�
		style.length = 4;
		style.underline = true;
		style.font = new Font(StaticVariable.parent.getDisplay(), "Arial", 30, SWT.BOLD);
	}
	
	@Override
    public void widgetSelected(final SelectionEvent e) { // ������
        handle_tabFolder_widgetSelected(e);
    }
	
	
	protected void handle_tabFolder_widgetSelected(final SelectionEvent e) {
		
		if (tabFolder.getSelectionIndex() == 1) {
			// ���StringBuffer��ֵ������Ӱ����һ�����ʾ
			showAnswer.setLength(0);
			int index = StaticVariable.questionSelect.getSelectionIndex();
			if (index > 0) {
				showAnswer.append("��:");
//				String question = StaticVariable.questionsMap.get(Integer.toString(index));
				// question��ʽ����Ŀ���ͣ���Ŀ����1����2... 
				String question = StaticVariable.questionsList.get(index - 1);
				String[] strs = question.split("#\\^");
				if (strs[0].equals("fill_in_the_blanks")) {
					// �ж��Ƿ��ж����
					if ((strs.length - 3) > 0) {
						// �ж����
						for (int i = 2; i < strs.length; i++) {
							showAnswer.append("\n\t\t" + strs[i]);
						}
					} else {
						// ֻ��һ����
						showAnswer.append("\n\t\t" + strs[2]);
					}					
				} else {
					// ֻ��һ����
					showAnswer.append("\n\t\t" + strs[2]);
				}
				StaticVariable.answer.setText(showAnswer.toString());
				// ������ʽ
				StaticVariable.answer.setStyleRange(style);
			}
//			StaticVariable.refresh.setEnabled(false);
//			StaticVariable.scoreChartBtn.setEnabled(false);
//			if (StaticVariable.scoreTab.getColumnCount() != 0) {
//				int deleteColumn = StaticVariable.scoreTab.getColumnCount();
//				int deleteRow = StaticVariable.scoreTab.getItemCount();
//				for (int i = 0; i < deleteRow; i++) {
//					StaticVariable.scoreTab.remove(0);
//				}
//				for (int i = 0; i < deleteColumn; i++) {
//					StaticVariable.scoreTab.getColumn(0).dispose();
//				}
//			}
//			if (StaticVariable.tableName != null) {
//				StaticVariable.refresh.setEnabled(true);
//				StaticVariable.scoreChartBtn.setEnabled(true);
//				columnNum = dbService.getTableColumn(StaticVariable.tableName);
//				records = dbService.getScoreData(StaticVariable.tableName);
//				for (int i = 0; i < columnNum; i++) {
//					if (i == 0) {
//						TableColumn Column_name = new TableColumn(StaticVariable.scoreTab, SWT.NONE);
//						Column_name.setText("�û���");
//						Column_name.setWidth(100);						
//					} else {
//						TableColumn Column_name = new TableColumn(StaticVariable.scoreTab, SWT.NONE);
//						Column_name.setText("��" + i);
//						Column_name.setWidth(100);
//					}
//				}
//				for (String[] record: records) {
//					new TableItem(StaticVariable.scoreTab, SWT.NONE).setText(record);
//				}
//			}
			
		}
		
		if (tabFolder.getSelectionIndex() == 2) {
			List<String> tableList = dbService.getTableList();
			String[] tables = tableList.toArray(new String[tableList.size()]);
			historyCombo.setItems(tables);
		}
		
	}

}
