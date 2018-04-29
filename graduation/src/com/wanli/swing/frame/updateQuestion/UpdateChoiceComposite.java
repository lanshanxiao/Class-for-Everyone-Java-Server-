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
 * 选择题面板
 * @author wanli
 *
 */
public class UpdateChoiceComposite extends Composite {

	private Composite parent;
	private int ascoll = 65; 						// 保存字母的ascoll码，默认为字母E的ascoll码
	private List<Label> labels = new ArrayList<>(); // 保存增加选项时的Label组件
	private List<Text> texts = new ArrayList<>(); 	// 保存增加选项时的Text组件
	private String key = "";						// 标记最后一个选项的key
	private int i;									// 修改的题目的list下标
	private String openFile;						// 打开的文件名

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
		// 设置面板布局
		UpdateChoiceComposite choiceCom = this;
		choiceCom.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		choiceCom.setLayoutData(choiceGrid);

		// 题目
		Label questionLabel = new Label(choiceCom, SWT.NONE);
		questionLabel.setText("题目：");
		Text questionText = new Text(choiceCom, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		questionText.setText(strs[1]);
		// 设置questionText为充满式布局，且水平占两列
		GridData questionGrid = new GridData(GridData.FILL_BOTH);
		questionGrid.horizontalSpan = 2;
		questionText.setLayoutData(questionGrid);
		
		// 设置选项及答案的布局
		GridData optionGrid = new GridData(GridData.FILL_HORIZONTAL);

		// 答案
		Label answerLabel = new Label(choiceCom, SWT.NONE);
		answerLabel.setText("答案：");
		Text answerText = new Text(choiceCom, SWT.BORDER);
		answerText.setLayoutData(optionGrid);
		answerText.setText(strs[2]);

		// 布置选项
		for (int i = 3; i < strs.length; i++) {
			Label option = new Label(choiceCom, SWT.NONE);
			option.setText("选项" + (char) ascoll + "：");
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
		// 默认四个选项
//		Label optionA = new Label(choiceCom, SWT.NONE);
//		optionA.setText("选项A：");
//		Text optionTextA = new Text(choiceCom, SWT.BORDER);
//		optionTextA.setLayoutData(optionGrid);
//		Label optionB = new Label(choiceCom, SWT.NONE);
//		optionB.setText("选项B：");
//		Text optionTextB = new Text(choiceCom, SWT.BORDER);
//		optionTextB.setLayoutData(optionGrid);
//		Label optionC = new Label(choiceCom, SWT.NONE);
//		optionC.setText("选项C：");
//		Text optionTextC = new Text(choiceCom, SWT.BORDER);
//		optionTextC.setLayoutData(optionGrid);
//		Label optionD = new Label(choiceCom, SWT.NONE);
//		optionD.setText("选项D：");
//		Text optionTextD = new Text(choiceCom, SWT.BORDER);
//		optionTextD.setLayoutData(optionGrid);
		// 将ChoiceComposite面板中的所有Text组件保存
//		StaticVariable.choiceAllText.put("optionA", optionTextA);
//		StaticVariable.choiceAllText.put("optionB", optionTextB);
//		StaticVariable.choiceAllText.put("optionC", optionTextC);
//		StaticVariable.choiceAllText.put("optionD", optionTextD);

		// 保存按钮
		Button save = new Button(parent, SWT.NONE);
		save.setText("保存修改");
		// 取消按钮
		Button cancle = new Button(parent, SWT.NONE);
		cancle.setText("取       消");
		// 设置两个按钮水平填充
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		save.setLayoutData(nextGrid);
		cancle.setLayoutData(nextGrid);
		// 为保存按钮添加监听器
		save.addSelectionListener(new QuestionUpdateSaveListener(i, openFile, strs[0]));
		cancle.addSelectionListener(new QuestionUpdateCancleListener());
	}
}
