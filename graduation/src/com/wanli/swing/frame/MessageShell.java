package com.wanli.swing.frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.frame.listener.AskQuestionTableListener;
import com.wanli.utils.StaticVariable;

public class MessageShell extends Dialog {
	// 设置窗口默认大小
	private static int SHELL_WIDTH = 400;
	private static int SHELL_HEIGHT = 400;
	protected Object result;
	protected Shell shell;
	// 要显示的问题
	private String question;
	public MessageShell(Shell shell, String question) {
		super(shell);
		this.question = question;
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
        shell.setText("问题");
        shell.setSize(SHELL_WIDTH, SHELL_HEIGHT);
        // 使窗口居中显示
        center(shell.getDisplay(), shell);
        // 定义一个网格布局
        GridLayout gridLayout = new GridLayout(2, false);
//        // 该布局中组件距离上边框的边距为20
//        gridLayout.marginHeight = 20;
//        // 该布局中组件之间的垂直间隔为20
//        gridLayout.verticalSpacing = 20;
//        // 该布局中组件距离左边框的边距为40
//        gridLayout.marginLeft = 40;
//        // 该布局中组件距离右边框的边距为40
//        gridLayout.marginRight = 40;
        // 设置窗口布局
        shell.setLayout(gridLayout);
        createClass(shell);
	}
	
	protected void createClass(Composite parent) {
		StyledText text = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
		text.setFont(new Font(parent.getDisplay(), "Arial", 18, SWT.NONE));
		String userName = question.substring(0, question.lastIndexOf(":") + 1);
		String theQuestion = question.substring(question.lastIndexOf(":") + 1);
		text.setText(userName + "\n" + theQuestion);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		text.setLayoutData(gridData);
		Button confirm = new Button(parent, SWT.NONE);
		confirm.setText(" 标记为已读 ");
		confirm.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int index = AskQuestionTableListener.index;
				StaticVariable.unanswerMap.remove(StaticVariable.askQuestions.getItem(index));					
				if (StaticVariable.unanswerMap.size() <= 0) {
					StaticVariable.askQuestion.setText("提问");
				} else {
					StaticVariable.askQuestion.setText(StaticVariable.unanswerMap.size() + " 提问");
				}
				TableItem item = StaticVariable.askQuestions.getItem(AskQuestionTableListener.index);
				item.setImage(new Image(StaticVariable.parent.getDisplay(), "image/answered.png"));
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
//		// 设置窗口默认绑定的按钮为confirm按钮
//		shell.setDefaultButton(confirm);
		Button cancle = new Button(parent, SWT.NONE);
		cancle.setText(" 取          消 ");
//		// 设置取消按钮水平右对齐
//		GridData cancleGrid = new GridData(GridData.HORIZONTAL_ALIGN_END);
//		cancle.setLayoutData(cancleGrid);
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