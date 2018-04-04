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

import com.wanli.swing.frame.MainFrame;

/**
 * 新建教室对话框
 * @author wanli
 *
 */
public class CreateClassListener extends SelectionAdapter {

	private Composite parent;
	
	public CreateClassListener(Composite parent) {
		this.parent = parent;
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Category Axis");
        shell.setSize(300, 150);
        center(shell.getDisplay(), shell);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 20;
        gridLayout.verticalSpacing = 20;
        gridLayout.marginLeft = 40;
        gridLayout.marginRight = 40;
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
					MainFrame.className = inputName.getText();
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
				MainFrame.className = "";
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
