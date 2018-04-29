package com.wanli.swing.frame;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wanli.swing.dao.DBDaoUser;
import com.wanli.swing.dao.RegistDao;
import com.wanli.swing.entities.UserBean;
import com.wanli.swing.frame.listener.ComboListener;

public class Login {
	private static String username;				// ��½���û���
	private static String password;				// ��½������
	private static Text textPassword;			// ����������ı��ؼ�
	private static Login window;				// ��ǰ�������
	private static UserBean user;				// �û���Ϣ��bean
	private static Image coverImg;				// ͼƬ
	private static Properties userProp;			// ��ȡ����ĵ�¼��Ϣ�����������б����ʾ
	private static Properties saveProp;			// ��ȡ����ĵ�¼��Ϣ�����ڴ洢��һ���˳�ʱ���ʺ�
	private static FileInputStream inStream;	// ����������ȡ��Ϣ
	private static FileWriter writer;			// �������д��Ϣ
	private static File userFile;				// ��ȡ�����û���Ϣ���ļ�
	private static File saveFile;				// ��ȡ������һ���˳�ʱ�ʺŵ���Ϣ
	private static Set<Object> itSet;			// ����������Ϣ
	private static Iterator keys;				// ���ڱ���������Ϣ
	private static Display display;
	private static Shell shell;

	/**
	 * ��ʼ������
	 */
	public Login() {
		this.user = new UserBean();
		userProp = new Properties();
		saveProp = new Properties();
		
		// ����File����
		userFile = new File("users.properties");		
		saveFile = new File("savecount.properties");
		try {
			// �����ļ�������
			inStream = new FileInputStream(userFile);
			// ���������м�������
			userProp.load(inStream);
			inStream = new FileInputStream(saveFile);
			saveProp.load(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		itSet = userProp.keySet();// ����ȡ����ϢתΪset
		keys = itSet.iterator();
	}
	
	public static void onCreate() {
		display = Display.getDefault();
		shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setImage(SWTResourceManager.getImage("image/1.jpg"));
		shell.setSize(474, 365);
		center(display, shell);
		shell.setText("Class For Everyone");

		// �����������
		Composite globalLayout = new Composite(shell, SWT.NONE);
		globalLayout.setLocation(0, 0);
		globalLayout.setSize(527, 338);
		globalLayout.setLayout(null);

		// ͼƬ��ʾ���
		Composite classforeveryone = new Composite(globalLayout, SWT.NONE);
		classforeveryone.setBounds(0, 0, 469, 154);
		classforeveryone.setBackgroundImage(SWTResourceManager.getImage("image/1.jpg"));

		// ��¼���
		Composite loginComposite = new Composite(globalLayout, SWT.NONE);
		loginComposite.setBounds(0, 152, 469, 186);

		// �����û���������������ʾ��ʷ��¼��Ϣ
		Combo comboUser = new Combo(loginComposite, SWT.NONE);
		comboUser.setBounds(120, 24, 220, 32);
		
		// ���������
		textPassword = new Text(loginComposite, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(120, 55, 220, 32);
		
		// ��ѡ�򣬱�������
		Button bRememberMe = new Button(loginComposite, SWT.CHECK);
		bRememberMe.setBounds(120, 93, 100, 28);
		bRememberMe.setForeground(SWTResourceManager.getColor(240, 248, 255));
		bRememberMe.setText("\u8BB0\u4F4F\u5BC6\u7801");
		
		// ��ѡ���Զ���¼
		Button bAutoLogin = new Button(loginComposite, SWT.CHECK);
		bAutoLogin.setBounds(240, 93, 100, 28);
		bAutoLogin.setForeground(SWTResourceManager.getColor(240, 248, 255));
		bAutoLogin.setText("\u81EA\u52A8\u767B\u5F55");
		
		// Ϊ��������ӵ���¼�
		comboUser.addSelectionListener(new ComboListener(comboUser, bRememberMe));
		
		// ����ȡ����Ϣȫ����ӵ���������ʾ
		while (keys.hasNext()) {
			comboUser.add((String)keys.next());
		}
		itSet = saveProp.keySet();
		keys = itSet.iterator();
		while (keys.hasNext()) {
			username = (String) keys.next();
			password = saveProp.getProperty(username);
			if (username != null) {
				comboUser.setText(username);
				textPassword.setText(password);
			}
			if (password != null && password.length() != 0) {
				bRememberMe.setSelection(true);
			} else {
				bRememberMe.setSelection(false);
			}
		}

		// ע���ʺ�����
		Link linkRegist = new Link(loginComposite, SWT.NONE);
		linkRegist.setBounds(350, 24, 100, 25);
		linkRegist.setText("<a>\u6CE8\u518C\u8D26\u53F7</a>");
		// Ϊ��������ӵ���¼�
		linkRegist.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				URI uri = null;
				try {
					// �������ת��ע��ҳ��
					uri = new URI("http://localhost:8090/graduation/regist.jsp");
					Desktop dtp = Desktop.getDesktop();
					dtp.browse(uri);
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

			}

		});

		// �������볬����
		Link linkForget = new Link(loginComposite, SWT.NONE);
		linkForget.setBounds(350, 55, 100, 25);
		linkForget.setText("<a>\u627E\u56DE\u5BC6\u7801</a>");
		// Ϊ��������ӵ���¼�
		linkForget.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				URI uri = null;
				try {
					// ��������Ӻ���ת���һ�����ҳ��
					uri = new URI("http://localhost:8090/graduation/forgetPassword.jsp");
					Desktop dtp = Desktop.getDesktop();
					dtp.browse(uri);
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

			}

		});

		// ��¼��ť
		Button bLogin = new Button(loginComposite, SWT.NONE);
		bLogin.setBounds(120, 137, 220, 27);
		bLogin.setText("\u767B\u5F55");

		// ��¼�û�ͷ��
		Composite composite = new Composite(loginComposite, SWT.NONE);
		composite.setBounds(32, 24, 98, 97);

		// Ϊ��¼��ť��ӵ���¼�
		bLogin.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// ��ȡ��¼���û���������
				window.getUser().setName(comboUser.getText());
				window.getUser().setPassword(textPassword.getText());
				DBDaoUser dao = new DBDaoUser();
				// �������ݿ�ȷ�ϸ��û��Ƿ���ڣ�������ִ�б����ʺŲ����ʹ�������
				if (dao.getUserByNameAndPassword(window.getUser().getName(), window.getUser().getPassword())) {
					// ��ձ�����һ�ε�¼��Ϣ���ļ���������εĵ�¼��Ϣ
					saveProp.clear();
					// �жϱ������븴ѡ���Ƿ�ѡ�У�ѡ����һ�𱣴����룬����ֻ�����ʺ�
					if (bRememberMe.getSelection()) {
						userProp.setProperty(comboUser.getText(), textPassword.getText());
						saveProp.setProperty(comboUser.getText(), textPassword.getText());
						try {
							writer = new FileWriter(userFile);
							userProp.store(writer, null);
							writer = new FileWriter(saveFile);
							saveProp.store(writer, null);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (writer != null) {
								try {
									writer.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					} else {
						userProp.setProperty(comboUser.getText(), "");
						saveProp.setProperty(comboUser.getText(), "");
			        	try {
							writer = new FileWriter(userFile);
							userProp.store(writer, null);
							writer = new FileWriter(saveFile);
							saveProp.store(writer, null);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (writer != null) {
								try {
									writer.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					shell.dispose();
					// ��������
					new MainFrame(window.getUser().getName()).run();
				} else {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("�û����������������������");
					messageBox.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		// ��ʾͼƬ
		window.setCoverImg(new Image(display, "image/1.jpg"));
	}

	public static void main(String[] args) {
		window = new Login();// ��ʼ��
		onCreate();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		System.exit(0);
	}
	
	/**
	 * ���ô���λ����Ļ�м�
	 * 
	 * @param display���豸
	 * @param shell��Ҫ����λ�õĴ��ڶ���
	 */
	public static void center(Display display, Shell shell) {
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public Image getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(Image coverImg) {
		this.coverImg = coverImg;
	}

	public static Shell getShell() {
		return shell;
	}

	public static void setShell(Shell shell) {
		Login.shell = shell;
	}
	
}
