package com.wanli.swing.frame.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.utils.StaticVariable;

public class NextOptionListener extends SelectionAdapter {

	private List<String> optionKeys = new ArrayList<>();// 存储StaticVariable.choiceAllText所有选项的key值
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		optionKeys.clear();
		boolean hasEmpty = false;
		switch (StaticVariable.questionType) {
		case "choice":
			for (Map.Entry<String, Text> inputText: StaticVariable.choiceAllText.entrySet()) {
				if (inputText.getValue().getText() == null || inputText.getValue().getText() == "") {
					MessageBox messageBox = new MessageBox(StaticVariable.parent.getShell(), SWT.YES);
					messageBox.setText("警告");
					messageBox.setMessage("所有空行都不能为空！！！");
					messageBox.open();
					hasEmpty = true;
					break;
				}
			}
			if (!hasEmpty) {
				StaticVariable.creQuesIndex++;
				String question = StaticVariable.choiceAllText.get("question").getText();
				String answer = StaticVariable.choiceAllText.get("answer").getText();
				List<String> options = new ArrayList<>();
				for (String key: StaticVariable.choiceAllText.keySet()) {
					if (!key.equals("answer") && !key.equals("question")) {
						optionKeys.add(key);						
					}
				}
				Collections.sort(optionKeys);
				for (String key: optionKeys) {
					options.add(StaticVariable.choiceAllText.get(key).getText());
				}
				StaticVariable.choiceList.add(new ChoiceQuestion(Integer.toString(StaticVariable.creQuesIndex), question, answer, options));
				
			}
			break;
			
		case "true_or_false":
			
			break;
			
		case "fill_in_the_blanks":
			break;
			
		}
	}
	
}
