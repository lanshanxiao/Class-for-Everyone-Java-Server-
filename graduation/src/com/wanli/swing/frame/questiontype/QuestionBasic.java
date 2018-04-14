package com.wanli.swing.frame.questiontype;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.frame.listener.NextOptionListener;
import com.wanli.utils.StaticVariable;

public class QuestionBasic {

	private Composite parent;
	private Composite child;
	
	public QuestionBasic(Composite parent, Composite child) {
		this.parent = parent;
		this.child = child;
		StaticVariable.nextOption = addComposite();
	}
	
	public Button addComposite() {
		child.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		child.setLayoutData(choiceGrid);
		
		// 题目
		Label questionLabel = new Label(child, SWT.NONE);
		questionLabel.setText("题目：");
		Text questionText = new Text(child, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		// 设置questionText为充满式布局，且水平占两列
		GridData questionGrid = new GridData(GridData.FILL_BOTH);
		questionGrid.horizontalSpan = 2;
		questionText.setLayoutData(questionGrid);
		
		// 设置选项及答案的布局
		GridData optionGrid = new GridData(GridData.FILL_HORIZONTAL);

		// 答案
		Label answerLabel = new Label(child, SWT.NONE);
		answerLabel.setText("答案：");
		Text answerText = new Text(child, SWT.BORDER);
		answerText.setLayoutData(optionGrid);
		
		if (child instanceof TrueOrFalseComposite) {
			StaticVariable.trueOrFalseAllText.put("question", questionText);
			StaticVariable.trueOrFalseAllText.put("answer", answerText);
		}
		if (child instanceof FillInTheBlanksComposite) {
			StaticVariable.fillblanksAllText.put("question", questionText);
			StaticVariable.fillblanksAllText.put("answer", answerText);
		}
		
		// 下一题
		Button nextOption = new Button(parent, SWT.NONE);
		nextOption.setText("下一题");
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		nextGrid.horizontalSpan = 2;
		nextOption.setLayoutData(nextGrid);
		nextOption.addSelectionListener(new NextOptionListener());
		
		return nextOption;
	}
	
}
