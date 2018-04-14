package com.wanli.swing.frame.questiontype;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BeginningComposite extends Composite {
	
	private Composite parent;

	public BeginningComposite(Composite parent, int parameters) {
		super(parent, parameters);
		this.parent = parent;
		addComposite();
	}

	public void addComposite() {
		BeginningComposite beginningCom = this;
		this.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		this.setLayoutData(choiceGrid);
		
		Label tip = new Label(this, SWT.CENTER);
		tip.setText("请先选择添加的题型！！！");
		tip.setFont(new Font(parent.getDisplay(), "Arial", 20, SWT.NONE));
	}
	
}
