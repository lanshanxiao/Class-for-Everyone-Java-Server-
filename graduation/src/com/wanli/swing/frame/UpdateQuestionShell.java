package com.wanli.swing.frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.wanli.swing.frame.updateQuestion.UpdateChoiceComposite;
import com.wanli.swing.frame.updateQuestion.UpdateFillInTheBlanksComposite;
import com.wanli.swing.frame.updateQuestion.UpdateTrueOrFalseComposite;
import com.wanli.utils.StaticVariable;

/**
 * �޸�����Ի���
 * @author wanli
 *
 */
public class UpdateQuestionShell {

	private Composite parent;	// �и�����һ����Ϣ�����
	private int i;				// �޸ĵ���Ŀ��list�±�
	private String openFile;	// �򿪵��ļ���
	
	public UpdateQuestionShell(Composite parent, int i, String openFile) {
		this.parent = parent;
		this.i = i;
		this.openFile = openFile;
		new UpdateShell(parent.getShell(), i, openFile).open();
	}
}

/**
 * ������
 * @author wanli
 *
 */
class UpdateShell extends Dialog {

	protected Object result;
	protected Shell shell;
	private int i;
	private String openFile;
	
	public UpdateShell(Shell shell, int i, String openFile) {
		super(shell);
		this.i = i;
		this.openFile = openFile;
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
	
	/**
	 * ��������
	 */
	protected void createContents() {
		// ����һ������
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        shell.setText("�����޸�");
        shell.setSize(800, 800);
        
        shell.addShellListener(new ShellAdapter() {
        	@Override
        	public void shellClosed(ShellEvent e) {
    			MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO);
    			messageBox.setText("����");
    			messageBox.setMessage("ȷ��Ҫ�˳���");
    			if (e.doit = messageBox.open() == SWT.YES) {
    				StaticVariable.choiceList.clear();
    				StaticVariable.trueOrFalseList.clear();
    				StaticVariable.fillblanksList.clear();
    			} 
    		}
        	
		});
        // ʹ���ھ�����ʾ
        center(shell.getDisplay(), shell);
        // ����һ�����񲼾�
        GridLayout gridLayout = new GridLayout(2, false);
        // �ò�������������ϱ߿�ı߾�Ϊ20
        gridLayout.marginHeight = 20;
        // �ò��������֮��Ĵ�ֱ���Ϊ20
        gridLayout.verticalSpacing = 20;
        // �ò��������������߿�ı߾�Ϊ40
        gridLayout.marginLeft = 40;
        // �ò�������������ұ߿�ı߾�Ϊ40
        gridLayout.marginRight = 40;
        // ���ô��ڲ���
        shell.setLayout(gridLayout);
        createShell(shell);
	}
	
	protected void createShell(Composite parent) {
		String question = StaticVariable.questionsList.get(i);
		String[] strs = question.split("#\\^");
		// �ж���Ŀ����
		switch (strs[0]) {
		// ѡ����
		case "choice":
			StaticVariable.questionCom = new UpdateChoiceComposite(parent, SWT.BORDER, i, openFile);			
			break;
		// �Ƿ���
		case "true_or_false":
			StaticVariable.questionCom = new UpdateTrueOrFalseComposite(parent, SWT.BORDER, i, openFile);
			break;
		// �����
		case "fill_in_the_blanks":
			StaticVariable.questionCom = new UpdateFillInTheBlanksComposite(parent, SWT.BORDER, i, openFile);
			break;
		}
	}
	
	/**
	 * ʹ�Ի��������ʾ
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


