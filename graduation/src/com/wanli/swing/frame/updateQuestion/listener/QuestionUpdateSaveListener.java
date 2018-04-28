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
 * 保存修改按钮监听器
 * @author wanli
 *
 */
public class QuestionUpdateSaveListener implements SelectionListener {

	private int i;				// 修改的题目的list下标
	private String openFile;	// 打开的文件名
	private String type;		// 修改的题目类型
	
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
		// 获取题目
		String question;
		// 获取答案
		String answer;
		if (type.equals("choice")) {
			// 存储所有选择题的选项的key值
			List<String> optionKeys = new ArrayList<>();
			// 获取题目
			question = StaticVariable.choiceAllText.get("question").getText();
			// 获取答案
			answer = StaticVariable.choiceAllText.get("answer").getText();
			// 获取所有选项的键值
			List<String> options = new ArrayList<>();
			for (String key : StaticVariable.choiceAllText.keySet()) {
				if (!key.equals("answer") && !key.equals("question")) {
					optionKeys.add(key);
				}
			}
			// 对所有键值进行排序
			Collections.sort(optionKeys);
			for (String key : optionKeys) {
				options.add(StaticVariable.choiceAllText.get(key).getText());
			}
			
			// 若是选择题，则向下转型为ChoiceQuestion
			ChoiceQuestion choiceQues = (ChoiceQuestion) qType;
			choiceQues.setQuestion(question);
			choiceQues.setAnswer(answer);
			choiceQues.setOptions(options);
			// 移除之前的，添加已修改的
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, choiceQues);
		} else if (type.equals("true_or_false")) {
			// 若是是非题，则向下转型为TrueOrFalse
			TrueOrFalse tofQues = (TrueOrFalse)qType;
			// 获取题目
			question = StaticVariable.trueOrFalseAllText.get("question").getText();
			// 获取答案
			answer = StaticVariable.trueOrFalseAllText.get("answer").getText();
			tofQues.setQuestion(question);
			tofQues.setAnswer(answer);
			// 移除之前的，添加已修改的
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, tofQues);
		} else {
			// 若是填空题，则向下转型为FillInTheBlanks
			FillInTheBlanks fbQues = (FillInTheBlanks)qType;
			// 获取题目
			question = StaticVariable.fillblanksAllText.get("question").getText();
			// 获取答案
			answer = StaticVariable.fillblanksAllText.get("answer").getText();
			fbQues.setQuestion(question);
			String[] answers = answer.split(" ");
			List<String> answerList = new ArrayList<>();
			// answers.length > 1 表示有多个答案
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
			// 移除之前的，添加已修改的
			StaticVariable.allQuestionList.remove(i);
			StaticVariable.allQuestionList.add(i, fbQues);
		}
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
		// 获取当前所有窗体的个数
		int currentShells = Display.getCurrent().getShells().length;
		// 关闭当前窗口
		Display.getCurrent().getShells()[currentShells - 1].dispose();
		// 重新转换xml文件
		new XmlToJavaBean(file);
		// 更新窗口上的表格
		OpenFileDialogBtnListener.fillTable();
	}
	
}
