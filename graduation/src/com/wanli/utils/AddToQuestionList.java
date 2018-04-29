package com.wanli.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.FillInTheBlanks;
import com.wanli.swing.entities.TrueOrFalse;

/**
 * �������д洢���͵�List(choiceList, trueOrFalseList, fillblanksList)
 * @author wanli
 *
 */
public class AddToQuestionList {

	// ���ѡ����
	public static void addToChoiceList(List<String> optionKeys, boolean hasEmpty) {
		// �ж��Ƿ��������û����д
		hasEmpty = judgeTextEmpty(StaticVariable.choiceAllText, hasEmpty);
		if (!hasEmpty) {
			choiceList(optionKeys);
		}
		if (!hasEmpty) {
			// �������������Ա���һ������
			setAllTextEmpty(StaticVariable.choiceAllText);			
		}
	}
	
	public static void choiceList(List<String> optionKeys) {
		// ȫ����д
		// ��Ŀ��������1
		StaticVariable.creQuesIndex++;
		// ��ȡ��Ŀ
		String question = StaticVariable.choiceAllText.get("question").getText();
		// ��ȡ��
		String answer = StaticVariable.choiceAllText.get("answer").getText();
		// ��ȡ����ѡ��ļ�ֵ
		List<String> options = new ArrayList<>();
		for (String key : StaticVariable.choiceAllText.keySet()) {
			if (!key.equals("answer") && !key.equals("question")) {
				optionKeys.add(key);
			}
		}
		// �����м�ֵ��������
		Collections.sort(optionKeys);
		for (String key : optionKeys) {
			options.add(StaticVariable.choiceAllText.get(key).getText());
		}
		// �����ɵ�һ��ѡ�������StaticVariable.choiceList
		StaticVariable.choiceList
				.add(new ChoiceQuestion(Integer.toString(StaticVariable.creQuesIndex), question, answer, options));
		// // ����
		// for (ChoiceQuestion cq: StaticVariable.choiceList) {
		// System.out.println(cq);
		// }
	}
	
	// ����Ƿ���
	public static void addToTrueOrFalseList(boolean hasEmpty) {
		hasEmpty = judgeTextEmpty(StaticVariable.trueOrFalseAllText, hasEmpty);
		if (!hasEmpty) {
			trueOrFalseList();
		}
		if (!hasEmpty) {
			setAllTextEmpty(StaticVariable.trueOrFalseAllText);			
		}
	}
	
	public static void trueOrFalseList() {
		StaticVariable.creQuesIndex++;
		String question = StaticVariable.trueOrFalseAllText.get("question").getText();
		String answer = StaticVariable.trueOrFalseAllText.get("answer").getText();
		// �����ɵ�һ���Ƿ��������StaticVariable.trueOrFalseList
		StaticVariable.trueOrFalseList.add(new TrueOrFalse(Integer.toString(StaticVariable.creQuesIndex), question, answer));
//		// ����
//		for (TrueOrFalse tof: StaticVariable.trueOrFalseList) {
//			System.out.println(tof);
//		}
	}
	
	// ��������
	public static void addToFillBlanks(boolean hasEmpty) {
		hasEmpty = judgeTextEmpty(StaticVariable.fillblanksAllText, hasEmpty);
		if (!hasEmpty) {
			fillBlanks();
			if (!hasEmpty) {
				setAllTextEmpty(StaticVariable.fillblanksAllText);				
			}
		}
	}
	
	public static void fillBlanks() {
		StaticVariable.creQuesIndex++;
		String question = StaticVariable.fillblanksAllText.get("question").getText();
		String answer = StaticVariable.fillblanksAllText.get("answer").getText();
		// �����𰸿����ж����ͨ���ո����𰸽��зָ�
		String[] answers = answer.split(" ");
		List<String> answerList = new ArrayList<>();
		// answers.length > 1 ��ʾ�ж����
		if (answers.length > 1) {
			for (int i = 0; i < answers.length; i++) {
				if (!answers[i].equals("")) {
					answerList.add(answers[i]);
				}
			}					
		} else {
			answerList.add(answer);
		}
//		// ����
//		System.out.println(Arrays.toString(answers));
//		System.out.println(answerList);
		StaticVariable.fillblanksList.add(new FillInTheBlanks(Integer.toString(StaticVariable.creQuesIndex), question, answerList));
//		// ����
//		for (FillInTheBlanks ftb: StaticVariable.fillblanksList) {
//			System.out.println(ftb);
//		}
	}
	
	/**
	 * �жϴ�����Ŀʱ�Ƿ���text���Ϊ��
	 * @param map
	 * @param hasEmpty
	 */
	private static boolean judgeTextEmpty(Map<String, Text> map, boolean hasEmpty) {
		for (Map.Entry<String, Text> inputText: map.entrySet()) {
			if (inputText.getValue().getText() == null || inputText.getValue().getText() == "" || inputText.getValue().getText().equals("���ж���գ������ÿո�ָ���")) {
				MessageBox messageBox = new MessageBox(StaticVariable.parent.getShell(), SWT.YES);
				messageBox.setText("����");
				messageBox.setMessage("���п��ж�����Ϊ�գ�����");
				messageBox.open();
				hasEmpty = true;
				break;
			}
		}
		return hasEmpty;
	}
	
	/**
	 * ������е�������
	 * @param map
	 */
	private static void setAllTextEmpty(Map<String, Text> map) {
		for (Map.Entry<String, Text> inputText: map.entrySet()) {
			inputText.getValue().setText("");
		}
	}
	
	/**
	 * �жϴ�����Ŀʱ�Ƿ���text���Ϊ��
	 * @param map
	 * @param hasEmpty
	 */
	public static String judgeTextEmpty(Map<String, Text> map) {
		for (Map.Entry<String, Text> inputText: map.entrySet()) {
			if (inputText.getValue().getText() == null || inputText.getValue().getText() == "" || inputText.getValue().getText().equals("���ж���գ������ÿո�ָ���")) {
				MessageBox messageBox = new MessageBox(StaticVariable.parent.getShell(), SWT.YES | SWT.NO);
				messageBox.setText("����");
				messageBox.setMessage("���п���δ��д���Ƿ�������⣿");
				if (messageBox.open() == SWT.YES) {
					return "true";
				} else {
					return "fasle";
				}
			}
		}
		return "create";
	}
	
	/**
	 * �ж�text����Ƿ�������д����
	 * @param map
	 * @return
	 */
	public static String judgeTextValue(Map<String, Text> map) {
		for (Map.Entry<String, Text> inputText: map.entrySet()) {
			if (inputText.getValue().getText() != null && inputText.getValue().getText() != "") {
				MessageBox messageBox = new MessageBox(StaticVariable.parent.getShell(), SWT.YES | SWT.NO);
				messageBox.setText("����");
				messageBox.setMessage("�Ƿ�������⣿");
				if (messageBox.open() == SWT.YES) {
					return "true";
				} else {
					return "fasle";
				}
			}
		}
		return "true";
	}

}
