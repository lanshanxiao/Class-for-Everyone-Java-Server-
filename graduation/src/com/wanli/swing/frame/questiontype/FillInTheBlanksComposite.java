package com.wanli.swing.frame.questiontype;

import org.eclipse.swt.widgets.Composite;

public class FillInTheBlanksComposite extends Composite {

	private Composite parent;
	
	public FillInTheBlanksComposite(Composite parent, int parameters) {
		super(parent, parameters);
		this.parent = parent;
		addComposite();
	}
	
	public void addComposite() {
		FillInTheBlanksComposite fillInTheBlanksCom = this;
		new QuestionBasic(parent, fillInTheBlanksCom);
	}

}
