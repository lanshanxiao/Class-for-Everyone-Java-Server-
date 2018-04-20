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

	private TabFolder tabFolder;// 选项卡
	private Combo historyCombo;// 显示历史成绩表的下拉框
	private DBService dbService;//查询数据库，获取表数据
	private StringBuffer showAnswer = new StringBuffer();	// 显示答案
	private StyleRange style;
	
	
	public TabFordlerListener(TabFolder tabFolder, Combo historyCombo) {
		this.tabFolder = tabFolder;
		this.historyCombo = historyCombo;
		dbService = new DBService();
		// 定义一个style对象
		style = new StyleRange();
		// 从第一个字符开始
		style.start = 0;
		// 将“选择题:”“是非题:”“填空题:”添加下划线并且加粗
		style.length = 4;
		style.underline = true;
		style.font = new Font(StaticVariable.parent.getDisplay(), "Arial", 30, SWT.BOLD);
	}
	
	@Override
    public void widgetSelected(final SelectionEvent e) { // 匿名类
        handle_tabFolder_widgetSelected(e);
    }
	
	
	protected void handle_tabFolder_widgetSelected(final SelectionEvent e) {
		
		if (tabFolder.getSelectionIndex() == 1) {
			// 清空StringBuffer的值，避免影响下一题的显示
			showAnswer.setLength(0);
			int index = StaticVariable.questionSelect.getSelectionIndex();
			if (index > 0) {
				showAnswer.append("答案:");
				String question = StaticVariable.questionsMap.get(Integer.toString(index));
				String[] strs = question.split(",");
				if (strs[1].equals("fill_in_the_blanks")) {
					// 判断是否有多个答案
					if ((strs.length - 4) > 0) {
						// 有多个答案
						for (int i = 3; i < strs.length; i++) {
							showAnswer.append("\n\t\t" + strs[i]);
						}
					} else {
						// 只有一个答案
						showAnswer.append("\n\t\t" + strs[3]);
					}					
				} else {
					// 只有一个答案
					showAnswer.append("\n\t\t" + strs[3]);
				}
				StaticVariable.answer.setText(showAnswer.toString());
				// 设置样式
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
//						Column_name.setText("用户名");
//						Column_name.setWidth(100);						
//					} else {
//						TableColumn Column_name = new TableColumn(StaticVariable.scoreTab, SWT.NONE);
//						Column_name.setText("题" + i);
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
