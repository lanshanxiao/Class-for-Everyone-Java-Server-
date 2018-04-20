package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.frame.MessageShell;
import com.wanli.utils.StaticVariable;

public class AskQuestionTableListener extends MouseAdapter {

	private Composite parent;		// 存储一切与主窗口相关的信息
	private String question = "";	// 存储学生提出的问题
	public static int index;		// 标记表格选中的行
	private Menu menu = null;		// 右键菜单
	
	public AskQuestionTableListener(Composite parent) {
		this.parent = parent;
	}
	
	@Override
	public void mouseDown(MouseEvent event) {
		mouseRightDown(event);
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		index = StaticVariable.askQuestions.getSelectionIndex();
		question = StaticVariable.askQuestions.getItem(index).getText();
		// 执行窗口弹出
		new MessageShell(parent.getShell(), question).open();		
	}
	
	private void mouseRightDown(MouseEvent event) {
		// 清除右键菜单，否则点击空白处也会弹出右键菜单
		if (menu != null) {
			menu.dispose();
		}
		// 获取选中的控件
		TableItem selected = StaticVariable.askQuestions.getItem(new Point(event.x, event.y));
		// 如果取到行，且是鼠标右键点击
		if (selected != null && event.button == 3) {
			// 添加右键菜单
			menu = new Menu(StaticVariable.askQuestions);
			MenuItem delItem = new MenuItem(menu, SWT.PUSH);
			delItem.setText("删除");
			delItem.addSelectionListener(new SelectionAdapter() {
				@Override
	        	public void widgetSelected(SelectionEvent e) {
	        		MessageBox messageBox = new MessageBox(parent.getShell(), SWT.YES | SWT.NO);
	        		messageBox.setText("删除问题");
	        		messageBox.setMessage("确定要删除这个问题吗？");
	        		if (messageBox.open() == SWT.YES) {
	        			StaticVariable.unanswerMap.remove(selected);
	        			if (StaticVariable.unanswerMap.size() <= 0) {
	        				StaticVariable.askQuestion.setText("提问");
	        			} else {
	        				StaticVariable.askQuestion.setText(StaticVariable.unanswerMap.size() + " 提问");	        				
	        			}
		        		selected.dispose();
	        		}
	        	}
			});
			MenuItem readItem = new MenuItem(menu, SWT.PUSH);
			readItem.setText("标记为已读");
			readItem.addSelectionListener(new SelectionAdapter() {
				@Override
	        	public void widgetSelected(SelectionEvent e) {
//					int index = AskQuestionTableListener.index;
					StaticVariable.unanswerMap.remove(StaticVariable.askQuestions.getItem(index));
        			if (StaticVariable.unanswerMap.size() <= 0) {
        				StaticVariable.askQuestion.setText("提问");
        			} else {
        				StaticVariable.askQuestion.setText(StaticVariable.unanswerMap.size() + " 提问");	        				
        			}
        			TableItem item = StaticVariable.askQuestions.getItem(index);
    				item.setImage(new Image(StaticVariable.parent.getDisplay(), "image/answered.png"));
	        	}
			});
			StaticVariable.askQuestions.setMenu(menu);
		}
	}

}
