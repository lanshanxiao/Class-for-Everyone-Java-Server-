package com.wanli.swing.frame.updateQuestion.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.FillInTheBlanks;
import com.wanli.swing.entities.Question;
import com.wanli.swing.entities.QuestionType;
import com.wanli.swing.entities.TrueOrFalse;
import com.wanli.swing.frame.listener.OpenFileDialogBtnListener;
import com.wanli.utils.JavaBeanToXml;
import com.wanli.utils.StaticVariable;
import com.wanli.utils.XmlToJavaBean;

/**
 * �����޸İ�ť������
 * @author wanli
 *
 */
public class QuestionUpdateSaveListener implements SelectionListener {

	private int i;				// �޸ĵ���Ŀ��list�±�
	private String openFile;	// �򿪵��ļ���
	private String type;		// �޸ĵ���Ŀ����
	
	public QuestionUpdateSaveListener(int i, String openFile, String type) {
		this.i = i;
		this.openFile = openFile;
		this.type = type;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		QuestionType qType = (QuestionType) StaticVariable.allQuestionList.get(i);
		// ��ȡ��Ŀ
		String question;
		// ��ȡ��
		String answer;
		if (type.equals("choice")) {
			// �洢����ѡ�����ѡ���keyֵ
			List<String> optionKeys = new ArrayList<>();
			// ��ȡ��Ŀ
			question = StaticVariable.choiceAllText.get("question").getText();
			// ��ȡ��
			answer = StaticVariable.choiceAllText.get("answer").getText();
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
			
			// ����ѡ���⣬������ת��ΪChoiceQuestion
			ChoiceQuestion choiceQues = (ChoiceQuestion) qType;
			choiceQues.setQuestion(question);
			choiceQues.setAnswer(answer);
			choiceQues.setOptions(options);
			// �Ƴ�֮ǰ�ģ�������޸ĵ�
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, choiceQues);
		} else if (type.equals("true_or_false")) {
			// �����Ƿ��⣬������ת��ΪTrueOrFalse
			TrueOrFalse tofQues = (TrueOrFalse)qType;
			// ��ȡ��Ŀ
			question = StaticVariable.trueOrFalseAllText.get("question").getText();
			// ��ȡ��
			answer = StaticVariable.trueOrFalseAllText.get("answer").getText();
			tofQues.setQuestion(question);
			tofQues.setAnswer(answer);
			// �Ƴ�֮ǰ�ģ�������޸ĵ�
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, tofQues);
		} else {
			// ��������⣬������ת��ΪFillInTheBlanks
			FillInTheBlanks fbQues = (FillInTheBlanks)qType;
			// ��ȡ��Ŀ
			question = StaticVariable.fillblanksAllText.get("question").getText();
			// ��ȡ��
			answer = StaticVariable.fillblanksAllText.get("answer").getText();
			fbQues.setQuestion(question);
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
			fbQues.setAnswer(answerList);
			// �Ƴ�֮ǰ�ģ�������޸ĵ�
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, fbQues);
		}
		// ���������洢��Ӧ���͵�list
		List<ChoiceQuestion> choiceList = new ArrayList<>();
		List<TrueOrFalse> tofList = new ArrayList<>();
		List<FillInTheBlanks> fbList = new ArrayList<>();
		// �����洢��������Ŀ��list������Ŀ���з���
		for (int i = 0; i < StaticVariable.allQuestionList.size(); i++) {
			qType = (QuestionType) StaticVariable.allQuestionList.get(i);
			switch (qType.getType()) {
			// ѡ����
			case "choice":
				choiceList.add((ChoiceQuestion)qType);
				break;
			// �Ƿ���
			case "true_or_false":
				tofList.add((TrueOrFalse)qType);
				break;
			// �����
			case "fill_in_the_blanks":
				fbList.add((FillInTheBlanks)qType);
				break;
			}
		}
		// ����ҪתΪxml��JavaBean����
		Question questions = new Question();
		questions.setChoiceList(choiceList);
		questions.setTrueOrFalseList(tofList);
		questions.setFillBlanksList(fbList);
		// ����ӳ���ַ���
		String str = JavaBeanToXml.beanToXml(questions, Question.class);
		// ���ļ�
		File file = new File(openFile);
		try {
			// д���ļ�
			FileWriter writer = new FileWriter(file);
			StringBuffer fileString = new StringBuffer();
			fileString.append(str);
			writer.write(fileString.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ��ȡ��ǰ���д���ĸ���
		int currentShells = Display.getCurrent().getShells().length;
		// �رյ�ǰ����
		Display.getCurrent().getShells()[currentShells - 1].dispose();
		// ����ת��xml�ļ�
		new XmlToJavaBean(file);
		// ���´����ϵı��
		OpenFileDialogBtnListener.fillTable();
	}
	
}
