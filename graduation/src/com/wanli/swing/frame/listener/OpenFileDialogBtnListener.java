package com.wanli.swing.frame.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.utils.StaticVariable;
import com.wanli.utils.XmlToJavaBean;

public class OpenFileDialogBtnListener implements SelectionListener {

	private static TableItem[] items;								// ��ʾ���е���Ŀ
	private static int BTNWIDTH = 90;								// ��ť��ʾ�Ŀ��
	private static String openFile;								// �򿪵��ļ���
	private static List<Control> cons = new ArrayList<>();			// �洢���б���еĿؼ�
	private static List<TableEditor> editors = new ArrayList<>();	// �洢���е�tableEditor
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		StaticVariable.allQuestionList.clear();
		StaticVariable.questionsList.clear();
		// ����Ի�������Ϊ����
		FileDialog dialog = new FileDialog(StaticVariable.parent.getShell(), SWT.OPEN);
		// ���öԻ���򿪵��޶�����
		dialog.setFilterExtensions(new String[] { "*.xml"});
		// �򿪶Ի��򣬲����ش��ļ���·��
		openFile = dialog.open();
		if (openFile == null) {
			return;
		}
		// ��ָ�����ļ�
		File file = new File(openFile);
		// ��xml�ļ�תΪJavaBean
		new XmlToJavaBean(file);
		if (StaticVariable.questionsList.size() > 0) {
			fillTable();			
		}
	}
	
	/**
	 * �����
	 */
	public static void fillTable() {
		// �ж����ݿ����Ƿ��б���ڣ�����ǰtable�Ѿ�����ʾ���ݣ��������������
		if (cons.size() > 0) {
			for (Control control: cons) {
				control.dispose();
			}
		}
		if (editors.size() > 0) {
			for (TableEditor editor: editors) {
				editor.dispose();
			}
		}
		StaticVariable.table.removeAll();
//		StaticVariable.table.pack();
		// ������Ŀ�ĸ�����ʼ������
		items = new TableItem[StaticVariable.questionSelect.getItemCount() - 1];
		for (int i = 0; i < StaticVariable.questionSelect.getItemCount() - 1; i++) {
			// ���õ�һ�е�text
			items[i] = new TableItem(StaticVariable.table, SWT.NONE);
			items[i].setText(0, StaticVariable.questionSelect.getItem(i + 1));
			// �ָ���Ŀ�ַ���
//			String question = StaticVariable.questionsMap.get(Integer.toString(i + 1));
			String question = StaticVariable.questionsList.get(i);
			String[] strs = question.split("#\\^");
			// ���õڶ��е�text
			items[i].setText(1, strs[1]);
			// ��������TableEditor�������ڷ���������ť
			TableEditor editorDel = new TableEditor(StaticVariable.table);
			TableEditor editorUp = new TableEditor(StaticVariable.table);
			editors.add(editorDel);
			editors.add(editorUp);
			// ����������ť�Ķ��뷽ʽ
			editorDel.horizontalAlignment = SWT.LEFT;
			editorUp.horizontalAlignment = SWT.RIGHT;
			// ���ð�ť��ʾ�Ŀ��
			editorDel.minimumWidth = BTNWIDTH;
			editorUp.minimumWidth = BTNWIDTH;
			// ����������ť�ؼ�
			Button buttonDel = new Button(StaticVariable.table, SWT.PUSH);
			Button buttonUp = new Button(StaticVariable.table, SWT.PUSH);
			buttonDel.setText("ɾ��");
			buttonUp.setText("�޸�");
			cons.add(buttonDel);
			cons.add(buttonUp);
			// ���ð�ť�ĸ߶ȣ�Ĭ�����еĸ߶�һ��
			buttonDel.computeSize(SWT.DEFAULT, StaticVariable.table.getItemHeight());
			buttonUp.computeSize(SWT.DEFAULT, StaticVariable.table.getItemHeight());
			// Ϊ��ť��Ӽ�����
			buttonDel.addSelectionListener(new DeleteQuestionListener(i, openFile));
			buttonUp.addSelectionListener(new UpdateQuestionListener(i, openFile));
			// ��Ӱ�ť�ؼ�
			editorDel.setEditor(buttonDel, items[i], 2);
			editorUp.setEditor(buttonUp, items[i], 2);
		}
	}

}
