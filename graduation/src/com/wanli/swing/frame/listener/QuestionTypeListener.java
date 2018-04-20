package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.wanli.swing.frame.questiontype.BeginningComposite;
import com.wanli.swing.frame.questiontype.ChoiceComposite;
import com.wanli.swing.frame.questiontype.FillInTheBlanksComposite;
import com.wanli.swing.frame.questiontype.TrueOrFalseComposite;
import com.wanli.utils.AddToQuestionList;
import com.wanli.utils.StaticVariable;

/**
 * 题型选择监听器
 * @author wanli
 *
 */
public class QuestionTypeListener implements SelectionListener {

	private Composite parent;
	private Combo questionTypeCombo;	// 选择题型下拉框
	private int index;					// 标记下拉框选中的项
	
	public QuestionTypeListener(Composite parent, Combo questionTypeCombo) {
		this.parent = parent;
		this.questionTypeCombo = questionTypeCombo;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		String select = "true";		// 标记是否切换题型
		index = questionTypeCombo.getSelectionIndex();
		switch (index) {
		case 0:
			// 清空所有的map
			clearAllMap();
			if (StaticVariable.nextOption != null) {
				StaticVariable.nextOption.dispose();
			}
			StaticVariable.questionCom.dispose();
			StaticVariable.questionCom = new BeginningComposite(parent, SWT.BORDER);
			// 重新布局parent，必须要有，否则无法显示
			parent.layout();
			break;

		// 选择题
		case 1:
			if (!StaticVariable.firstOpenPrepareLessonsShell) {
				// 当前已经是选择题类型
				if (StaticVariable.questionType != null && StaticVariable.questionType.equals("choice")) {
					// 判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.choiceAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("true_or_false")) {
					// 切换成是非题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.trueOrFalseAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("fill_in_the_blanks")) {
					// 切换成填空题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.fillblanksAllText);
				}
				StaticVariable.firstOpenPrepareLessonsShell = false;
			}
			if (select.equals("true")) {
				// 清空所有的map
				clearAllMap();
				StaticVariable.questionType = "choice";
				if (StaticVariable.nextOption != null) {
					StaticVariable.nextOption.dispose();
				}
				System.out.println("选择题");
				StaticVariable.questionCom.dispose();
				StaticVariable.questionCom = new ChoiceComposite(parent, SWT.BORDER);
				// 重新布局parent，必须要有，否则无法显示
				parent.layout();				
			}
			break;
			
		// 是非题
		case 2:
			if (!StaticVariable.firstOpenPrepareLessonsShell) {
				// 当前已经是是非题
				if (StaticVariable.questionType != null && StaticVariable.questionType.equals("true_or_false")) {
					// 判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.trueOrFalseAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("choice")) {
					// 切换成选择题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.choiceAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("fill_in_the_blanks")) {
					// 切换成填空题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.fillblanksAllText);
				}
				StaticVariable.firstOpenPrepareLessonsShell = false;
			}
			if (select.equals("true")) {
				// 清空所有的map
				clearAllMap();
				StaticVariable.questionType = "true_or_false";
				if (StaticVariable.nextOption != null) {
					StaticVariable.nextOption.dispose();
				}
				System.out.println("是非题");
				StaticVariable.questionCom.dispose();
				StaticVariable.questionCom = new TrueOrFalseComposite(parent, SWT.BORDER);
				// 重新布局parent，必须要有，否则无法显示
				parent.layout();				
			}
			break;
			
		// 填空题
		case 3:
			if (!StaticVariable.firstOpenPrepareLessonsShell) {
				// 当前已经是填空题
				if (StaticVariable.questionType != null && StaticVariable.questionType.equals("fill_in_the_blanks")) {
					// 判断当前的题有没有填写内容，有则询问是否放弃本题 
					select = AddToQuestionList.judgeTextValue(StaticVariable.fillblanksAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("choice")) {
					// 切换成选择题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.choiceAllText);
				} else if (StaticVariable.questionType != null && StaticVariable.questionType.equals("true_or_false")) {
					// 切换成是非题，判断当前的题有没有填写内容，有则询问是否放弃本题
					select = AddToQuestionList.judgeTextValue(StaticVariable.trueOrFalseAllText);
				}
				StaticVariable.firstOpenPrepareLessonsShell = false;
			}
			if (select.equals("true")) {
				clearAllMap();
				StaticVariable.questionType = "fill_in_the_blanks";
				if (StaticVariable.nextOption != null) {
					StaticVariable.nextOption.dispose();
				}
				System.out.println("填空题");
				StaticVariable.questionCom.dispose();
				StaticVariable.questionCom = new FillInTheBlanksComposite(parent, SWT.BORDER);
				// 重新布局parent，必须要有，否则无法显示
				parent.layout();				
			}
			break;
		}
	}

	/**
	 * 清空所有的map
	 */
	private void clearAllMap() {
		StaticVariable.choiceAllText.clear();
		StaticVariable.trueOrFalseAllText.clear();
		StaticVariable.fillblanksAllText.clear();
	}
	
}
