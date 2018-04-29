package com.wanli.swing.frame.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.FillInTheBlanks;
import com.wanli.swing.entities.Question;
import com.wanli.swing.entities.QuestionType;
import com.wanli.swing.entities.TrueOrFalse;
import com.wanli.utils.JavaBeanToXml;
import com.wanli.utils.StaticVariable;
import com.wanli.utils.XmlToJavaBean;

public class DeleteQuestionListener implements SelectionListener {

	private int i;					// ɾ����Ŀ��list�±�
	private String openFile;		// �򿪵��ļ���
	
	public DeleteQuestionListener(int i, String openFile) {
		this.i = i;
		this.openFile = openFile;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		MessageBox messageBox = new MessageBox(StaticVariable.parent.getShell(), SWT.YES | SWT.NO);
		messageBox.setText("����");
		messageBox.setMessage("ȷ��Ҫɾ��������");
		if (messageBox.open() == SWT.YES) {
			// ɾ��ָ������Ŀ
			StaticVariable.allQuestionList.remove(i);
			QuestionType qType;
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
			new XmlToJavaBean(file);
			// ���´����ϵı��
			OpenFileDialogBtnListener.fillTable();
		}
	}

}
