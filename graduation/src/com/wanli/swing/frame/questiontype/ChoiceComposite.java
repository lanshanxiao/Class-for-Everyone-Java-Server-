package com.wanli.swing.frame.questiontype;

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

import com.wanli.swing.frame.listener.NextOptionListener;
import com.wanli.utils.StaticVariable;

public class ChoiceComposite extends Composite {

	private Composite parent;
	private int ascoll = 69; 						// 保存字母的ascoll码，默认为字母E的ascoll码
	private List<Label> labels = new ArrayList<>(); // 保存增加选项时的Label组件
	private List<Text> texts = new ArrayList<>(); 	// 保存增加选项时的Text组件
	private String key = "";						// 标记最后一个选项的key

	public ChoiceComposite(Composite parent, int parameters) {
		super(parent, parameters);
		this.parent = parent;
		addComposite();
	}

	public void addComposite() {
		ChoiceComposite choiceCom = this;
		choiceCom.setLayout(new GridLayout(2, false));
		GridData choiceGrid = new GridData(GridData.FILL_BOTH);
		choiceGrid.horizontalSpan = 2;
		choiceCom.setLayoutData(choiceGrid);

		// 题目
		Label questionLabel = new Label(choiceCom, SWT.NONE);
		questionLabel.setText("题目：");
		Text questionText = new Text(choiceCom, SWT.BORDER | SWT.WRAP | SWT.MULTI);
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

		// 增加选项
		Button addOption = new Button(choiceCom, SWT.NONE);
		addOption.setText("增加选项");
		addOption.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Label option = new Label(choiceCom, SWT.NONE);
				option.setText("选项" + (char) ascoll + "：");
				Text optionText = new Text(choiceCom, SWT.BORDER);
				optionText.setLayoutData(optionGrid);
				choiceCom.layout();
				labels.add(option);
				texts.add(optionText);
				key = "option" + (char) ascoll;
				StaticVariable.choiceAllText.put(key, optionText);
				ascoll++;
			}
		});
		// 删除多余的选项
		Button delOption = new Button(choiceCom, SWT.NONE);
		delOption.setText("删除选项");
		delOption.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (labels.size() > 0) {
					ascoll--;
					key = "option" + (char) ascoll;
					StaticVariable.choiceAllText.remove(key);
					labels.get(labels.size() - 1).dispose();
					texts.get(texts.size() - 1).dispose();
					labels.remove(labels.size() - 1);
					texts.remove(texts.size() - 1);
					choiceCom.layout();
//					ascoll--;
				} else {
					MessageBox messageBox = new MessageBox(parent.getShell(), SWT.YES);
					messageBox.setText("提示");
					messageBox.setMessage("不能再删除选项了！");
					messageBox.open();
				}
			}
		});

		// 默认四个选项
		Label optionA = new Label(choiceCom, SWT.NONE);
		optionA.setText("选项A：");
		Text optionTextA = new Text(choiceCom, SWT.BORDER);
		optionTextA.setLayoutData(optionGrid);
		Label optionB = new Label(choiceCom, SWT.NONE);
		optionB.setText("选项B：");
		Text optionTextB = new Text(choiceCom, SWT.BORDER);
		optionTextB.setLayoutData(optionGrid);
		Label optionC = new Label(choiceCom, SWT.NONE);
		optionC.setText("选项C：");
		Text optionTextC = new Text(choiceCom, SWT.BORDER);
		optionTextC.setLayoutData(optionGrid);
		Label optionD = new Label(choiceCom, SWT.NONE);
		optionD.setText("选项D：");
		Text optionTextD = new Text(choiceCom, SWT.BORDER);
		optionTextD.setLayoutData(optionGrid);
		// 将ChoiceComposite面板中的所有Text组件保存
		StaticVariable.choiceAllText.put("question", questionText);
		StaticVariable.choiceAllText.put("answer", answerText);
		StaticVariable.choiceAllText.put("optionA", optionTextA);
		StaticVariable.choiceAllText.put("optionB", optionTextB);
		StaticVariable.choiceAllText.put("optionC", optionTextC);
		StaticVariable.choiceAllText.put("optionD", optionTextD);

		// 下一题
		Button nextOption = new Button(parent, SWT.NONE);
		nextOption.setText("下一题");
		GridData nextGrid = new GridData(GridData.FILL_HORIZONTAL);
		nextGrid.horizontalSpan = 2;
		nextOption.setLayoutData(nextGrid);
		StaticVariable.nextOption = nextOption;
		nextOption.addSelectionListener(new NextOptionListener());
	}
}
