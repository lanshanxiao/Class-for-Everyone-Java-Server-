package com.wanli.swing.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.wanli.classforereryone.server.MyServer;
import com.wanli.classforereryone.server.ServerThread;
import com.wanli.swing.frame.listener.ButtonDownListener;
import com.wanli.swing.frame.listener.CreateClassListener;
import com.wanli.swing.frame.listener.HistoryCharBtnListener;
import com.wanli.swing.frame.listener.HistoryComboListener;
import com.wanli.swing.frame.listener.OnlineTreeListener;
import com.wanli.swing.frame.listener.ScoreChartBtnListener;
import com.wanli.swing.frame.listener.TabFordlerListener;
import com.wanli.swing.service.DBService;
import com.wanli.thread.ListeningSocket;
import com.wanli.utils.StaticVariable;

public class MainFrame extends ApplicationWindow {
	private static String APPNAME = "Class For Everyone";	// 当前软件的名称
	private static String welcome = "欢迎您，";				// 显示欢迎提示
	private Shell shell;									// 主窗口的shell类
	private Action newCreate;								// 新建教室
	private Action openFile;								// 打开文件
	private Action saveFile;								// 保存文件
	private Action saveAsFile;								// 另存为
	private Action exit;									// 退出程序
	private Action copyFile;								// 复制
	private Action pasteFile;								// 粘贴
	private Action cutFile;									// 剪切
	private Action setFont;									// 设置字体
	private Action setColor;								// 设置颜色
	private Action selectAll;								// 全选
	private Action formate;									// 格式
	private Action about;									// 关于
	private Tree tree;										// 显示在线用户列表
	private Font font;										// 字体
	private File file;										// 文件
	private Color color;									// 颜色
	private StyleRange style, range;						// 风格
	private TabFolder tabFolder;							// 选项卡
	private Button first;									// 跳转到第一题
	private Button previous;								// 上一题
	private Button next;									// 下一题
	private Button last;									// 最后一题
	private String userName;								// 用户名
	boolean changes;										// 文档是否改变
	private DBService dbService;							// 创建操作dao的业务
	private Runtime runtime;								// 获取当前程序的运行时
	private Process process;								// 用来存储调用的外部进程

	public MainFrame(String userName) {
		// 部署窗口
		super(null);
		//开启一个线程，用来监听客户端的连接
		new Thread(new MyServer()).start();
		//开启一个线程，监听客户端的连接，监听到有客户端连接上就显示客户端的连接情况
		new Thread(new ListeningSocket()).start();
		this.userName = userName;// 获取用户名
		newCreate = new NewCreate();
		openFile = new OpenFile();
		saveFile = new SaveFileAction();
		saveAsFile = new SaveAsFileAction();
		exit = new ExitAction();
		copyFile = new CopyFileAction();
		pasteFile = new PasteFileAction();
		cutFile = new CutFileAction();
		setFont = new SetFontAction();
		setColor = new SetColorAction();
		selectAll = new SelectAllAction();
		formate = new FormateAction();
		about = new AboutAction();
		dbService = new DBService();
		addMenuBar();// 添加菜单栏
		addToolBar(SWT.FLAT);// 添加工具栏
	}

	public void run() {
		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();
	}

	@Override
	public Control createContents(Composite parent) {
		// 设置窗体大小
		Rectangle bounds = parent.getShell().getDisplay().getPrimaryMonitor().getBounds();
		int windowWidth = bounds.width;
		int windowHeight = (int) (bounds.height * 0.98);
		parent.getShell().setSize(windowWidth, (int) (bounds.height * 0.98));
		// 设置窗体标题
		parent.getShell().setText(APPNAME);
		shell = parent.getShell();
		StaticVariable.parent = parent;
		// 设置主面板
		Composite mainFrame = new Composite(parent, SWT.NONE);
		mainFrame.setLayout(null);
		// 设置显示在线情况面板
		Composite onlineView = new Composite(mainFrame, SWT.BORDER);
		onlineView.setBounds(0, 0, (int) (windowWidth * 0.3), (int) (windowHeight * 0.87));
		// 设置创建教室面板
		Composite createRoom = new Composite(onlineView, SWT.BORDER);
		createRoom.setBounds(0, 0, onlineView.getSize().x, (int) (onlineView.getSize().y * 0.04));
		createRoom.setLayout(new FillLayout());
		
		Button button = new Button(createRoom, SWT.NONE);
		FormData fd_button = new FormData();
		fd_button.left = new FormAttachment(0);
		button.setLayoutData(fd_button);
		button.setText("\u5F00\u542F\u9738\u5C4F");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ServerThread.sendToClient("开启霸屏");
			}
		});
		Button screenshot = new Button(createRoom, SWT.NONE);
		screenshot.setText("教学截图");
		screenshot.setLayoutData(fd_button);
		screenshot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				runtime = Runtime.getRuntime();		// 获取当前程序的运行时
				try {
					// 执行windows系统自带的截图工具程序，并通过process管理该进程
					process = runtime.exec("C:\\Windows\\System32\\SnippingTool.exe");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// 设置显示在线人数面板
		Composite onlineNum = new Composite(onlineView, SWT.BORDER);
		onlineNum.setBounds(0, (int) (onlineView.getSize().y * 0.04), onlineView.getSize().x,
				(int) (onlineView.getSize().y * 0.04));

		// 显示登录信息
		Label welcome = new Label(onlineNum, SWT.NONE);
		welcome.setBounds(0, 0, 268, 32);
		welcome.setText(MainFrame.welcome + this.userName);

		// 显示在线人数
		StaticVariable.onlining = new Label(onlineNum, SWT.NONE);
		StaticVariable.onlining.setBounds(269, 0, 303, 32);
		StaticVariable.onlining.setText(StaticVariable.onlineNumsStr + StaticVariable.onlineNumsInt);

		// 设置显示在线用户列表面板
		Composite onlineUser = new Composite(onlineView, SWT.BORDER);
		onlineUser.setBounds(0, onlineNum.getSize().y + createRoom.getSize().y, onlineView.getSize().x,
				(int) (onlineView.getSize().y * 0.92));
		onlineUser.setLayout(new FillLayout(SWT.HORIZONTAL));

		// 以树的形式显示在线用户列表
		tree = new Tree(onlineUser, SWT.BORDER);
		tree.addMouseListener(new OnlineTreeListener(tree, parent));
		
		// 设置显示题目，成绩，历史记录等的面板
		Composite textView = new Composite(mainFrame, SWT.BORDER);
		textView.setBounds((int) (windowWidth * 0.3), 0, (int) (windowWidth * 0.69), onlineView.getSize().y);
		textView.setLayout(new GridLayout(6, false));

		// 题目，成绩，历史记录选项卡
		tabFolder = new TabFolder(textView, SWT.NONE);
		GridData gridTab = new GridData(GridData.FILL_BOTH);
		gridTab.horizontalSpan = 6;
		tabFolder.setLayoutData(gridTab);

		// 定义第一个选项卡
		final TabItem question = new TabItem(tabFolder, SWT.NONE);
		question.setText("题目");
		{
			//为该选项卡定义一个面板
			Composite questionComp = new Composite(tabFolder, SWT.BORDER);
			question.setControl(questionComp);
			//设置该面板为网格式布局，且设置为4列
			questionComp.setLayout(new GridLayout(4, false));
			//添加一个面板，用来放置StyledText控件
			Composite textComp = new Composite(questionComp, SWT.BORDER);
			//设置textComp面板的布局为充满式布局
			textComp.setLayout(new FillLayout());
			StaticVariable.text = new StyledText(textComp, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
			StaticVariable.text.setRightMargin(40);
			StaticVariable.text.setLeftMargin(40);
			StaticVariable.text.setFont(new Font(parent.getDisplay(), "Arial", 20, SWT.NONE));
			StaticVariable.text.setBounds(0, 0, textComp.getSize().x, questionComp.getSize().y);
			//为socreTableComp面板设置一个控制布局的对象GridTab1，设置该面板在水平、垂直两个方向全充满
			GridData gridTab1 = new GridData(GridData.FILL_BOTH);
			//设置socreTableComp面板垂直占4列
			gridTab1.horizontalSpan = 4;
			textComp.setLayoutData(gridTab1);
			// 定义操作按钮
			first = new Button(questionComp, SWT.NONE);
			first.setText("首题");
			first.setEnabled(false);

			previous = new Button(questionComp, SWT.NONE);
			previous.setText("上一题");
			previous.setEnabled(false);

			next = new Button(questionComp, SWT.NONE);
			next.setText("下一题");
			next.setEnabled(false);

			last = new Button(questionComp, SWT.NONE);
			last.setText("末题");
			last.setEnabled(false);
		}

		// 定义第二个选项卡
		final TabItem score = new TabItem(tabFolder, SWT.NONE);
		score.setText("成绩");
		{
			//为该选项卡添加一个面板
			Composite scoreComp = new Composite(tabFolder, SWT.BORDER);
			score.setControl(scoreComp);
			//设置该面板的布局为网格布局，设置成一列
			scoreComp.setLayout(new GridLayout(2, false));
			//添加一个面板，用来放置表格
			Composite scoreTableComp = new Composite(scoreComp, SWT.BORDER);
			//设置socreTableComp面板为充满式布局
			scoreTableComp.setLayout(new FillLayout());
			// 定义表格
			StaticVariable.scoreTab = new Table(scoreTableComp, SWT.MULTI);
			// 设置表头可见
			StaticVariable.scoreTab.setHeaderVisible(true);
			// 设置网格线可见
			StaticVariable.scoreTab.setLinesVisible(true);
			//为socreTableComp面板设置一个控制布局的对象GridTab2，设置该面板在水平、垂直两个方向全充满
			GridData gridTab2 = new GridData(GridData.FILL_BOTH);
			//设置socreTableComp面板垂直占两列
			gridTab2.horizontalSpan = 2;
			scoreTableComp.setLayoutData(gridTab2);
			//定义一个刷新表格的按钮
			StaticVariable.refresh = new Button(scoreComp, SWT.NONE);
			StaticVariable.refresh.setText("刷新");
			StaticVariable.scoreChartBtn = new Button(scoreComp, SWT.NONE);
			StaticVariable.scoreChartBtn.setText("图表数据");
		}

		// 定义第三个选项卡
		final TabItem history = new TabItem(tabFolder, SWT.NONE);
		history.setText("历史记录");
		{
			//为该选项卡添加一个面板
			Composite historyComp = new Composite(tabFolder, SWT.BORDER);
			history.setControl(historyComp);
			//设置该面板的布局为网格布局，设置成两列
			historyComp.setLayout(new GridLayout(3, false));
			//添加一个面板，用来放置表格
			Composite tableComp = new Composite(historyComp, SWT.BORDER);
			//设置tableComp面板的布局为充满式布局
			tableComp.setLayout(new FillLayout());
			//定义一张表格
			StaticVariable.historyTab = new Table(tableComp, SWT.MULTI);
			// 设置表头可见
			StaticVariable.historyTab.setHeaderVisible(true);
			// 设置网格线可见
			StaticVariable.historyTab.setLinesVisible(true);
			//为socreTableComp面板设置一个控制布局的对象GridTab2，设置该面板在水平、垂直两个方向全充满
			GridData gridTab3 = new GridData(GridData.FILL_BOTH);
			//设置socreTableComp面板垂直占两列
			gridTab3.horizontalSpan = 3;
			tableComp.setLayoutData(gridTab3);

			//定义一个Label控件
			Label hisTable = new Label(historyComp, SWT.NONE);
			hisTable.setText("选择历史表：");
			//定义一个下拉框，用来选择历史表
			StaticVariable.historyCombo = new Combo(historyComp, SWT.READ_ONLY);
			//为下拉框定义一个控制布局对象，使下拉框水平充满
			GridData moduleGrid = new GridData(GridData.FILL_HORIZONTAL);
			StaticVariable.historyCombo.setLayoutData(moduleGrid);
			//为下拉框添加监听事件
			StaticVariable.historyCombo.addSelectionListener(new HistoryComboListener(StaticVariable.historyCombo));
			StaticVariable.historyCharBtn = new Button(historyComp, SWT.NONE);
			StaticVariable.historyCharBtn.setText("图表数据");
		}
		
		// 定义第四个选项卡
		final TabItem askQuestion = new TabItem(tabFolder, SWT.NONE);
		askQuestion.setText("提问");
		{
			//为该选项卡添加一个面板
			Composite questionComp = new Composite(tabFolder, SWT.BORDER);
			questionComp.setLayout(new FillLayout());
			askQuestion.setControl(questionComp);
			//设置该面板的布局为网格布局，设置成两列
			questionComp.setLayout(new FillLayout());
			//定义一张表格
			StaticVariable.askQuestions = new Table(questionComp, SWT.MULTI);
			// 设置表头可见
			StaticVariable.askQuestions.setHeaderVisible(true);
			// 设置网格线可见
			StaticVariable.askQuestions.setLinesVisible(true);
			// 定义表中的列
			TableColumn nameColumn = new TableColumn(StaticVariable.askQuestions, SWT.NONE);
			nameColumn.setText("学生");
			nameColumn.setWidth(150);
			TableColumn questionColumn = new TableColumn(StaticVariable.askQuestions, SWT.NONE);
			questionColumn.setText("问题");
			questionColumn.setWidth(textView.getSize().x);
		}
		
		// 为选项卡添加监听事件
		tabFolder.addSelectionListener(new TabFordlerListener(tabFolder, StaticVariable.historyCombo));
		
		Composite statusBar = new Composite(mainFrame, SWT.BORDER);
		statusBar.setBounds(0, (int) (windowHeight * 0.87), windowWidth, 33);

		// 给所有按钮添加监听器
		first.addSelectionListener(new ButtonDownListener("first"));
		previous.addSelectionListener(new ButtonDownListener("previous"));
		next.addSelectionListener(new ButtonDownListener("next"));
		last.addSelectionListener(new ButtonDownListener("last"));
		StaticVariable.refresh.addSelectionListener(new ButtonDownListener("refresh"));
		StaticVariable.scoreChartBtn.addSelectionListener(new ScoreChartBtnListener(parent));
		StaticVariable.historyCharBtn.addSelectionListener(new HistoryCharBtnListener(parent));

		parent.getShell().addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent arg0) {
				int windowWidth = parent.getShell().getSize().x;
				int windowHeight = parent.getShell().getSize().y;
				onlineView.setBounds(0, 0, (int) (windowWidth * 0.3), (int) (windowHeight - 130));
				// System.out.println(windowHeight + ", " +(int)(windowHeight *
				// 0.87));
				createRoom.setBounds(0, 0, onlineView.getSize().x, 33);
				onlineNum.setBounds(0, 33, onlineView.getSize().x, 33);
				onlineUser.setBounds(0, 66, onlineView.getSize().x, onlineView.getSize().y - 70);
				textView.setBounds((int) (windowWidth * 0.3), 0, (int) (windowWidth * 0.69), onlineView.getSize().y);
				// System.out.println(parent.getShell().getSize().x);
				statusBar.setBounds(0, windowHeight - 33, windowWidth, 33);
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		return mainFrame;
	}

	protected MenuManager createMenuManager() {
		MenuManager menuBar = new MenuManager();
		MenuManager fileMenu = new MenuManager(" 文件(&F)");
		MenuManager editorMenu = new MenuManager(" 编辑(&E)");
		MenuManager helpMenu = new MenuManager(" 帮助(&H)");
		// 在文件菜单项添加下拉菜单
		fileMenu.add(newCreate);
		fileMenu.add(openFile);
		fileMenu.add(new Separator());
		fileMenu.add(saveFile);
		fileMenu.add(saveAsFile);
		fileMenu.add(new Separator());
		fileMenu.add(exit);
		// 在编辑菜单下添加下拉菜单
		editorMenu.add(copyFile);
		editorMenu.add(pasteFile);
		editorMenu.add(cutFile);
		editorMenu.add(new Separator());
		editorMenu.add(setFont);
		editorMenu.add(setColor);
		editorMenu.add(new Separator());
		editorMenu.add(selectAll);
		editorMenu.add(formate);
		helpMenu.add(about);
		// 在 menuBar 上添加文件菜单、编辑菜单和帮助菜单
		menuBar.add(fileMenu);
		menuBar.add(editorMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	// 在工具栏上添加工具栏按钮
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		// gtoolBarManager.add(new NewCreateAction());
		toolBarManager.add(new OpenFileAction());
		toolBarManager.add(new NewCreateAction());
		// toolBarManager.add(new SaveFileAction());
		// toolBarManager.add(new Separator());
		// toolBarManager.add(new CopyFileAction());
		// toolBarManager.add(new PasteFileAction());
		// toolBarManager.add(new CutFileAction());
		// toolBarManager.add(new Separator());
		// toolBarManager.add(new BlodAction());
		// toolBarManager.add(new ItalicAction());
		// toolBarManager.add(new UnderlineAction());
		return toolBarManager;
	}

	/**
	 * 新建教室，菜单栏
	 * @author wanli
	 *
	 */
	class NewCreate extends Action {
		public NewCreate() {
			super("NewCreateAction@Ctrl+N", Action.AS_PUSH_BUTTON);
			setText(" 新建教室");
		}

		public void run() {
			new CreateClassListener(StaticVariable.parent);
			createClass();
		}
	}

	/**
	 * 新建教室，工具栏
	 * @author wanli
	 *
	 */
	class NewCreateAction extends Action {
		public NewCreateAction() {
			super("NewCreateAction@Ctrl+N", Action.AS_PUSH_BUTTON);
			setText(" 新建教室");
			try {
				// 载入图像
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/create_class.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}

		public void run() {
			new CreateClassListener(StaticVariable.parent);
			createClass();
		}
	}

	/**
	 * 打开文件，菜单栏
	 * @author wanli
	 *
	 */
	class OpenFile extends Action {
		public OpenFile() {
			super("OpenFileAction@Ctrl+O", Action.AS_PUSH_BUTTON);
			setText(" 打开");
		}

		public void run() {
			// 在打开新的文件之前，判断是否保存当前文件
			if (judgeTextSave())
				OpenTextFile();
		}
	}

	/**
	 * 打开文件，工具栏
	 * @author wanli
	 *
	 */
	class OpenFileAction extends Action {
		public OpenFileAction() {
			super("OpenFileAction@Ctrl+O", Action.AS_PUSH_BUTTON);
			setText(" 打开");
			try {
				// 载入图像
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/open_file.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}

		public void run() {
			// 在打开新的文件之前，判断是否保存当前文件
			if (judgeTextSave())
				OpenTextFile();
		}
	}

	/**
	 * 保存文件
	 * @author wanli
	 *
	 */
	class SaveFileAction extends Action {
		public SaveFileAction() {
			super("SaveFileAction@Ctrl+S", Action.AS_PUSH_BUTTON);
			setText(" 保存");
		}

		public void run() {
			saveTextFile();
		}
	}

	/**
	 * 文件另存为
	 * @author wanli
	 *
	 */
	class SaveAsFileAction extends Action {
		public SaveAsFileAction() {
			super(" 另存为@Alt+A", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			saveFileAs();
		}
	}

	/**
	 * 退出程序
	 * @author wanli
	 *
	 */
	class ExitAction extends Action {
		public ExitAction() {
			super(" 退出@Ctrl+E", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			getShell().dispose();
		}
	}

	/**
	 * 复制
	 * @author wanli
	 *
	 */
	class CopyFileAction extends Action {
		public CopyFileAction() {
			super("CopyFileAction@Ctrl+C", Action.AS_PUSH_BUTTON);
			setText(" 复制");
		}

		public void run() {
			StaticVariable.text.copy();
		}
	}

	/**
	 * 粘贴
	 * @author wanli
	 *
	 */
	class PasteFileAction extends Action {
		public PasteFileAction() {
			super("PasteFileAction@Ctrl+V", Action.AS_PUSH_BUTTON);
			setText(" 粘贴");
		}

		public void run() {
			StaticVariable.text.paste();
		}
	}

	/**
	 * 剪切
	 * @author wanli
	 *
	 */
	class CutFileAction extends Action {
		public CutFileAction() {
			super("CutFileAction @Ctrl+X", Action.AS_PUSH_BUTTON);
			setText(" 剪切");
		}

		public void run() {
			StaticVariable.text.cut();
		}
	}

	/**
	 * 设置字体
	 * @author wanli
	 *
	 */
	class SetFontAction extends Action {
		public SetFontAction() {
			super(" 设置字体@Alt+F", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			FontDialog fontDialog = new FontDialog(getShell());
			fontDialog.setFontList((StaticVariable.text.getFont()).getFontData());
			FontData fontData = fontDialog.open();
			if (fontData != null) {
				if (font != null) {
					font.dispose();
				}
				font = new Font(getShell().getDisplay(), fontData);
				StaticVariable.text.setFont(font);
			}
		}
	}

	/**
	 * 设置字体颜色
	 * @author wanli
	 *
	 */
	class SetColorAction extends Action {
		public SetColorAction() {
			super(" 设置颜色@Alt+C", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			// 定义颜色选择对话框
			ColorDialog dlg = new ColorDialog(getShell());
			// 打开对话框
			RGB rgb = dlg.open();
			if (rgb != null) {
				// 定义 color 对象
				color = new Color(getShell().getDisplay(), rgb);
				// 定义 point 对象，获取选择范围。
				Point point = StaticVariable.text.getSelectionRange();
				for (int i = point.x; i < point.x + point.y; i++) {
					// 获得选中的字体样式和范围
					range = StaticVariable.text.getStyleRangeAtOffset(i);
					// 如果字体设置了其他样式( 如加粗、斜体、加下划线)
					if (range != null) {
						/**
						 * 设置一个与原来 StyleRange 的值相同的 StyleRange
						 */
						style = (StyleRange) range.clone();
						style.start = i;
						style.length = 1;
						// 设置前景颜色
						style.foreground = color;
					} else {

						style = new StyleRange(i, 1, color, null, SWT.NORMAL);
					}
					StaticVariable.text.setStyleRange(style);
				}
			}
		}
	}

	/**
	 * 全选
	 * @author wanli
	 *
	 */
	class SelectAllAction extends Action {
		public SelectAllAction() {
			super(" 全选@Ctrl+A", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			StaticVariable.text.selectAll();
		}
	}

	/**
	 * 格式化
	 * @author wanli
	 *
	 */
	class FormateAction extends Action {
		public FormateAction() {
			super(" 格式化@Ctrl+W", Action.AS_CHECK_BOX);
		}

		public void run() {
			StaticVariable.text.setWordWrap(isChecked());
		}
	}

	/**
	 * 关于
	 * @author wanli
	 *
	 */
	class AboutAction extends Action {
		public AboutAction() {
			super(" 关于@Ctrl+H", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
			messageBox.setMessage(" ClassForEvery 2.0 版本！ ");
			messageBox.open();
		}
	}

	class BlodAction extends Action {
		public BlodAction() {
			setText(" 加粗");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				StyleRange range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (range != null) {
					style = (StyleRange) range.clone();
					style.start = i;
					style.length = 1;
				} else {
					style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// 加粗字体
				style.fontStyle ^= SWT.BOLD;
				StaticVariable.text.setStyleRange(style);
			}
		}
	}

	class ItalicAction extends Action {
		public ItalicAction() {
			setText(" 斜体");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (range != null) {
					style = (StyleRange) range.clone();
					style.start = i;
					style.length = 1;
				} else {
					style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// 设置为斜体
				style.fontStyle ^= SWT.ITALIC;
				StaticVariable.text.setStyleRange(style);
			}
		}
	}

	class UnderlineAction extends Action {
		public UnderlineAction() {
			setText(" 下划线");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (range != null) {
					style = (StyleRange) range.clone();
					style.start = i;
					style.length = 1;
				} else {
					style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// 设置下划线
				style.underline = !style.underline;
				StaticVariable.text.setStyleRange(style);
			}
		}
	}

	boolean judgeTextSave() {
		if (!changes)
			return true;
		MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
		messageBox.setMessage(" 是否保存对文件的更改？ ");
		messageBox.setText(" ClassForEveryone V2.0");
		int message = messageBox.open();
		if (message == SWT.YES) {
			return saveTextFile();
		} else if (message == SWT.NO) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 打开文件对话框
	 * @return
	 */
	boolean OpenTextFile() {
		StaticVariable.index = 1;
		// 定义对话框，类型为打开型
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		// 设置对话框打开的限定类型
		dialog.setFilterExtensions(new String[] { " *.txt", "*.doc", "*.docx"});
		// 打开对话框，并返回打开文件的路径
		String openFile = dialog.open();
		if (openFile == null) {
			return false;
		}
		// 打开指定的文件
		file = new File(openFile);
		String fileExtension = file.getName().substring(file.getName().indexOf("."));
		try {
			//读取扩展名为.doc的word文档
			HWPFDocument doc = null;
			//读取扩展名为.docx的word文档
			XWPFDocument docx = null;
			XWPFWordExtractor extractor = null;
			//读取扩展名为.txt的文档
			StringBuffer sb = null;
			String fileText = null;
			BufferedReader reader = null;
			FileInputStream in = null;
			if (fileExtension.equals(".doc")) {
				in = new FileInputStream(file);
				doc = new HWPFDocument(in);
				fileText = doc.getDocumentText();
				StaticVariable.questions = fileText.split(new String("#\\^"));
				StaticVariable.text.setText(StaticVariable.questions[1]);
			} else if (fileExtension.equals(".docx")) {
				in = new FileInputStream(file);
				docx = new XWPFDocument(in);
				extractor = new XWPFWordExtractor(docx);
				fileText = extractor.getText();
				StaticVariable.questions = fileText.split(new String("#\\^"));
				StaticVariable.text.setText(StaticVariable.questions[1]);
			}
			else {
				// 读取文件
				FileReader fileReader = new FileReader(file);
				// 把字符流的字符读入缓冲区
				reader = new BufferedReader(fileReader);
				sb = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					// 通过 append() 方法实现将字符串添加到字符缓冲区。
					sb.append(line);
					sb.append("\r\n");
				}
				// 将读取的文件用字符串"#^"分开，因为'^'是转义字符，所以前面要加\\
				StaticVariable.questions = sb.toString().split(new String("#\\^"));	
				StaticVariable.text.setText(StaticVariable.questions[1]);
			}
			System.out.println(StaticVariable.questions.length);
			int num = StaticVariable.questions.length - 1;
			String fileName = file.getName();
			shell.setText(APPNAME + "-" + file);
			StaticVariable.tableName = fileName.substring(0, fileName.indexOf("."));
			dbService.createTable(num, StaticVariable.tableName);
			//有文件，启用按钮
			first.setEnabled(true);
			previous.setEnabled(true);
			next.setEnabled(true);
			last.setEnabled(true);
			if (reader != null) {
				reader.close();				
			}
			if (in != null) {
				in.close();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存文件对话框
	 * @return
	 */
	boolean saveTextFile() {
		if (file == null) {
			// 定义文件选择对话框，类型为保存型
			FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
			dialog.setText(" 保存");
			// 设置对话框保存的限定类型
			dialog.setFilterExtensions(new String[] { " *.txt", "*.doc", "*.docx"});
			// 打开对话框，并返回保存文件的路径
			String saveFile = dialog.open();
			if (saveFile == null) {
				return false;
			}
			file = new File(saveFile);
		}
		try {
			FileWriter writer = new FileWriter(file);
			StringBuffer fileString = new StringBuffer();
			StaticVariable.questions[StaticVariable.index] = StaticVariable.text.getText();
			for (int i = 1; i <= StaticVariable.questions.length - 1; i++) {
				fileString.append("#\\^" + StaticVariable.questions[i] + "\r\n");
			}
			writer.write(fileString.toString());
			writer.close();
			changes = false;
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	/**
	 * 判断是否另存为
	 * @return
	 */
	boolean saveFileAs() {
		SafeSaveDialog dlg = new SafeSaveDialog(getShell());
		String temp = dlg.open();
		if (temp == null) {
			return false;
		}
		file = new File(temp);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(StaticVariable.text.getText());
			writer.close();
		} catch (IOException e) {
		}
		return false;
	}

	/**
	 * 另存为对话框
	 * @author wanli
	 *
	 */
	class SafeSaveDialog {
		private FileDialog dlg;

		public SafeSaveDialog(Shell shell) {
			dlg = new FileDialog(shell, SWT.SAVE);
			dlg.setFilterExtensions(new String[] { "*.txt", "*.doc", "*.docx"});
		}

		public String open() {
			String fileName = null;
			boolean done = false;
			while (!done) {
				// 打开另存为对话框，并返回保存路径
				fileName = dlg.open();
				if (fileName == null) {
					done = true;
				} else {
					// 判断保存的文件是否已经存在
					File file = new File(fileName);
					if (file.exists()) {
						// 若文件存在，则弹出提示性的对话框
						MessageBox mb = new MessageBox(dlg.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
						// 提示性的信息
						mb.setMessage(fileName + " 已经存在，是否将该文件替换?");
						/**
						 * 单击"yes" 按钮将磁盘上的文件替换 否则重新填写文件名
						 */
						done = mb.open() == SWT.YES;
					} else {
						done = true;
					}
				}
			}
			return fileName;
		}
	}

	/**
	 * 创建教室，生成树
	 */
	public void createClass() {
		if (StaticVariable.className != null && StaticVariable.className != "") {
			TreeItem classroom = new TreeItem(tree, SWT.NONE);
			classroom.setText(StaticVariable.className);
			StaticVariable.rooms.add(classroom);
		}
	}
}
