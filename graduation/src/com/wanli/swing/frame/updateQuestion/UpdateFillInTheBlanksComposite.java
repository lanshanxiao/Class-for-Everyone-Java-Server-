package com.wanli.swing.frame.updateQuestion;

import org.eclipse.swt.widgets.Composite;

public class UpdateFillInTheBlanksComposite extends Composite {

	private Composite parent;
	private int i;
	private String openFile;
	
	public UpdateFillInTheBlanksComposite(Composite parent, int parameters, int i, String openFile) {
		super(parent, parameters);
		this.parent = parent;
		this.i = i;
		this.openFile = openFile;
		addComposite();
	}
	
	public void addComposite() {
		UpdateFillInTheBlanksComposite fillInTheBlanksCom = this;
		new UpdateQuestionBasic(parent, fillInTheBlanksCom, i, openFile);
	}

}
