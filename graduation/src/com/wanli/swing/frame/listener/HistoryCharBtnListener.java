package com.wanli.swing.frame.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.wanli.swing.frame.MainFrame;
import com.wanli.utils.CharTableUtil;

public class HistoryCharBtnListener implements SelectionListener {

	private Composite parent;
	private String tableName = null;
	
	public HistoryCharBtnListener(Composite parent) {
		this.parent = parent;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		tableName = MainFrame.historyCombo.getText();
		if (tableName != null) {
			new CharTableUtil(parent.getShell(), tableName).open();
		}
	}

}
