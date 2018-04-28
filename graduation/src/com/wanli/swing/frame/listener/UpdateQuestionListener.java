package com.wanli.swing.frame.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.wanli.swing.frame.UpdateQuestionShell;
import com.wanli.utils.StaticVariable;

/**
 * 修改监听器
 * @author wanli
 *
 */
public class UpdateQuestionListener implements SelectionListener {

	private int i = 0;			// list的下标
	private String openFile;	// 打开的文件名
	
	public UpdateQuestionListener(int i, String openFile) {
		this.i = i;
		this.openFile = openFile;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
//		System.out.println("修改问题");
//		System.out.println(StaticVariable.allQuestionList.get(i));
		new UpdateQuestionShell(StaticVariable.parent, i, openFile);
	}
}
