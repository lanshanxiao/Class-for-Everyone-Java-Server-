package com.wanli.swing.frame.updateQuestion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.frame.updateQuestion.listener.QuestionUpdateCancleListener;
import com.wanli.swing.frame.updateQuestion.listener.QuestionUpdateSaveListener;
import com.wanli.utils.StaticVariable;
/**
 * �Ƿ����������ͨ�õ��࣬��ʾ��ͬ�Ľ���
 * @author wanli
 *
 */
public class UpdateQuestionBasic {

	private Composite parent;	// ������
	private Composite child;	// �Ӵ���
	private int i;
	private String openFile;
	
	public UpdateQuestionBasic(Composite parent, Composite child, int i, String openFile) {
		this.parent = parent;
		this.child = child;
		this.i = i;
		this.openFile = openFile;
		addComposite();
	}
	
	public void addComposite() {
		String question = StaticVariable.questionsList.get(i);
		String[] strs = question.split("#\\^");
		// ���ô��岼��Ϊ����ʽ���֣��ҷֳ�����
		child.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		child.setLayoutData(choiceGrid);
		
		// ��Ŀ
		Label questionLabel = new Label(child, SWT.NONE);
		questionLabel.setText("��Ŀ��");
		Text questionText = new Text(child, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		questionText.setText(strs[1]);
		// ����questionTextΪ����ʽ���֣���ˮƽռ����
		GridData questionGrid = new GridData(GridData.FILL_BOTH);
		questionGrid.horizontalSpan = 2;
		questionText.setLayoutData(questionGrid);
		
		// ����ѡ��𰸵Ĳ���
		GridData optionGrid = new GridData(GridData.FILL_HORIZONTAL);

		// ��
		Label answerLabel = new Label(child, SWT.NONE);
		answerLabel.setText("�𰸣�");
		
		if (child instanceof UpdateTrueOrFalseComposite) {
			Text answerText = new Text(child, SWT.BORDER);
			answerText.setLayoutData(optionGrid);
			answerText.setText(strs[2]);
			Label tip1 = new Label(child, SWT.NONE);
			tip1.setText("��ʾ��");
			Label tip2 = new Label(child, SWT.NONE);
			tip2.setText("�Ƿ������ȷ������T����������F��");
			tip2.setLayoutData(optionGrid);
			StaticVariable.trueOrFalseAllText.put("question", questionText);
			StaticVariable.trueOrFalseAllText.put("answer", answerText);
		}
		if (child instanceof UpdateFillInTheBlanksComposite) {
			
//			answerText.setText("���ж���գ������ÿո�ָ���");	            		
//			answerText.addFocusListener(new FocusAdapter() {
//				@Override
//	            public void focusGained(FocusEvent e)
//	            {
//					if ("���ж���գ������ÿո�ָ���".equals(answerText.getText())) {
//						answerText.setText("");						
//					}
//	            }
//	            @Override
//	            public void focusLost(FocusEvent e)
//	            {
//	            	if (answerText.getText() == "") {
//	            		answerText.setText("���ж���գ������ÿո�ָ���");	            		
//	            	}
//	            }
//			});
			Text answerText = new Text(child, SWT.BORDER);
			answerText.setLayoutData(optionGrid);
			StringBuilder builder = new StringBuilder();
			for (int i = 2; i < strs.length; i++) {
				if (i == 2) {
					builder.append(strs[2]);					
				} else {
					builder.append(" " + strs[i]);
				}
			}
			answerText.setText(builder.toString());				
			Label tip1 = new Label(child, SWT.NONE);
			tip1.setText("��ʾ��");
			Label tip2 = new Label(child, SWT.NONE);
			tip2.setText("���ж���գ������ÿո�ָ���");
			tip2.setLayoutData(optionGrid);
			StaticVariable.fillblanksAllText.put("question", questionText);
			StaticVariable.fillblanksAllText.put("answer", answerText);
		}
		
		Button save = new Button(parent, SWT.NONE);
		save.setText("�����޸�");
		Button cancle = new Button(parent, SWT.NONE);
		cancle.setText("ȡ       ��");
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		save.setLayoutData(nextGrid);
		cancle.setLayoutData(nextGrid);
		save.addSelectionListener(new QuestionUpdateSaveListener(i, openFile, strs[0]));
		cancle.addSelectionListener(new QuestionUpdateCancleListener());
	}
	
}
