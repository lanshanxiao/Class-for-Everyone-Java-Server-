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
import com.wanli.utils.StaticVariable;

public class QuestionTypeListener implements SelectionListener {

	private Composite parent;
	private Combo questionTypeCombo;
	private int index;
	
	public QuestionTypeListener(Composite parent, Combo questionTypeCombo) {
		this.parent = parent;
		this.questionTypeCombo = questionTypeCombo;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		index = questionTypeCombo.getSelectionIndex();
		switch (index) {
		case 0:
			if (StaticVariable.nextOption != null) {
				StaticVariable.nextOption.dispose();
			}
			StaticVariable.questionCom.dispose();
			StaticVariable.questionCom = new BeginningComposite(parent, SWT.BORDER);
			// 重新布局parent，必须要有，否则无法显示
			parent.layout();
			break;

		case 1:
			StaticVariable.questionType = "choice";
			if (StaticVariable.nextOption != null) {
				StaticVariable.nextOption.dispose();
			}
			System.out.println("选择题");
			StaticVariable.questionCom.dispose();
			StaticVariable.questionCom = new ChoiceComposite(parent, SWT.BORDER);
			// 重新布局parent，必须要有，否则无法显示
			parent.layout();
			break;
			
		case 2:
			StaticVariable.questionType = "true_or_false";
			if (StaticVariable.nextOption != null) {
				StaticVariable.nextOption.dispose();
			}
			System.out.println("是非题");
			StaticVariable.questionCom.dispose();
			StaticVariable.questionCom = new TrueOrFalseComposite(parent, SWT.BORDER);
			// 重新布局parent，必须要有，否则无法显示
			parent.layout();
			break;
			
		case 3:
			StaticVariable.questionType = "fill_in_the_blanks";
			if (StaticVariable.nextOption != null) {
				StaticVariable.nextOption.dispose();
			}
			System.out.println("填空题");
			StaticVariable.questionCom.dispose();
			StaticVariable.questionCom = new FillInTheBlanksComposite(parent, SWT.BORDER);
			// 重新布局parent，必须要有，否则无法显示
			parent.layout();
			break;
		}
	}

}
