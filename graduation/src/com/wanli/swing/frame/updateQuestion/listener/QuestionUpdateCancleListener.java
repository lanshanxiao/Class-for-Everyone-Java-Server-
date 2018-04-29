package com.wanli.swing.frame.updateQuestion.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

public class QuestionUpdateCancleListener implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		int currentShells = Display.getCurrent().getShells().length;
		Display.getCurrent().getShells()[currentShells - 1].dispose();
	}

}
