package com.wanli.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
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
 * �����ʱҪ��ʾ��ͼ��ͳ������
 * @author wanli
 *
 */
public class MultipleAnswersChartTableUtil extends Dialog {

	protected Object result;
	protected Shell shell;
	private List<double[]> ySeries = new ArrayList<>();	// �洢���лش��ͳ�����
	private String[] cagetorySeries;					// �洢���д𰸣�������ʾ
	
	public MultipleAnswersChartTableUtil(Shell parent) {
		super(parent, SWT.NONE);
		init();
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

        for (int i = 0; i < cagetorySeries.length; i++) {
        	IBarSeries barSeries = (IBarSeries) chart.getSeriesSet().createSeries(
                    SeriesType.BAR, cagetorySeries[i]);
        	
        	barSeries.setYSeries(ySeries.get(i));
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
	
	protected void init() {
		cagetorySeries = new String[StaticVariable.correct.size()];
		List<List<Integer>> allList = new ArrayList<>();// ������ͳ�Ƶ������洢��һ��list�У����ں������
		allList.add(StaticVariable.correct);
		allList.add(StaticVariable.error);
		allList.add(StaticVariable.unResponse);
		// ���ݴ𰸵ĸ�����ʼ��ySeries
		for (int i = 0; i < StaticVariable.correct.size(); i++) {
			ySeries.add(new double[StaticVariable.correct.size()]);
		}
		// ������ȷ�������ʹ�������������δ�ش������
		for (int i = 0; i < StaticVariable.correct.size(); i++) {
			int unResponse = StaticVariable.unResponse.get(i).intValue();
			int correct = StaticVariable.correct.get(i).intValue();
			int error = StaticVariable.error.get(i).intValue();
			unResponse = StaticVariable.users.size() - correct - error;
			StaticVariable.unResponse.set(i, new Integer(unResponse));
			System.out.println(StaticVariable.unResponse);
		}
		// ���ySeries
		for (int i = 0; i < StaticVariable.correct.size(); i++) {
			for (int j = 0; j < ySeries.get(i).length; j++) {
				ySeries.get(j)[i] = allList.get(i).get(j);									
			}
		}
		// �����д�����cagetorySeries����
		for (int i = 0; i < cagetorySeries.length; i++) {
			int index = StaticVariable.questionSelect.getSelectionIndex();
//			String question = StaticVariable.questionsMap.get(Integer.toString(index));
			String question = StaticVariable.questionsList.get(index - 1);
			String[] strs = question.split("#\\^");
			cagetorySeries[i] = strs[i + 3];
		}
	}
}
