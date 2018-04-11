//package com.wanli.swing.frame.listener;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.widgets.Tree;
//import org.eclipse.swt.widgets.TreeItem;
//
////该类没有被使用
//
//public class CreateClassListener extends SelectionAdapter {
//
//	private Tree tree;
//	private String userName;
//	private int number = 1;
//	
//	public CreateClassListener(Tree tree, String userName) {
//		this.tree = tree;
//		this.userName = userName;
//	}
//	
//	public void createClass() {
//		TreeItem classroom = new TreeItem(tree, SWT.NONE);
//		classroom.setText(userName + number);
//		number++;
//	}
//	
//	@Override
//	public void widgetSelected(SelectionEvent arg0) {
//		
//		TreeItem classroom = new TreeItem(tree, SWT.NONE);
//		classroom.setText(userName + number);
//		number++;
//	}
//	
//}
package com.wanli.swing.frame.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.wanli.utils.StaticVariable;

/**
 * 新建教室对话框
 * @author wanli
 *
 */
public class CreateClassListener extends SelectionAdapter {

	private Composite parent;	// 存储一切与主窗口相关的信息
	
	public CreateClassListener(Composite parent) {
		this.parent = parent;
		// 执行窗口弹出
		new CreateClassShell(parent.getShell()).open();
	}
	
	@Override
	public void widgetSelected(SelectionEvent arg0) {
	}
	
}

class CreateClassShell extends Dialog {
	protected Object result;
	protected Shell shell;
	public CreateClassShell(Shell shell) {
		super(shell);	
	}
	
	public Object open() {
		createContents(); 
		shell.open();  
        shell.layout();  
        Display display = getParent().getDisplay();  
        while (!shell.isDisposed()) {  
            if (!display.readAndDispatch())  
                display.sleep();  
        }  
        return result;
	}
	
	protected void createContents() {
		// 创建一个窗口
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("创建教室");
        shell.setSize(300, 150);
        // 使窗口居中显示
        center(shell.getDisplay(), shell);
        // 定义一个网格布局
        GridLayout gridLayout = new GridLayout(2, false);
        // 该布局中组件距离上边框的边距为20
        gridLayout.marginHeight = 20;
        // 该布局中组件之间的垂直间隔为20
        gridLayout.verticalSpacing = 20;
        // 该布局中组件距离左边框的边距为40
        gridLayout.marginLeft = 40;
        // 该布局中组件距离右边框的边距为40
        gridLayout.marginRight = 40;
        // 设置窗口布局
        shell.setLayout(gridLayout);
        createClass(shell);
	}
	
	protected void createClass(Composite parent) {
		Label className = new Label(parent, SWT.NONE);
		className.setText("教室名称：");
		Text inputName = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		inputName.setLayoutData(gridData);
		Button confirm = new Button(parent, SWT.NONE);
		confirm.setText(" 确      认 ");
		confirm.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// 没有输入教室名则弹出提示框
				if (inputName.getText().trim() == "") {
					MessageBox messageBox = new MessageBox(parent.getShell());
					messageBox.setMessage("请输入教室名称");
					messageBox.open();
				} else {
					StaticVariable.className = inputName.getText();
					shell.dispose();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		// 设置窗口默认绑定的按钮为confirm按钮
		shell.setDefaultButton(confirm);
		inputName.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}
			
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				// 按回车即可触发确认按钮事件
				if (keyEvent.keyCode == SWT.CR) {
					shell.setDefaultButton(confirm);
				}
			}
		});
		Button cancle = new Button(parent, SWT.NONE);
		cancle.setText(" 取     消 ");
		// 设置取消按钮水平右对齐
		GridData cancleGrid = new GridData(GridData.HORIZONTAL_ALIGN_END);
		cancle.setLayoutData(cancleGrid);
		cancle.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				StaticVariable.className = "";
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}
	
	/**
	 * 使对话框居中显示
	 * @param display
	 * @param shell
	 */
	protected void center(Display display, Shell shell) {
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}
	
}

