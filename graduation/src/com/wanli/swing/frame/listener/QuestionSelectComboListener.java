package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;

import com.wanli.utils.StaticVariable;

public class QuestionSelectComboListener implements SelectionListener {

	private int option = 65;							// 标记选项
	private String question;							// 获取题目
	private String[] strs;								// 将获取的题目进行分割
	private StringBuffer showQues = new StringBuffer();	// 显示题目
	private StyleRange style;
	
	public QuestionSelectComboListener() {
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
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
//		System.out.println(StaticVariable.questionSelect.getSelectionIndex());
//		System.out.println(StaticVariable.questionSelect.getText());
		// 获取当前选中列的下标
		int index = StaticVariable.questionSelect.getSelectionIndex();
		// 获取当前选中列的文本
		String selected = StaticVariable.questionSelect.getText();
		if (index != 0) {
			// 截取题目类型
			String type = selected.substring(selected.indexOf("-") + 1);
			switch (type) {
			case "选择题":
				// 清空StringBuffer
				showQues.setLength(0);
				// 获取题目
				question = StaticVariable.questionsMap.get(Integer.toString(index));
				// 分割题目
				strs = question.split(",");
				// 重组题目
				showQues.append("选择题:\n\n");
				showQues.append(strs[2]);
				for (int i = 4; i < strs.length; i++) {
					showQues.append("\n" + (char)option + "." + strs[i]);
					option++;
				}
				option = 65;
				// 显示题目
				StaticVariable.text.setText(showQues.toString());
				// 设置样式
				StaticVariable.text.setStyleRange(style);
				break;

			case "是非题":
				showQues.setLength(0);
				question = StaticVariable.questionsMap.get(Integer.toString(index));
				strs = question.split(",");
				showQues.append("是非题:\n\n");
				showQues.append(strs[2]);
				StaticVariable.text.setText(showQues.toString());
				StaticVariable.text.setStyleRange(style);
				break;
			case "填空题":
				showQues.setLength(0);
				question = StaticVariable.questionsMap.get(Integer.toString(index));
				strs = question.split(",");
				showQues.append("填空题:\n\n");
				showQues.append(strs[2]);
				StaticVariable.text.setText(showQues.toString());
				StaticVariable.text.setStyleRange(style);
				break;
			}
		}
	}

}
