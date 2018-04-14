package com.wanli.swing.frame.questiontype;

import org.eclipse.swt.widgets.Composite;

public class TrueOrFalseComposite extends Composite {

	private Composite parent;
	
	public TrueOrFalseComposite(Composite parent, int parameters) {
		super(parent, parameters);
		this.parent = parent;
		addComposite();
	}
	
	public void addComposite() {
		TrueOrFalseComposite trueOrFalseCom = this;
		new QuestionBasic(parent, trueOrFalseCom);
	}
}
