package com.wanli.swing.frame.updateQuestion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.entities.QuestionType;
import com.wanli.swing.frame.listener.NextOptionListener;
import com.wanli.swing.frame.updateQuestion.listener.QuestionUpdateCancleListener;
import com.wanli.swing.frame.updateQuestion.listener.QuestionUpdateSaveListener;
import com.wanli.utils.StaticVariable;

/**
 * ѡ�������
 * @author wanli
 *
 */
public class UpdateChoiceComposite extends Composite {

	private Composite parent;
	private int ascoll = 65; 						// ������ĸ��ascoll�룬Ĭ��Ϊ��ĸE��ascoll��
	private List<Label> labels = new ArrayList<>(); // ��������ѡ��ʱ��Label���
	private List<Text> texts = new ArrayList<>(); 	// ��������ѡ��ʱ��Text���
	private String key = "";						// ������һ��ѡ���key
	private int i;									// �޸ĵ���Ŀ��list�±�
	private String openFile;						// �򿪵��ļ���

	public UpdateChoiceComposite(Composite parent, int parameters, int i, String openFile) {
		super(parent, parameters);
		this.parent = parent;
		this.i = i;
		this.openFile = openFile;
		addComposite();
	}

	public void addComposite() {
		String question = StaticVariable.questionsList.get(i);
		String[] strs = question.split("#\\^");
		// ������岼��
		UpdateChoiceComposite choiceCom = this;
		choiceCom.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		choiceCom.setLayoutData(choiceGrid);

		// ��Ŀ
		Label questionLabel = new Label(choiceCom, SWT.NONE);
		questionLabel.setText("��Ŀ��");
		Text questionText = new Text(choiceCom, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		questionText.setText(strs[1]);
		// ����questionTextΪ����ʽ���֣���ˮƽռ����
		GridData questionGrid = new GridData(GridData.FILL_BOTH);
		questionGrid.horizontalSpan = 2;
		questionText.setLayoutData(questionGrid);
		
		// ����ѡ��𰸵Ĳ���
		GridData optionGrid = new GridData(GridData.FILL_HORIZONTAL);

		// ��
		Label answerLabel = new Label(choiceCom, SWT.NONE);
		answerLabel.setText("�𰸣�");
		Text answerText = new Text(choiceCom, SWT.BORDER);
		answerText.setLayoutData(optionGrid);
		answerText.setText(strs[2]);

		// ����ѡ��
		for (int i = 3; i < strs.length; i++) {
			Label option = new Label(choiceCom, SWT.NONE);
			option.setText("ѡ��" + (char) ascoll + "��");
			Text optionText = new Text(choiceCom, SWT.BORDER);
			optionText.setLayoutData(optionGrid);
			optionText.setText(strs[i]);
			choiceCom.layout();
			labels.add(option);
			texts.add(optionText);
			key = "option" + (char) ascoll;
			StaticVariable.choiceAllText.put(key, optionText);
			ascoll++;
		}
		StaticVariable.choiceAllText.put("question", questionText);
		StaticVariable.choiceAllText.put("answer", answerText);
		// Ĭ���ĸ�ѡ��
//		Label optionA = new Label(choiceCom, SWT.NONE);
//		optionA.setText("ѡ��A��");
//		Text optionTextA = new Text(choiceCom, SWT.BORDER);
//		optionTextA.setLayoutData(optionGrid);
//		Label optionB = new Label(choiceCom, SWT.NONE);
//		optionB.setText("ѡ��B��");
//		Text optionTextB = new Text(choiceCom, SWT.BORDER);
//		optionTextB.setLayoutData(optionGrid);
//		Label optionC = new Label(choiceCom, SWT.NONE);
//		optionC.setText("ѡ��C��");
//		Text optionTextC = new Text(choiceCom, SWT.BORDER);
//		optionTextC.setLayoutData(optionGrid);
//		Label optionD = new Label(choiceCom, SWT.NONE);
//		optionD.setText("ѡ��D��");
//		Text optionTextD = new Text(choiceCom, SWT.BORDER);
//		optionTextD.setLayoutData(optionGrid);
		// ��ChoiceComposite����е�����Text�������
//		StaticVariable.choiceAllText.put("optionA", optionTextA);
//		StaticVariable.choiceAllText.put("optionB", optionTextB);
//		StaticVariable.choiceAllText.put("optionC", optionTextC);
//		StaticVariable.choiceAllText.put("optionD", optionTextD);

		// ���水ť
		Button save = new Button(parent, SWT.NONE);
		save.setText("�����޸�");
		// ȡ����ť
		Button cancle = new Button(parent, SWT.NONE);
		cancle.setText("ȡ       ��");
		// ����������ťˮƽ���
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		save.setLayoutData(nextGrid);
		cancle.setLayoutData(nextGrid);
		// Ϊ���水ť��Ӽ�����
		save.addSelectionListener(new QuestionUpdateSaveListener(i, openFile, strs[0]));
		cancle.addSelectionListener(new QuestionUpdateCancleListener());
	}
}
