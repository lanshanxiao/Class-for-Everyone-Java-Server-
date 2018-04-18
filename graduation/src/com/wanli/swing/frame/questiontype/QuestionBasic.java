package com.wanli.swing.frame.questiontype;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.frame.listener.NextOptionListener;
import com.wanli.utils.StaticVariable;
/**
 * 是非题与填空题通用的类，显示相同的界面
 * @author wanli
 *
 */
public class QuestionBasic {

	private Composite parent;	// 父窗口
	private Composite child;	// 子窗口
	
	public QuestionBasic(Composite parent, Composite child) {
		this.parent = parent;
		this.child = child;
		StaticVariable.nextOption = addComposite();
	}
	
	public Button addComposite() {
		// 设置窗体布局为网格式布局，且分成两列
		child.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		child.setLayoutData(choiceGrid);
		
		// 题目
		Label questionLabel = new Label(child, SWT.NONE);
		questionLabel.setText("题目：");
		Text questionText = new Text(child, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		// 设置questionText为充满式布局，且水平占两列
		GridData questionGrid = new GridData(GridData.FILL_BOTH);
		questionGrid.horizontalSpan = 2;
		questionText.setLayoutData(questionGrid);
		
		// 设置选项及答案的布局
		GridData optionGrid = new GridData(GridData.FILL_HORIZONTAL);

		// 答案
		Label answerLabel = new Label(child, SWT.NONE);
		answerLabel.setText("答案：");
		Text answerText = new Text(child, SWT.BORDER);
		answerText.setLayoutData(optionGrid);
		
		if (child instanceof TrueOrFalseComposite) {
			Label tip1 = new Label(child, SWT.NONE);
			tip1.setText("提示：");
			Label tip2 = new Label(child, SWT.NONE);
			tip2.setText("是非题答案正确请输入T，错误输入F！");
			tip2.setLayoutData(optionGrid);
			StaticVariable.trueOrFalseAllText.put("question", questionText);
			StaticVariable.trueOrFalseAllText.put("answer", answerText);
		}
		if (child instanceof FillInTheBlanksComposite) {
			
//			answerText.setText("若有多个空，答案请用空格分隔！");	            		
//			answerText.addFocusListener(new FocusAdapter() {
//				@Override
//	            public void focusGained(FocusEvent e)
//	            {
//					if ("若有多个空，答案请用空格分隔！".equals(answerText.getText())) {
//						answerText.setText("");						
//					}
//	            }
//	            @Override
//	            public void focusLost(FocusEvent e)
//	            {
//	            	if (answerText.getText() == "") {
//	            		answerText.setText("若有多个空，答案请用空格分隔！");	            		
//	            	}
//	            }
//			});
			Label tip1 = new Label(child, SWT.NONE);
			tip1.setText("提示：");
			Label tip2 = new Label(child, SWT.NONE);
			tip2.setText("若有多个空，答案请用空格分隔！");
			tip2.setLayoutData(optionGrid);
			StaticVariable.fillblanksAllText.put("question", questionText);
			StaticVariable.fillblanksAllText.put("answer", answerText);
		}
		
		// 下一题
		Button nextOption = new Button(parent, SWT.NONE);
		nextOption.setText("下一题");
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		nextGrid.horizontalSpan = 2;
		nextOption.setLayoutData(nextGrid);
		nextOption.addSelectionListener(new NextOptionListener());
		
		return nextOption;
	}
	
}
