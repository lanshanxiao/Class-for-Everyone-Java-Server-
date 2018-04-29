package com.wanli.swing.frame.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.wanli.swing.entities.Question;
import com.wanli.utils.AddToQuestionList;
import com.wanli.utils.JavaBeanToXml;
import com.wanli.utils.StaticVariable;

/**
 * ����Xml�ļ�
 * @author wanli
 *
 */
public class CreateXmlFileBtnListener implements SelectionListener {

	private Question questions;							// Question�࣬ӳ���xml�ļ�
	private String create;								// ����Ƿ񴴽�xml�ļ�
	private List<String> optionKeys = new ArrayList<>();// �洢StaticVariable.choiceAllText����ѡ���keyֵ
	private Shell shell;
	
	public CreateXmlFileBtnListener(Shell shell) {
		this.shell = shell;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		if (StaticVariable.questionType == null) {
			MessageBox messageBox = new MessageBox(shell, SWT.YES);
			messageBox.setText("����");
			messageBox.setMessage("��û������κε���Ŀ���޷�������");
			messageBox.open();
		} else if (StaticVariable.questionType.equals("choice")) {
			// �ж����һ�������
			// �ж��Ƿ��п���û����д������ѯ���Ƿ��������
			create = AddToQuestionList.judgeTextEmpty(StaticVariable.choiceAllText);
			if (create.equals("create")) {
				AddToQuestionList.choiceList(optionKeys);
			}
		} else if (StaticVariable.questionType.equals("true_or_false")) {
			create = AddToQuestionList.judgeTextEmpty(StaticVariable.trueOrFalseAllText);
			if (create.equals("create")) {
				AddToQuestionList.trueOrFalseList();
			}
		} else {
			create = AddToQuestionList.judgeTextEmpty(StaticVariable.fillblanksAllText);
			if (create.equals("create")) {
				AddToQuestionList.fillBlanks();
			}
		}
		if (create != null) {
			if (create.equals("create") || create.equals("true")) {
				if (StaticVariable.choiceList.size() > 0 || StaticVariable.trueOrFalseList.size() > 0 || StaticVariable.fillblanksList.size() > 0) {
					questions = new Question();
					questions.setChoiceList(StaticVariable.choiceList);
					questions.setTrueOrFalseList(StaticVariable.trueOrFalseList);
					questions.setFillBlanksList(StaticVariable.fillblanksList);
					// ����ӳ���ַ���
					String str = JavaBeanToXml.beanToXml(questions, Question.class);
					// д�뵽xml�ļ���
					boolean save = saveXmlFile(str);
					StaticVariable.choiceList.clear();
					StaticVariable.trueOrFalseList.clear();
					StaticVariable.fillblanksList.clear();
					StaticVariable.creQuesIndex = 0;
					if (save) {
						shell.dispose();				
					}
				} else {
					MessageBox messageBox = new MessageBox(shell, SWT.YES);
					messageBox.setText("����");
					messageBox.setMessage("��û������κε���Ŀ���޷�������");
					messageBox.open();
				}	
			}			
		}
	}

    /**
     * �����xml�ļ�
     * @param str
     * @return
     */
    private boolean saveXmlFile(String str) {
    	boolean save = false;
    	// �����ļ��Ի���
    	FileDialog dialog = new FileDialog(StaticVariable.parent.getShell(), SWT.SAVE);
    	dialog.setText("����");
    	// ���öԻ��򱣴���޶�����,�����xml�ļ�
    	dialog.setFilterExtensions(new String[] {"*.xml"});
    	// �򿪶Ի��򣬲����ر����ļ���·��
    	String saveFile = dialog.open();
    	if (saveFile != null && saveFile != "") {
    		save = true;
    		File file = new File(saveFile);
    		try {
    			FileWriter writer = new FileWriter(file);
    			StringBuffer fileString = new StringBuffer();
    			fileString.append(str);
    			writer.write(fileString.toString());
    			writer.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}    		
    	}
    	return save;
    }
    
}
