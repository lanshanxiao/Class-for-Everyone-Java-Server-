package com.wanli.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.ISeries;
import org.swtchart.ISeries.SeriesType;

/**
 * 只有单个答案时显示的图表
 * @author wanli
 *
 */
public class SingleAnswerChartTableUtil extends Dialog {

	protected Object result;
	protected Shell shell;
	private static double[] ySeries = new double[3];	//存储柱形图的数据
	private String answer;

	private static final String[] cagetorySeries = { "正确", "错误",
	            "未回答" };
	
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
        shell.setText("图表");
        shell.setSize(800, 300);
        shell.setLayout(new FillLayout());
        createChart(shell);
	}
	
	protected Chart createChart(Composite parent) {
		// 创建一张图表
        Chart chart = new Chart(parent, SWT.NONE);
        chart.getTitle().setText("成绩表");

        // 设置y值
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(cagetorySeries);
        chart.getAxisSet().getXAxis(0).getTick().setTickLabelAngle(45);

        // 添加柱形图
        ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR,
                answer);
        barSeries.setYSeries(ySeries);

        chart.getAxisSet().adjustRange();

        return chart;
    }
	
	protected void inti() {
		if (StaticVariable.unResponse.size() > 0) {
			// 计算正确答案，错误答案，未回答人数的总数
			int unResponse = StaticVariable.unResponse.get(0).intValue();
			int correct = StaticVariable.correct.get(0).intValue();
			int error = StaticVariable.error.get(0).intValue();
			unResponse = StaticVariable.users.size() - correct - error;
			StaticVariable.unResponse.set(0, new Integer(unResponse));
			// 初始化每个柱形图的数据
			ySeries[0] = StaticVariable.correct.get(0).intValue();
			ySeries[1] = StaticVariable.error.get(0).intValue();
			ySeries[2] = StaticVariable.unResponse.get(0).intValue();
			// 将答案保存到answer
			int index = StaticVariable.questionSelect.getSelectionIndex();
			String question = StaticVariable.questionsMap.get(Integer.toString(index));
			String[] strs = question.split(",");
			answer = strs[3];			
		}
	}
	
}
