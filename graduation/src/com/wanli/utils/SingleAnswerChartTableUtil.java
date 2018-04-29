package com.wanli.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.swtchart.Chart;
import org.swtchart.ISeries;
import org.swtchart.ISeries.SeriesType;

/**
 * ֻ�е�����ʱ��ʾ��ͼ��
 * @author wanli
 *
 */
public class SingleAnswerChartTableUtil extends Dialog {

	protected Object result;
	protected Shell shell;
	private static double[] ySeries = new double[3];	//�洢����ͼ������
	private String answer;

	private static final String[] cagetorySeries = { "��ȷ", "����",
	            "δ�ش�" };
	
	public SingleAnswerChartTableUtil(Shell parent, String tableName) {
		super(parent, SWT.NONE);
		inti();
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
		// ����һ��ͼ��
        Chart chart = new Chart(parent, SWT.NONE);
        chart.getTitle().setText("�ɼ���");

        // ����yֵ
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(cagetorySeries);
        chart.getAxisSet().getXAxis(0).getTick().setTickLabelAngle(45);

        // �������ͼ
        ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR,
                answer);
        barSeries.setYSeries(ySeries);

        chart.getAxisSet().adjustRange();

        return chart;
    }
	
	protected void inti() {
		if (StaticVariable.unResponse.size() > 0) {
			// ������ȷ�𰸣�����𰸣�δ�ش�����������
			int unResponse = StaticVariable.unResponse.get(0).intValue();
			int correct = StaticVariable.correct.get(0).intValue();
			int error = StaticVariable.error.get(0).intValue();
			unResponse = StaticVariable.users.size() - correct - error;
			StaticVariable.unResponse.set(0, new Integer(unResponse));
			// ��ʼ��ÿ������ͼ������
			ySeries[0] = StaticVariable.correct.get(0).intValue();
			ySeries[1] = StaticVariable.error.get(0).intValue();
			ySeries[2] = StaticVariable.unResponse.get(0).intValue();
			// ���𰸱��浽answer
			int index = StaticVariable.questionSelect.getSelectionIndex();
//			String question = StaticVariable.questionsMap.get(Integer.toString(index));
			String question = StaticVariable.questionsList.get(index - 1);
			String[] strs = question.split("#\\^");
			answer = strs[3];			
		}
	}
	
}
