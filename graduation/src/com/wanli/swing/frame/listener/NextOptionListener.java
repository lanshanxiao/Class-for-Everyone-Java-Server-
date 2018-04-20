package com.wanli.swing.frame.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.FillInTheBlanks;
import com.wanli.swing.entities.TrueOrFalse;
import com.wanli.utils.AddToQuestionList;
import com.wanli.utils.StaticVariable;

public class NextOptionListener extends SelectionAdapter {

	private List<String> optionKeys = new ArrayList<>();// 存储StaticVariable.choiceAllText所有选项的key值
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// 先清空optionKeys，否则会影响下一个选择题的选项
		optionKeys.clear();
		// 判断是否所有输入框都有填写，默认为false
		boolean hasEmpty = false;
		switch (StaticVariable.questionType) {
		// 选择题
		case "choice":
			AddToQuestionList.addToChoiceList(optionKeys, hasEmpty);
			break;
		
		// 是非题
		case "true_or_false":
			AddToQuestionList.addToTrueOrFalseList(hasEmpty);
			break;
			
		case "fill_in_the_blanks":
			AddToQuestionList.addToFillBlanks(hasEmpty);
			break;
			
		}
	}
	
}
