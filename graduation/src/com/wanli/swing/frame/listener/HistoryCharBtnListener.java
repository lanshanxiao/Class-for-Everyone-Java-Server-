package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import com.wanli.utils.CharTableUtil;
import com.wanli.utils.StaticVariable;

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
		System.out.println(StaticVariable.historyCombo.getText());
		tableName = StaticVariable.historyCombo.getText();
		if (tableName != null && tableName != "") {
			new CharTableUtil(parent.getShell(), tableName).open();
		} else {
			MessageBox messageBox = new MessageBox(parent.getShell(), SWT.YES);
    		messageBox.setText("图表");
    		messageBox.setMessage("请先选择一张历史表！");
    		messageBox.open();
		}
	}

}
