package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;

import com.wanli.swing.service.DBService;
import com.wanli.utils.StaticVariable;

/**
 * ��Ŀѡ��������
 * @author wanli
 *
 */
public class QuestionSelectComboListener implements SelectionListener {

	private int option = 65;							// ���ѡ��
	private String question;							// ��ȡ��Ŀ
	private String[] strs;								// ����ȡ����Ŀ���зָ�
	private StringBuffer showQues = new StringBuffer();	// ��ʾ��Ŀ
	private StyleRange style;							// �ı���ʾ�ķ��
	private DBService dbService;						// �������ݿ�ķ���
	private int index = 0;								// ���������ĵ�ǰѡ�е���
	
	public QuestionSelectComboListener() {
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
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
//		System.out.println(StaticVariable.questionSelect.getSelectionIndex());
//		System.out.println(StaticVariable.questionSelect.getText());
		// StaticVariable.correct.size() > 0��ʾ��ѧ���ش����⣬���ش�Ĵ𰸴������ݿ�
		if (StaticVariable.correct.size() > 0) {
			// ��һ��ѡ�����Ŀ
//			String question = StaticVariable.questionsMap.get(Integer.toString(index));
			String question = StaticVariable.questionsList.get(index - 1);
			// �ָ��ַ���������Ŀ�𰸵ȷ���
			String[] strs = question.split("#\\^");
			if ((strs.length - 3) > 0) {
				// (strs.length - 3) > 0��ʾ�ж����
				StringBuffer answer = new StringBuffer();	// �洢����Ĵ�
				StringBuffer corStr = new StringBuffer();	// �洢�ش���ȷ�ĸ����������ʹ�ÿո�ָ�
				StringBuffer errStr = new StringBuffer();	// �洢�ش����ĸ����������ʹ�ÿո�ָ�
				StringBuffer unResStr = new StringBuffer();	// �洢δ�ش�ĸ����������ʹ�ÿո�ָ�
				// ������ȷ�������ʹ�������������δ�ش������
				for (int i = 0; i < StaticVariable.correct.size(); i++) {
					int unResponse = StaticVariable.unResponse.get(i).intValue();
					int correct = StaticVariable.correct.get(i).intValue();
					int error = StaticVariable.error.get(i).intValue();
					unResponse = StaticVariable.users.size() - correct - error;
					StaticVariable.unResponse.set(i, new Integer(unResponse));
					if (i == 0) {
						answer.append(strs[i + 2]);
						corStr.append(correct);
						errStr.append(error);
						unResStr.append(unResponse);
					} else {
						answer.append(" " + strs[i + 2]);
						corStr.append(" " + correct);
						errStr.append(" " + error);
						unResStr.append(" " + unResponse);
					}
				}
				String countStr = answer.toString() + "," + corStr.toString() + "," + errStr.toString() + "," + unResStr.toString();
				StaticVariable.answers.put("ͳ��", countStr);
				System.out.println(countStr);
			} else {
				int unResponse = StaticVariable.unResponse.get(0).intValue();
				int correct = StaticVariable.correct.get(0).intValue();
				int error = StaticVariable.error.get(0).intValue();
				unResponse = StaticVariable.users.size() - correct - error;
				StaticVariable.unResponse.set(0, new Integer(unResponse));
				String countStr = Integer.toString(correct) + "," + Integer.toString(error) + "," + Integer.toString(unResponse);
				StaticVariable.answers.put("ͳ��", countStr);
			}
			System.out.println(StaticVariable.correct.size());
			dbService.addRecord(StaticVariable.tableName, StaticVariable.answers, index);
		}
		// ��ȡ��ǰѡ���е��±�
		index = StaticVariable.questionSelect.getSelectionIndex();
		// ��ȡ��ǰѡ���е��ı�
		String selected = StaticVariable.questionSelect.getText();
		if (index != 0) {
			StaticVariable.scoreChartBtn.setEnabled(true);
			// ��ȡ��Ŀ����
			String type = selected.substring(selected.indexOf("-") + 1);
			switch (type) {
			case "ѡ����":
				
				// ÿѡ��һ�⣬���ü�������
				StaticVariable.correct.clear();
				StaticVariable.error.clear();
				StaticVariable.unResponse.clear();
				// ���StringBuffer
				showQues.setLength(0);
				// ��ȡ��Ŀ
//				question = StaticVariable.questionsMap.get(Integer.toString(index));
				question = StaticVariable.questionsList.get(index - 1);
				// �ָ���Ŀ
				strs = question.split("#\\^");
				// ������Ŀ
				showQues.append("ѡ����:\n\n");
				showQues.append(strs[1]);
				for (int i = 3; i < strs.length; i++) {
					showQues.append("\n" + (char)option + "." + strs[i]);
					option++;
				}
				option = 65;
				// ��ʾ��Ŀ
				StaticVariable.text.setText(showQues.toString());
				// ������ʽ
				StaticVariable.text.setStyleRange(style);
				break;

			case "�Ƿ���":
				// ÿѡ��һ�⣬���ü�������
				StaticVariable.correct.clear();
				StaticVariable.error.clear();
				StaticVariable.unResponse.clear();
				showQues.setLength(0);
//				question = StaticVariable.questionsMap.get(Integer.toString(index));
				question = StaticVariable.questionsList.get(index - 1);
				strs = question.split("#\\^");
				showQues.append("�Ƿ���:\n\n");
				showQues.append(strs[1]);
				StaticVariable.text.setText(showQues.toString());
				StaticVariable.text.setStyleRange(style);
				break;
			case "�����":
				// ÿѡ��һ�⣬���ü�������
				StaticVariable.correct.clear();
				StaticVariable.error.clear();
				StaticVariable.unResponse.clear();
				showQues.setLength(0);
//				question = StaticVariable.questionsMap.get(Integer.toString(index));
				question = StaticVariable.questionsList.get(index - 1);
				strs = question.split("#\\^");
				showQues.append("�����:\n\n");
				showQues.append(strs[1]);
				StaticVariable.text.setText(showQues.toString());
				StaticVariable.text.setStyleRange(style);
				break;
			}
		} else {
			StaticVariable.scoreChartBtn.setEnabled(false);
			StaticVariable.text.setText("");
		}
	}

}
