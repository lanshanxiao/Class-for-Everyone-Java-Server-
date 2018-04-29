package com.wanli.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

/**
 * ��ʷ��ĵ���ͼ����
 * @author wanli
 *
 */
public class HistoryChartTableUtil extends Dialog {
	protected Object result;
	protected Shell shell;
//	private static double[] ySeries = new double[3];
//	private String answer;
	private List<double[]> ySeries = new ArrayList<>();	// �洢���лش��ͳ�����
	private String[] cagetorySeries;					// �洢���д𰸣�������ʾ

//	private static final String[] cagetorySeries = { "��ȷ", "����",
//	            "δ�ش�" };
	
	public HistoryChartTableUtil(Shell parent, int index) {
		super(parent, SWT.NONE);
		if (StaticVariable.statisticalData.size() > 0) {
			inti(StaticVariable.statisticalData.get(index));			
		}
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        shell.setText("ͼ��");
        shell.setImage(SWTResourceManager.getImage("image/quesCount.png"));
        shell.setSize(800, 300);
        shell.setLayout(new FillLayout());
        createChart(shell);
	}

	protected Chart createChart(Composite parent) {
		// create a chart
        Chart chart = new Chart(parent, SWT.NONE);

        // set titles
        chart.getTitle().setText("�ɼ���");
        chart.getAxisSet().getXAxis(0).getTitle().setText("Y axis");
        chart.getAxisSet().getYAxis(0).getTitle().setText("X axis");

        // set category
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(
                new String[] { "��ȷ", "����", "δ�ش�"});

        if (cagetorySeries != null) {
        	for (int i = 0; i < cagetorySeries.length; i++) {
        		IBarSeries barSeries = (IBarSeries) chart.getSeriesSet().createSeries(
        				SeriesType.BAR, cagetorySeries[i]);
        		
        		barSeries.setYSeries(ySeries.get(i));
        	}        	
        }
//        // create bar series
//        IBarSeries barSeries1 = (IBarSeries) chart.getSeriesSet().createSeries(
//                SeriesType.BAR, "bar series 1");
//        barSeries1.setBarColor(Display.getDefault().getSystemColor(
//                SWT.COLOR_GREEN));
//
//        IBarSeries barSeries2 = (IBarSeries) chart.getSeriesSet().createSeries(
//                SeriesType.BAR, "bar series 2");
//        barSeries2.setYSeries(ySeries2);

        // adjust the axis range
        chart.getAxisSet().adjustRange();
		return chart;
	}
	
	protected void inti(String str) {
		String[] strs;
		// �ַ����洢�ĸ�ʽ��{��1 ��2 ��3...,��ȷ1  ��ȷ2 ��ȷ3...,����1 ����2 ����3...,δ�ش�1 δ�ش�2 δ�ش�3...}
		// �����������ַ���ʹ�á�,���ŷָ�
		if (str != null && str != "") {
			strs = str.split("#\\^");			
		} else {
			return;
		}
		// �ж��Ƿ��Ƕ����
		if (strs[0].split(" ").length > 1) {
			// ���ݴ𰸵Ķ��ٳ�ʼ������
			cagetorySeries = new String[strs[0].split(" ").length];
			// ʹ��list�洢������ȷ������δ�ش���ַ���
			// list.add({��ȷ1 ��ȷ2 ��ȷ3...})
			// list.add({����1 ����2 ����3...})
			// list.add({δ�ش�1 δ�ش�2 δ�ش�3...})
			List<String> allList = new ArrayList<>();
			for (int i = 1; i < strs.length; i++) {
				allList.add(strs[i]);
			}
			// ���ݴ𰸵ĸ�����ʼ��ySeries
			for (int i = 0; i < strs[0].split(" ").length; i++) {
				ySeries.add(new double[strs[0].split(" ").length]);
			}
			// ���ySeries
			for (int i = 0; i < strs[0].split(" ").length; i++) {
				String[] answers = allList.get(i).split(" ");
				for (int j = 0; j < answers.length; j++) {
					ySeries.get(j)[i] = Double.parseDouble(answers[j]);									
				}
			}
			// �����д�����cagetorySeries����
			String[] answers = strs[0].split(" ");
			for (int i = 0; i < cagetorySeries.length; i++) {
				cagetorySeries[i] = answers[i];
			}

		} else {
			cagetorySeries = new String[1];
			cagetorySeries[0] = strs[0];
			ySeries.add(new double[3]);
			for (int i = 0; i < ySeries.get(0).length; i++) {
				ySeries.get(0)[i] = Double.parseDouble(strs[i + 1]);
			}
		}
//		int unResponse = StaticVariable.unResponse.get(0).intValue();
//		int correct = StaticVariable.correct.get(0).intValue();
//		int error = StaticVariable.error.get(0).intValue();
//		unResponse = StaticVariable.users.size() - correct - error;
//		StaticVariable.unResponse.set(0, new Integer(unResponse));
//		ySeries[0] = StaticVariable.correct.get(0).intValue();
//		ySeries[1] = StaticVariable.error.get(0).intValue();
//		ySeries[2] = StaticVariable.unResponse.get(0).intValue();
//		// ���𰸱��浽answer
//		int index = StaticVariable.questionSelect.getSelectionIndex();
//		String question = StaticVariable.questionsMap.get(Integer.toString(index));
//		String[] strs = question.split(",");
//		answer = strs[3];
	}
}
