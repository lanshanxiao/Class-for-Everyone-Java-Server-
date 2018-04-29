package com.wanli.swing.frame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wanli.classforereryone.server.MyServer;
import com.wanli.classforereryone.server.ServerThread;
import com.wanli.swing.frame.listener.AskQuestionTableListener;
import com.wanli.swing.frame.listener.ButtonDownListener;
import com.wanli.swing.frame.listener.CreateClassListener;
import com.wanli.swing.frame.listener.HistoryCharBtnListener;
import com.wanli.swing.frame.listener.HistoryComboListener;
import com.wanli.swing.frame.listener.OnlineTreeListener;
import com.wanli.swing.frame.listener.QuestionSelectComboListener;
import com.wanli.swing.frame.listener.ScoreChartBtnListener;
import com.wanli.swing.frame.listener.TabFordlerListener;
import com.wanli.swing.frame.text.menu.listener.BoldFont;
import com.wanli.swing.frame.text.menu.listener.CopyText;
import com.wanli.swing.frame.text.menu.listener.CutText;
import com.wanli.swing.frame.text.menu.listener.ItalicText;
import com.wanli.swing.frame.text.menu.listener.PasteText;
import com.wanli.swing.frame.text.menu.listener.SelectAll;
import com.wanli.swing.frame.text.menu.listener.SetTextColor;
import com.wanli.swing.frame.text.menu.listener.UnderlineText;
import com.wanli.swing.service.DBService;
import com.wanli.swing.service.DBServiceUser;
import com.wanli.thread.ListeningSocket;
import com.wanli.utils.StaticVariable;
import com.wanli.utils.XmlToJavaBean;

/**
 * ���������
 * @author wanli
 *
 */
public class MainFrame extends ApplicationWindow {
	private static String APPNAME = "Class For Everyone";	// ��ǰ���������
	private static String welcome = "��ӭ����";				// ��ʾ��ӭ��ʾ
	private Shell shell;									// �����ڵ�shell��
	private Action newCreate;								// �½�����
	private Action openFile;								// ���ļ�
//	private Action saveFile;								// �����ļ�
//	private Action saveAsFile;								// ���Ϊ
	private Action unsubscribe;								// ע��
	private Action exit;									// �˳�����
	private Action copyFile;								// ����
	private Action pasteFile;								// ճ��
	private Action cutFile;									// ����
	private Action setFont;									// ��������
	private Action setColor;								// ������ɫ
	private Action selectAll;								// ȫѡ
	private Action formate;									// ��ʽ
	private Action about;									// ����
	private Tree tree;										// ��ʾ�����û��б�
	private Font font;										// ����
	private File file;										// �ļ�
	private TabFolder tabFolder;							// ѡ�
//	private Button first;									// ��ת����һ��
//	private Button previous;								// ��һ��
//	private Button next;									// ��һ��
//	private Button last;									// ���һ��
	private String userName;								// �û���
	boolean changes;										// �ĵ��Ƿ�ı�
	private DBService dbService;							// ��������dao��ҵ��
	private Runtime runtime;								// ��ȡ��ǰ���������ʱ
	private Process process;								// �����洢���õ��ⲿ����
	private DBServiceUser serviceUser;	
	private String nickname;
	
	public MainFrame(String userName) {
		// ���𴰿�
		super(null);
		// ����һ���̣߳����������ͻ��˵�����
		new Thread(new MyServer()).start();
		// ����һ���̣߳������ͻ��˵����ӣ��������пͻ��������Ͼ���ʾ�ͻ��˵��������
		new Thread(new ListeningSocket()).start();
		this.userName = userName;// ��ȡ�û���
		newCreate = new NewCreate();
		openFile = new OpenFile();
//		saveFile = new SaveFileAction();
//		saveAsFile = new SaveAsFileAction();
		unsubscribe = new UnsubscribeAction();
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
		addMenuBar();// ��Ӳ˵���
		addToolBar(SWT.FLAT);// ��ӹ�����
		serviceUser = new DBServiceUser();
		nickname = serviceUser.getNicknameByPhoneOrEmail(userName);
	}

	public void run() {
		// ֱ�����ڹرշ��ܴ� open() ��������
		setBlockOnOpen(true);
		// �򿪴���
		open();
		if (!Display.getCurrent().isDisposed()) {
			// �رմ��ڣ��ͷ��ڲ���ϵͳ���õ�����Դ
			Display.getCurrent().dispose();			
		}
	}
	
	/**
	 * ʹ�Ի��������ʾ
	 * @param display
	 * @param shell
	 */
	protected void center(Display display, Shell shell) {
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	// ���ô������
	@Override
	public Control createContents(Composite parent) {
		// ���ô����С
		Rectangle bounds = parent.getShell().getDisplay().getPrimaryMonitor().getBounds();
		int windowWidth = bounds.width;
		int windowHeight = (int) (bounds.height * 0.98);
		parent.getShell().getDisplay();
		center(parent.getDisplay(), parent.getShell());
		// ���ô������
		parent.getShell().setText(APPNAME);
		parent.getShell().setImage(SWTResourceManager.getImage("image/1.jpg"));
		shell = parent.getShell();
		StaticVariable.parent = parent;
		// ���������
		Composite mainFrame = new Composite(parent, SWT.NONE);
		mainFrame.setLayout(null);
		// ������ʾ����������
		Composite onlineView = new Composite(mainFrame, SWT.BORDER);
		onlineView.setBounds(0, 0, (int) (windowWidth * 0.3), (int) (windowHeight * 0.87));
		// ���ô����������
		Composite createRoom = new Composite(onlineView, SWT.BORDER);
		createRoom.setBounds(0, 0, onlineView.getSize().x, (int) (onlineView.getSize().y * 0.04));
		createRoom.setLayout(new FillLayout());
		// �������Ұ�ť
		Button button = new Button(createRoom, SWT.NONE);
		FormData fd_button = new FormData();
		fd_button.left = new FormAttachment(0);
		button.setLayoutData(fd_button);
		button.setText("\u5F00\u542F\u9738\u5C4F");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ServerThread.sendToAllClient("��������");
			}
		});
		// ��ͼ��ť
		Button screenshot = new Button(createRoom, SWT.NONE);
		screenshot.setText("��ѧ��ͼ");
		screenshot.setLayoutData(fd_button);
		screenshot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				runtime = Runtime.getRuntime();		// ��ȡ��ǰ���������ʱ
				try {
					// ִ��windowsϵͳ�Դ��Ľ�ͼ���߳��򣬲�ͨ��process����ý���
					process = runtime.exec("C:\\Windows\\System32\\SnippingTool.exe");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
//		Button messBtn = new Button(createRoom, SWT.NONE);
//		messBtn.setText("����");
//		messBtn.setLayoutData(fd_button);
//		messBtn.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				new AskQuestionTableListener(parent);
//			}
//		});

		// ������ʾ�����������
		Composite onlineNum = new Composite(onlineView, SWT.BORDER);
		onlineNum.setBounds(0, (int) (onlineView.getSize().y * 0.04), onlineView.getSize().x,
				(int) (onlineView.getSize().y * 0.04));

		// ��ʾ��¼��Ϣ
		Label welcome = new Label(onlineNum, SWT.NONE);
		welcome.setBounds(0, 0, 300, 32);
		welcome.setText(MainFrame.welcome + this.nickname);

		// ��ʾ��������
		StaticVariable.onlining = new Label(onlineNum, SWT.NONE);
		StaticVariable.onlining.setBounds(300, 0, 300, 32);
		StaticVariable.onlining.setText(StaticVariable.onlineNumsStr + StaticVariable.onlineNumsInt);

		// ������ʾ�����û��б����
		Composite onlineUser = new Composite(onlineView, SWT.BORDER);
		onlineUser.setBounds(0, onlineNum.getSize().y + createRoom.getSize().y, onlineView.getSize().x,
				(int) (onlineView.getSize().y * 0.92));
		onlineUser.setLayout(new FillLayout(SWT.HORIZONTAL));

		// ��������ʽ��ʾ�����û��б�
		tree = new Tree(onlineUser, SWT.BORDER);
		tree.addMouseListener(new OnlineTreeListener(tree, parent));
		
		// ������ʾ��Ŀ���ɼ�����ʷ��¼�ȵ����
		Composite textView = new Composite(mainFrame, SWT.BORDER);
		textView.setBounds((int) (windowWidth * 0.3), 0, (int) (windowWidth * 0.69), onlineView.getSize().y);
		textView.setLayout(new GridLayout(6, false));

		// ��Ŀ���ɼ�����ʷ��¼ѡ�
		tabFolder = new TabFolder(textView, SWT.NONE);
		GridData gridTab = new GridData(GridData.FILL_BOTH);
		gridTab.horizontalSpan = 6;
		tabFolder.setLayoutData(gridTab);

		// �����һ��ѡ�
		final TabItem question = new TabItem(tabFolder, SWT.NONE);
		question.setText("��Ŀ");
		{
			//Ϊ��ѡ�����һ�����
			Composite questionComp = new Composite(tabFolder, SWT.BORDER);
			question.setControl(questionComp);
			//���ø����Ϊ����ʽ���֣�������Ϊ4��
			questionComp.setLayout(new GridLayout(4, false));
			//���һ����壬��������StyledText�ؼ�
			Composite textComp = new Composite(questionComp, SWT.BORDER);
			//����textComp���Ĳ���Ϊ����ʽ����
			textComp.setLayout(new FillLayout());
			StaticVariable.text = new StyledText(textComp, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
			StaticVariable.text.setRightMargin(40);
			StaticVariable.text.setLeftMargin(40);
			StaticVariable.text.setFont(new Font(parent.getDisplay(), "Arial", 20, SWT.NONE));
			StaticVariable.text.setBounds(0, 0, textComp.getSize().x, questionComp.getSize().y);
			// Ϊtext����Ҽ��˵�
			Menu menu = new Menu(shell, SWT.POP_UP);
			// ���ȫѡ�˵�
			MenuItem selectAllItem = new MenuItem(menu, SWT.PUSH);
			selectAllItem.setText("ȫѡ");
			selectAllItem.setImage(new Image(parent.getDisplay(), "image/select_all.png"));
			selectAllItem.addSelectionListener(new SelectAll());
			// ��Ӹ��Ʋ˵�
			MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
			copyItem.setText("����");
			copyItem.setImage(new Image(parent.getDisplay(), "image/copy.png"));
			copyItem.addSelectionListener(new CopyText());
			// �������˵�
			MenuItem pasteItem = new MenuItem(menu, SWT.PUSH);
			pasteItem.setText("���");
			pasteItem.setImage(new Image(parent.getDisplay(), "image/paste.png"));
			pasteItem.addSelectionListener(new PasteText());
			// ��Ӽ��в˵�
			MenuItem cutItem = new MenuItem(menu, SWT.PUSH);
			cutItem.setText("����");
			cutItem.setImage(new Image(parent.getDisplay(), "image/cut.png"));
			cutItem.addSelectionListener(new CutText());
			// ��ӼӴֲ˵�
			MenuItem boldItem = new MenuItem(menu, SWT.PUSH);
			boldItem.setText("�Ӵ�");
			boldItem.setImage(new Image(parent.getDisplay(), "image/bold.png"));
			boldItem.addSelectionListener(new BoldFont());
			// ���б��˵�
			MenuItem italicItem = new MenuItem(menu, SWT.PUSH);
			italicItem.setText("б��");
			italicItem.setImage(new Image(parent.getDisplay(), "image/italic.png"));
			italicItem.addSelectionListener(new ItalicText());
			// ����»��߲˵�
			MenuItem underlineItem = new MenuItem(menu, SWT.PUSH);
			underlineItem.setText("�»���");
			underlineItem.setImage(new Image(parent.getDisplay(), "image/underline.png"));
			underlineItem.addSelectionListener(new UnderlineText());
			// ���������ɫ�˵�
			MenuItem colorItem = new MenuItem(menu, SWT.PUSH);
			colorItem.setText("������ɫ");
			colorItem.setImage(new Image(parent.getDisplay(), "image/font_color.png"));
			colorItem.addSelectionListener(new SetTextColor());
			StaticVariable.text.setMenu(menu);
			//ΪsocreTableComp�������һ�����Ʋ��ֵĶ���GridTab1�����ø������ˮƽ����ֱ��������ȫ����
			GridData gridTab1 = new GridData(GridData.FILL_BOTH);
			//����socreTableComp��崹ֱռ4��
			gridTab1.horizontalSpan = 4;
			textComp.setLayoutData(gridTab1);
			// ����һ��Label�ؼ�
			Label choiceQues = new Label(questionComp, SWT.NONE);
			choiceQues.setText("ѡ����Ŀ��");
			// ����һ������������ѡ����Ŀ
			StaticVariable.questionSelect = new Combo(questionComp, SWT.READ_ONLY);
			StaticVariable.questionSelect.add("��ѡ����Ŀ");
			StaticVariable.questionSelect.select(0);
			GridData selectGrid = new GridData(GridData.FILL_HORIZONTAL);
			StaticVariable.questionSelect.setLayoutData(selectGrid);
			StaticVariable.questionSelect.addSelectionListener(new QuestionSelectComboListener());
//			StaticVariable.refresh = new Button(scoreComp, SWT.NONE);
//			StaticVariable.refresh.setText("ˢ��");
			StaticVariable.scoreChartBtn = new Button(questionComp, SWT.NONE);
			StaticVariable.scoreChartBtn.setText("ͼ������");
			StaticVariable.scoreChartBtn.setEnabled(false);
//			first = new Button(questionComp, SWT.NONE);
//			first.setText("����");
//			first.setEnabled(false);
//
//			previous = new Button(questionComp, SWT.NONE);
//			previous.setText("��һ��");
//			previous.setEnabled(false);
//
//			next = new Button(questionComp, SWT.NONE);
//			next.setText("��һ��");
//			next.setEnabled(false);
//
//			last = new Button(questionComp, SWT.NONE);
//			last.setText("ĩ��");
//			last.setEnabled(false);
		}

		// ����ڶ���ѡ�
		final TabItem score = new TabItem(tabFolder, SWT.NONE);
		score.setText("��");
		{
			//Ϊ��ѡ����һ�����
			Composite answerComp = new Composite(tabFolder, SWT.BORDER);
			score.setControl(answerComp);
			answerComp.setLayout(new FillLayout());
			StaticVariable.answer = new StyledText(answerComp, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
			StaticVariable.answer.setRightMargin(40);
			StaticVariable.answer.setLeftMargin(40);
			StaticVariable.answer.setFont(new Font(parent.getDisplay(), "Arial", 30, SWT.NONE));
			StaticVariable.answer.setBounds(0, 0, answerComp.getSize().x, answerComp.getSize().y);
//			//���ø����Ĳ���Ϊ���񲼾֣����ó�һ��
//			scoreComp.setLayout(new GridLayout(2, false));
//			//���һ����壬�������ñ��
//			Composite scoreTableComp = new Composite(scoreComp, SWT.BORDER);
//			//����socreTableComp���Ϊ����ʽ����
//			scoreTableComp.setLayout(new FillLayout());
//			// ������
//			StaticVariable.scoreTab = new Table(scoreTableComp, SWT.MULTI);
//			// ���ñ�ͷ�ɼ�
//			StaticVariable.scoreTab.setHeaderVisible(true);
//			// ���������߿ɼ�
//			StaticVariable.scoreTab.setLinesVisible(true);
//			//ΪsocreTableComp�������һ�����Ʋ��ֵĶ���GridTab2�����ø������ˮƽ����ֱ��������ȫ����
//			GridData gridTab2 = new GridData(GridData.FILL_BOTH);
//			//����socreTableComp��崹ֱռ����
//			gridTab2.horizontalSpan = 2;
//			scoreTableComp.setLayoutData(gridTab2);
//			//����һ��ˢ�±��İ�ť
//			StaticVariable.refresh = new Button(scoreComp, SWT.NONE);
//			StaticVariable.refresh.setText("ˢ��");
//			StaticVariable.scoreChartBtn = new Button(scoreComp, SWT.NONE);
//			StaticVariable.scoreChartBtn.setText("ͼ������");
		}

		// ���������ѡ�
		final TabItem history = new TabItem(tabFolder, SWT.NONE);
		history.setText("��ʷ��¼");
		{
			//Ϊ��ѡ����һ�����
			Composite historyComp = new Composite(tabFolder, SWT.BORDER);
			history.setControl(historyComp);
			//���ø����Ĳ���Ϊ���񲼾֣����ó�����
			historyComp.setLayout(new GridLayout(3, false));
			//���һ����壬�������ñ��
			Composite tableComp = new Composite(historyComp, SWT.BORDER);
			//����tableComp���Ĳ���Ϊ����ʽ����
			tableComp.setLayout(new FillLayout());
			//����һ�ű��
			StaticVariable.historyTab = new Table(tableComp, SWT.MULTI);
			// ���ñ�ͷ�ɼ�
			StaticVariable.historyTab.setHeaderVisible(true);
			// ���������߿ɼ�
			StaticVariable.historyTab.setLinesVisible(true);
			//ΪsocreTableComp�������һ�����Ʋ��ֵĶ���GridTab2�����ø������ˮƽ����ֱ��������ȫ����
			GridData gridTab3 = new GridData(GridData.FILL_BOTH);
			//����socreTableComp��崹ֱռ����
			gridTab3.horizontalSpan = 3;
			tableComp.setLayoutData(gridTab3);

			//����һ��Label�ؼ�
			Label hisTable = new Label(historyComp, SWT.NONE);
			hisTable.setText("ѡ����ʷ��");
			//����һ������������ѡ����ʷ��
			StaticVariable.historyCombo = new Combo(historyComp, SWT.READ_ONLY);
			//Ϊ��������һ�����Ʋ��ֶ���ʹ������ˮƽ����
			GridData moduleGrid = new GridData(GridData.FILL_HORIZONTAL);
			StaticVariable.historyCombo.setLayoutData(moduleGrid);
			//Ϊ��������Ӽ����¼�
			StaticVariable.historyCombo.addSelectionListener(new HistoryComboListener(StaticVariable.historyCombo));
//			StaticVariable.historyCharBtn = new Button(historyComp, SWT.NONE);
//			StaticVariable.historyCharBtn.setText("ͼ������");
		}
		
		// ������ĸ�ѡ�
//		final TabItem askQuestion = new TabItem(tabFolder, SWT.NONE);
		StaticVariable.askQuestion = new TabItem(tabFolder, SWT.NONE);
		StaticVariable.askQuestion.setText("����");
		
		{
			//Ϊ��ѡ����һ�����
			Composite questionComp = new Composite(tabFolder, SWT.BORDER);
			questionComp.setLayout(new FillLayout());
			StaticVariable.askQuestion.setControl(questionComp);
			//���ø����Ĳ���Ϊ���񲼾֣����ó�����
			questionComp.setLayout(new FillLayout());
			//����һ�ű��
			StaticVariable.askQuestions = new Table(questionComp, SWT.MULTI);
			// ���ñ�ͷ�ɼ�
			StaticVariable.askQuestions.setHeaderVisible(true);
			// ���������߿ɼ�
			StaticVariable.askQuestions.setLinesVisible(true);
			// ������е���
//			TableColumn nameColumn = new TableColumn(StaticVariable.askQuestions, SWT.NONE);
//			nameColumn.setText("ѧ��");
//			nameColumn.setWidth(150);
			TableColumn questionColumn = new TableColumn(StaticVariable.askQuestions, SWT.NONE);
			questionColumn.setText("����");
			questionColumn.setWidth(textView.getSize().x);
			StaticVariable.askQuestions.addMouseListener(new AskQuestionTableListener(parent));
		}
		
		// Ϊѡ���Ӽ����¼�
		tabFolder.addSelectionListener(new TabFordlerListener(tabFolder, StaticVariable.historyCombo));
		
		Composite statusBar = new Composite(mainFrame, SWT.BORDER);
		statusBar.setBounds(0, (int) (windowHeight * 0.87), windowWidth, 33);

		// �����а�ť��Ӽ�����
//		first.addSelectionListener(new ButtonDownListener("first"));
//		previous.addSelectionListener(new ButtonDownListener("previous"));
//		next.addSelectionListener(new ButtonDownListener("next"));
//		last.addSelectionListener(new ButtonDownListener("last"));
//		StaticVariable.refresh.addSelectionListener(new ButtonDownListener("refresh"));
		StaticVariable.scoreChartBtn.addSelectionListener(new ScoreChartBtnListener(parent));
//		StaticVariable.historyCharBtn.addSelectionListener(new HistoryCharBtnListener(parent));

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

	// ����رմ��ڰ�ťʱ�����Ķ���
	@Override
	protected void handleShellCloseEvent() { 
		MessageBox messagebox = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);  
	    messagebox.setMessage("��ȷ��Ҫ�˳���?");  
	    int message = messagebox.open();  
	    if (message == SWT.YES) {
	    	if (StaticVariable.correct.size() > 0) {
				int index = StaticVariable.questionSelect.getSelectionIndex();
//				String question = StaticVariable.questionsMap.get(Integer.toString(index));
				String question = StaticVariable.questionsList.get(index - 1);
				// �ָ��ַ���������Ŀ�𰸵ȷ���
				String[] strs = question.split("#\\^");
				if ((strs.length - 4) > 0) {
					// (strs.length - 4) > 0��ʾ�ж����
					StringBuffer answer = new StringBuffer();	// �洢����Ĵ�
					StringBuffer corStr = new StringBuffer();	// �洢�ش���ȷ�ĸ����������ʹ�ÿո�ָ�
					StringBuffer errStr = new StringBuffer();	// �洢�ش����ĸ����������ʹ�ÿո�ָ�
					StringBuffer unResStr = new StringBuffer();	// �洢δ�ش�ĸ����������ʹ�ÿո�ָ�
					// ������ȷ�������ʹ�������������δ�ش������
					for (int i = 0; i < StaticVariable.correct.size(); i++) {
						int unResponse = StaticVariable.unResponse.get(i).intValue();
						int correct = StaticVariable.correct.get(i).intValue();
						int error = StaticVariable.error.get(i).intValue();
						unResponse = StaticVariable.users.size() - correct - error;
						StaticVariable.unResponse.set(i, new Integer(unResponse));
						if (i == 0) {
							answer.append(strs[i + 3]);
							corStr.append(correct);
							errStr.append(error);
							unResStr.append(unResponse);
						} else {
							answer.append(" " + strs[i + 3]);
							corStr.append(" " + correct);
							errStr.append(" " + error);
							unResStr.append(" " + unResponse);
						}
					}
					String countStr = answer.toString() + "," + corStr.toString() + "," + errStr.toString() + "," + unResStr.toString();
					StaticVariable.answers.put("ͳ��", countStr);
					System.out.println(countStr);
				} else {
					int unResponse = StaticVariable.unResponse.get(0).intValue();
					int correct = StaticVariable.correct.get(0).intValue();
					int error = StaticVariable.error.get(0).intValue();
					unResponse = StaticVariable.users.size() - correct - error;
					StaticVariable.unResponse.set(0, new Integer(unResponse));
					String countStr = Integer.toString(correct) + "," + Integer.toString(error) + "," + Integer.toString(unResponse);
					StaticVariable.answers.put("ͳ��", countStr);
				}
				System.out.println(StaticVariable.correct.size());
				dbService.addRecord(StaticVariable.tableName, StaticVariable.answers, index);
			}
	        super.handleShellCloseEvent();  
	    }
		
	}
	protected MenuManager createMenuManager() {
		MenuManager menuBar = new MenuManager();
		MenuManager fileMenu = new MenuManager(" �ļ�(&F)");
		MenuManager editorMenu = new MenuManager(" �༭(&E)");
		MenuManager helpMenu = new MenuManager(" ����(&H)");
		// ���ļ��˵�����������˵�
		fileMenu.add(newCreate);
		fileMenu.add(openFile);
		// ��ӷָ���
		fileMenu.add(new Separator());
//		fileMenu.add(saveFile);
//		fileMenu.add(saveAsFile);
//		fileMenu.add(unsubscribe);
		// ��ӷָ���
		fileMenu.add(new Separator());
		fileMenu.add(exit);
		// �ڱ༭�˵�����������˵�
		editorMenu.add(copyFile);
		editorMenu.add(pasteFile);
		editorMenu.add(cutFile);
		// ��ӷָ���
		editorMenu.add(new Separator());
		editorMenu.add(setFont);
		editorMenu.add(setColor);
		// ��ӷָ���
		editorMenu.add(new Separator());
		editorMenu.add(selectAll);
		editorMenu.add(formate);
		helpMenu.add(about);
		// �� menuBar ������ļ��˵����༭�˵��Ͱ����˵�
		menuBar.add(fileMenu);
		menuBar.add(editorMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	// �ڹ���������ӹ�������ť
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		// gtoolBarManager.add(new NewCreateAction());
		toolBarManager.add(new OpenFileAction());
		toolBarManager.add(new CreateXmlFileAction());
		toolBarManager.add(new QuestionManagerAction());
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
	 * �½����ң��˵���
	 * @author wanli
	 *
	 */
	class NewCreate extends Action {
		public NewCreate() {
			super("NewCreateAction@Ctrl+N", Action.AS_PUSH_BUTTON);
			setText(" �½�����");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/createclass.png"));
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
	 * �½����ң�������
	 * @author wanli
	 *
	 */
	class NewCreateAction extends Action {
		public NewCreateAction() {
			super("NewCreateAction@Ctrl+N", Action.AS_PUSH_BUTTON);
			setText(" �½�����");
			try {
				// ����ͼ��
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
	 * ���ļ����˵���
	 * @author wanli
	 *
	 */
	class OpenFile extends Action {
		public OpenFile() {
			super("OpenFileAction@Ctrl+O", Action.AS_PUSH_BUTTON);
			setText(" ��");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/folder.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}

		public void run() {
			// �ڴ��µ��ļ�֮ǰ���ж��Ƿ񱣴浱ǰ�ļ�
			if (judgeTextSave())
				OpenTextFile();
		}
	}

	/**
	 * ���ļ���������
	 * @author wanli
	 *
	 */
	class OpenFileAction extends Action {
		public OpenFileAction() {
			super("OpenFileAction@Ctrl+O", Action.AS_PUSH_BUTTON);
			setText(" ��");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/open_file.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}

		public void run() {
			// �ڴ��µ��ļ�֮ǰ���ж��Ƿ񱣴浱ǰ�ļ�
			if (judgeTextSave())
				OpenTextFile();
		}
	}
	
	/**
	 * ��ǰ���⣬������
	 * @author wanli
	 *
	 */
	class CreateXmlFileAction extends Action {
		public CreateXmlFileAction() {
			super("CreateXmlFileAction@Ctrl+Shift+C", Action.AS_PUSH_BUTTON);
			setText("��ǰ����");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/before_class.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}
		
		public void run() {
			new PrepareLessons(StaticVariable.parent);
		}
	}
	
	class QuestionManagerAction extends Action {
		public QuestionManagerAction() {
			super("QuestionManagerAction@Ctrl+Shift+M", Action.AS_PUSH_BUTTON);
			setText("�������");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/manager.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				System.err.println(e.getMessage());
			}
		}
		public void run() {
//			new PrepareLessons(StaticVariable.parent);
			new QuestionManagerShell(StaticVariable.parent);
		}
	}

	/**
	 * �����ļ�
	 * @author wanli
	 *
	 */
	class SaveFileAction extends Action {
		public SaveFileAction() {
			super("SaveFileAction@Ctrl+S", Action.AS_PUSH_BUTTON);
			setText(" ����");
			try {
				// ����ͼ��
				ImageDescriptor icon = ImageDescriptor.createFromURL(new URL("file:image/save.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			saveTextFile();
		}
	}

	/**
	 * �ļ����Ϊ
	 * @author wanli
	 *
	 */
	class SaveAsFileAction extends Action {
		public SaveAsFileAction() {
			super(" ���Ϊ@Alt+A", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/saveas.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			saveFileAs();
		}
	}
	
	/**
	 * ע���˺�
	 * @author wanli
	 *
	 */
	class UnsubscribeAction extends Action {
		public UnsubscribeAction() {
			super(" ע���˺�@Ctrl+U", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/unsubscribe.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO);
			messageBox.setText("����");
			messageBox.setMessage("ȷ��Ҫע����ǰ�˺���");
			if (messageBox.open() == SWT.YES) {
//				Display.getCurrent().dispose();
				shell.dispose();
				
			}
		}
	}

	/**
	 * �˳�����
	 * @author wanli
	 *
	 */
	class ExitAction extends Action {
		public ExitAction() {
			super(" �˳�@Ctrl+E", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/close.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			getShell().dispose();
		}
	}

	/**
	 * ����
	 * @author wanli
	 *
	 */
	class CopyFileAction extends Action {
		public CopyFileAction() {
			super("CopyFileAction@Ctrl+C", Action.AS_PUSH_BUTTON);
			setText(" ����");
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/copy.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			StaticVariable.text.copy();
		}
	}

	/**
	 * ճ��
	 * @author wanli
	 *
	 */
	class PasteFileAction extends Action {
		public PasteFileAction() {
			super("PasteFileAction@Ctrl+V", Action.AS_PUSH_BUTTON);
			setText(" ճ��");
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/paste.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			StaticVariable.text.paste();
		}
	}

	/**
	 * ����
	 * @author wanli
	 *
	 */
	class CutFileAction extends Action {
		public CutFileAction() {
			super("CutFileAction @Ctrl+X", Action.AS_PUSH_BUTTON);
			setText(" ����");
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/cut.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			StaticVariable.text.cut();
		}
	}

	/**
	 * ��������
	 * @author wanli
	 *
	 */
	class SetFontAction extends Action {
		public SetFontAction() {
			super(" ��������@Alt+F", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/font.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			FontDialog fontDialog = new FontDialog(getShell());
			fontDialog.setFontList((StaticVariable.text.getFont()).getFontData());
			FontData fontData = fontDialog.open();
//			if (fontData != null) {
//				if (font != null) {
//					font.dispose();
//				}
//			}
			font = new Font(getShell().getDisplay(), fontData);
			StaticVariable.text.setFont(font);
		}
	}

	/**
	 * ����������ɫ
	 * @author wanli
	 *
	 */
	class SetColorAction extends Action {
		public SetColorAction() {
			super(" ������ɫ@Alt+C", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/font_color.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			// ������ɫѡ��Ի���
			ColorDialog dlg = new ColorDialog(getShell());
			// �򿪶Ի���
			RGB rgb = dlg.open();
			if (rgb != null) {
				// ���� color ����
				StaticVariable.color = new Color(getShell().getDisplay(), rgb);
				// ���� point ���󣬻�ȡѡ��Χ��
				Point point = StaticVariable.text.getSelectionRange();
				for (int i = point.x; i < point.x + point.y; i++) {
					// ���ѡ�е�������ʽ�ͷ�Χ
					StaticVariable.range = StaticVariable.text.getStyleRangeAtOffset(i);
					// �������������������ʽ( ��Ӵ֡�б�塢���»���)
					if (StaticVariable.range != null) {
						/**
						 * ����һ����ԭ�� StyleRange ��ֵ��ͬ�� StyleRange
						 */
						StaticVariable.style = (StyleRange) StaticVariable.range.clone();
						StaticVariable.style.start = i;
						StaticVariable.style.length = 1;
						// ����ǰ����ɫ
						StaticVariable.style.foreground = StaticVariable.color;
					} else {

						StaticVariable.style = new StyleRange(i, 1, StaticVariable.color, null, SWT.NORMAL);
					}
					StaticVariable.text.setStyleRange(StaticVariable.style);
				}
			}
		}
	}

	/**
	 * ȫѡ
	 * @author wanli
	 *
	 */
	class SelectAllAction extends Action {
		public SelectAllAction() {
			super(" ȫѡ@Ctrl+A", Action.AS_PUSH_BUTTON);
			// ����ͼ��
			ImageDescriptor icon;
			try {
				icon = ImageDescriptor.createFromURL(new URL("file:image/select_all.png"));
				setImageDescriptor(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			StaticVariable.text.selectAll();
		}
	}

	/**
	 * ��ʽ��
	 * @author wanli
	 *
	 */
	class FormateAction extends Action {
		public FormateAction() {
			super(" ��ʽ��@Ctrl+W", Action.AS_CHECK_BOX);
		}

		public void run() {
			StaticVariable.text.setWordWrap(isChecked());
		}
	}

	/**
	 * ����
	 * @author wanli
	 *
	 */
	class AboutAction extends Action {
		public AboutAction() {
			super(" ����@Ctrl+H", Action.AS_PUSH_BUTTON);
		}

		public void run() {
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
			messageBox.setMessage(" ClassForEvery 2.0 �汾�� ");
			messageBox.open();
		}
	}

	/**
	 * �Ӵ�
	 * @author wanli
	 *
	 */
	class BlodAction extends Action {
		public BlodAction() {
			setText(" �Ӵ�");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				StyleRange range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (range != null) {
					StaticVariable.style = (StyleRange) range.clone();
					StaticVariable.style.start = i;
					StaticVariable.style.length = 1;
				} else {
					StaticVariable.style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// �Ӵ�����
				StaticVariable.style.fontStyle ^= SWT.BOLD;
				StaticVariable.text.setStyleRange(StaticVariable.style);
			}
		}
	}

	/**
	 * б��
	 * @author wanli
	 *
	 */
	class ItalicAction extends Action {
		public ItalicAction() {
			setText(" б��");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				StaticVariable.range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (StaticVariable.range != null) {
					StaticVariable.style = (StyleRange) StaticVariable.range.clone();
					StaticVariable.style.start = i;
					StaticVariable.style.length = 1;
				} else {
					StaticVariable.style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// ����Ϊб��
				StaticVariable.style.fontStyle ^= SWT.ITALIC;
				StaticVariable.text.setStyleRange(StaticVariable.style);
			}
		}
	}

	/**
	 * �»���
	 * @author wanli
	 *
	 */
	class UnderlineAction extends Action {
		public UnderlineAction() {
			setText(" �»���");
		}

		public void run() {
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				StaticVariable.range = StaticVariable.text.getStyleRangeAtOffset(i);
				if (StaticVariable.range != null) {
					StaticVariable.style = (StyleRange) StaticVariable.range.clone();
					StaticVariable.style.start = i;
					StaticVariable.style.length = 1;
				} else {
					StaticVariable.style = new StyleRange(i, 1, null, null, SWT.NORMAL);
				}
				// �����»���
				StaticVariable.style.underline = !StaticVariable.style.underline;
				StaticVariable.text.setStyleRange(StaticVariable.style);
			}
		}
	}

	boolean judgeTextSave() {
		if (!changes)
			return true;
		MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
		messageBox.setMessage(" �Ƿ񱣴���ļ��ĸ��ģ� ");
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
	 * ���ļ��Ի���
	 * @return
	 */
	boolean OpenTextFile() {
		StaticVariable.index = 1;
		// ����Ի�������Ϊ����
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		// ���öԻ���򿪵��޶�����
		dialog.setFilterExtensions(new String[] { "*.xml"});
		// �򿪶Ի��򣬲����ش��ļ���·��
		String openFile = dialog.open();
		if (openFile == null) {
			return false;
		}
		// ��ָ�����ļ�
		file = new File(openFile);
		new XmlToJavaBean(file);
		shell.setText(APPNAME + "-" + file);
		String fileName = file.getName();
		StaticVariable.tableName = fileName.substring(0, fileName.indexOf("."));
//		dbService.createTable(StaticVariable.questionsMap.size(), StaticVariable.tableName);
		dbService.createTable(StaticVariable.allQuestionList, StaticVariable.tableName);
//		try {
//			
//			// ��ȡ�ļ�
//			FileReader fileReader = new FileReader(file);
//			// ���ַ������ַ����뻺����
//			BufferedReader reader = new BufferedReader(fileReader);
//			StringBuffer sb = new StringBuffer();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				// ͨ�� append() ����ʵ�ֽ��ַ�����ӵ��ַ���������
//				sb.append(line);
//				sb.append("\r\n");
//			}
//			
//			int num = StaticVariable.questions.length - 1;
//			String fileName = file.getName();
//			shell.setText(APPNAME + "-" + file);
//			StaticVariable.tableName = fileName.substring(0, fileName.indexOf("."));
//			dbService.createTable(num, StaticVariable.tableName);
//			
//			//���ļ������ð�ť
////			first.setEnabled(true);
////			previous.setEnabled(true);
////			next.setEnabled(true);
////			last.setEnabled(true);
//			if (reader != null) {
//				reader.close();				
//			}
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}

	/**
	 * �����ļ��Ի���
	 * @return
	 */
	boolean saveTextFile() {
		if (file == null) {
			// �����ļ�ѡ��Ի�������Ϊ������
			FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
			dialog.setText(" ����");
			// ���öԻ��򱣴���޶�����
			dialog.setFilterExtensions(new String[] { " *.txt", "*.doc", "*.docx"});
			// �򿪶Ի��򣬲����ر����ļ���·��
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
	 * �ж��Ƿ����Ϊ
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
	 * ���Ϊ�Ի���
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
				// �����Ϊ�Ի��򣬲����ر���·��
				fileName = dlg.open();
				if (fileName == null) {
					done = true;
				} else {
					// �жϱ�����ļ��Ƿ��Ѿ�����
					File file = new File(fileName);
					if (file.exists()) {
						// ���ļ����ڣ��򵯳���ʾ�ԵĶԻ���
						MessageBox mb = new MessageBox(dlg.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
						// ��ʾ�Ե���Ϣ
						mb.setMessage(fileName + " �Ѿ����ڣ��Ƿ񽫸��ļ��滻?");
						/**
						 * ����"yes" ��ť�������ϵ��ļ��滻 ����������д�ļ���
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
	 * �������ң�������
	 */
	public void createClass() {
		if (StaticVariable.className != null && StaticVariable.className != "") {
			TreeItem classroom = new TreeItem(tree, SWT.NONE);
			classroom.setText(StaticVariable.className);
			StaticVariable.rooms.add(classroom);
		}
	}
}
