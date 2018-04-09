package com.wanli.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

import com.wanli.swing.frame.MainFrame;
import com.wanli.swing.service.DBService;

public class CharTableUtil extends Dialog {

	protected Object result;
	protected Shell shell;
	private String tableName;
	private DBService dbService;//查询数据库，获取表数据
	private List<String[]> records;//将查询的表数据存入该对象
	private List<Double> listA = new ArrayList<>();//统计所有答案各题中A选项的个数
	private List<Double> listB = new ArrayList<>();//统计所有答案各题中B选项的个数
	private List<Double> listC = new ArrayList<>();//统计所有答案各题中C选项的个数
	private List<Double> listD = new ArrayList<>();//统计所有答案各题中D选项的个数
	//用于显示柱形图
	private double[] answerA;
	private double[] answerB;
	private double[] answerC;
	private double[] answerD;
	private int columnNum = 0;//统计表的列的个数
	private String[] questionNo;//存储题目编号，用于横轴显示
	
	public CharTableUtil(Shell parent, String tableName) {
		super(parent, SWT.NONE);
		this.tableName = tableName;
		dbService = new DBService();
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
		StatisticalQuantities();
        // 创建一个图表
        Chart chart = new Chart(parent, SWT.NONE);

        //设置图表标题
        chart.getTitle().setText("成绩统计");
        // 设置图表的横轴标题和纵轴标题
        chart.getAxisSet().getXAxis(0).getTitle().setText("题");
        chart.getAxisSet().getYAxis(0).getTitle().setText("个数");

        // 设置横轴坐标名称
        for (int i = 1; i <= columnNum - 1; i++) {
        	questionNo[i - 1] = "题" + i;
        }
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(questionNo);

        // 创建柱形图
        IBarSeries barSeriesA = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "A");
        barSeriesA.setYSeries(answerA);
        barSeriesA.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));

        IBarSeries barSeriesB = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "B");
        barSeriesB.setYSeries(answerB);
        barSeriesB.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
        
        IBarSeries barSeriesC = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "C");
        barSeriesC.setYSeries(answerC);
        barSeriesC.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
        
        IBarSeries barSeriesD = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "D");
        barSeriesD.setYSeries(answerD);
        barSeriesD.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW));
        
        // 调整轴范围
        chart.getAxisSet().adjustRange();

        return chart;
    }
	
	/**
	 * 统计表格数据，构造用于制作图表的数据
	 */
	protected void StatisticalQuantities() {
		columnNum = dbService.getTableColumn(tableName);
		records = dbService.getScoreData(tableName);
		questionNo = new String[columnNum - 1];
		int A = 0;
		int B = 0;
		int C = 0;
		int D = 0;
		answerA = new double[columnNum - 1];
		answerB = new double[columnNum - 1];
		answerC = new double[columnNum - 1];
		answerD = new double[columnNum - 1];
		for (int i = 1; i < columnNum; i++) {
			for (int j = 0; j < records.size(); j++) {
				switch (records.get(j)[i]) {
				case "A":
					A++;
					break;
				case "B":
					B++;
					break;
				case "C":
					C++;
					break;
				case "D":
					D++;
					break;
				}	
			}
			listA.add((double) A);
			listB.add((double) B);
			listC.add((double) C);
			listD.add((double) D);
			A = 0;
			B = 0;
			C = 0;
			D = 0;
		}
		for (int i = 0; i < columnNum - 1; i++) {
			answerA[i] = listA.get(i);
			answerB[i] = listB.get(i);
			answerC[i] = listC.get(i);
			answerD[i] = listD.get(i);
		}
	}
	
}
