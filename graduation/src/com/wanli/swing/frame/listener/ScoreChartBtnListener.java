package com.wanli.swing.frame.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.wanli.utils.CharTableUtil;
import com.wanli.utils.StaticVariable;

public class ScoreChartBtnListener implements SelectionListener {
	private Composite parent;
	public ScoreChartBtnListener(Composite parent) {
		this.parent = parent;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		new CharTableUtil(parent.getShell(), StaticVariable.tableName).open();
	}
}

