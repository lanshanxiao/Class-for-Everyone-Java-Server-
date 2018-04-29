package com.wanli.swing.frame.updateQuestion;

import org.eclipse.swt.widgets.Composite;

public class UpdateTrueOrFalseComposite extends Composite {

	private Composite parent;
	private int i;
	private String openFile;
	
	public UpdateTrueOrFalseComposite(Composite parent, int parameters, int i, String openFile) {
		super(parent, parameters);
		this.parent = parent;
		this.i = i;
		this.openFile = openFile;
		addComposite();
	}
	
	// ����Ƿ�������
	public void addComposite() {
		UpdateTrueOrFalseComposite trueOrFalseCom = this;
		new UpdateQuestionBasic(parent, trueOrFalseCom, i, openFile);
	}
}