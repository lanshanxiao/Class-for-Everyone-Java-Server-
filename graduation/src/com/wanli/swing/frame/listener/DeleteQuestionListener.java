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

	private int i;					// 删除题目的list下标
	private String openFile;		// 打开的文件名
	
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
		messageBox.setText("警告");
		messageBox.setMessage("确定要删除该题吗？");
		if (messageBox.open() == SWT.YES) {
			// 删除指定的题目
			StaticVariable.allQuestionList.remove(i);
			QuestionType qType;
			// 定义三个存储对应类型的list
			List<ChoiceQuestion> choiceList = new ArrayList<>();
			List<TrueOrFalse> tofList = new ArrayList<>();
			List<FillInTheBlanks> fbList = new ArrayList<>();
			// 遍历存储了所有题目的list，将题目进行分类
			for (int i = 0; i < StaticVariable.allQuestionList.size(); i++) {
				qType = (QuestionType) StaticVariable.allQuestionList.get(i);
				switch (qType.getType()) {
				// 选择题
				case "choice":
					choiceList.add((ChoiceQuestion)qType);
					break;
					// 是非题
				case "true_or_false":
					tofList.add((TrueOrFalse)qType);
					break;
					// 填空题
				case "fill_in_the_blanks":
					fbList.add((FillInTheBlanks)qType);
					break;
				}
			}
			// 定义要转为xml的JavaBean对象
			Question questions = new Question();
			questions.setChoiceList(choiceList);
			questions.setTrueOrFalseList(tofList);
			questions.setFillBlanksList(fbList);
			// 生成映射字符串
			String str = JavaBeanToXml.beanToXml(questions, Question.class);
			// 打开文件
			File file = new File(openFile);
			try {
				// 写入文件
				FileWriter writer = new FileWriter(file);
				StringBuffer fileString = new StringBuffer();
				fileString.append(str);
				writer.write(fileString.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			new XmlToJavaBean(file);
			// 更新窗口上的表格
			OpenFileDialogBtnListener.fillTable();
		}
	}

}
