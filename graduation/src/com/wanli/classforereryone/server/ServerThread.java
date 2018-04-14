package com.wanli.classforereryone.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Array;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.Mmmm;
import com.wanli.swing.entities.OnlineUser;
import com.wanli.swing.frame.MessagePOP_UP;
import com.wanli.swing.service.DBService;
import com.wanli.swing.service.DBServiceUser;
import com.wanli.utils.StaticVariable;

public class ServerThread implements Runnable {

	// 定义当前线程所处理的Socket
	private Socket s = null;
	// 该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	// 接收连接上服务端的客户端IP地址
	private static String ipAddress = "";
	// 存储学生提出的问题
	private String question;
	// 操作用户数据库
	private DBServiceUser dbServiceUser;
	// 操作成绩表数据库
	private DBService dbService;
	// 接收学生的答案
	private String[] answers;
	
	public ServerThread(Socket s) throws IOException {
		this.s = s;
		// 初始化该Socket对应的输入流
		br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
		dbServiceUser = new DBServiceUser();
		dbService = new DBService();
		
	}
	
	@Override
	public void run() {
		
		String content = null;
		int index = 0;
		ipAddress = s.getInetAddress().toString().substring(1);
		// 采用循环不断从Socket中读取客户端发送过来的数据
		while ((content = readFromClient()) != null) {
			
			String[] info = content.split(",");
//			System.out.println(Arrays.toString(info));
			switch(info[0]) {
				// 读取注册信息
				case "1":
					if (dbServiceUser.addUser(info)) {
						sendToClient("1");
					} else {
						sendToClient("1-false");
					}
					break;
				// 读取登录信息
				case "2":
					if (dbServiceUser.getUserByNameAndPassword(info[1], info[2])) {
						// 有客户端连接就把连接的客户端使用map存储
						try {
							System.out.println("客户端连接成功！！！");
							StaticVariable.users.put(s.getInetAddress().toString().substring(1), new OnlineUser(s));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sendToClient("2");
						System.out.println("登录了。。。。。");
						StaticVariable.users.get(ipAddress).setInetAddress(s.getInetAddress().toString().substring(1));
						StaticVariable.users.get(ipAddress).setUsername(info[1]);
					} else {
						sendToClient("2-false");
					}
					break;
				// 读取回答问题的信息
				case "3":
					if (StaticVariable.questions != null) {
						if (answers == null) {
							answers = new String[StaticVariable.questions.length - 1];
							answers[index] = info[2];
							index++;						
						} else {
							if (index < answers.length) {
								System.out.println(info[2]);
								answers[index] = info[2];
								if (index == answers.length - 1) {
									System.out.println(Arrays.toString(answers));
									dbService.addRecord(info[1], StaticVariable.tableName, answers);
								}
								index++;
							} else {
								index = 0;
							}							
						}
					}
//					System.out.println(info[2]);
					break;
				// 读取提问的信息，并且弹框提示有学生提问
				case "4":
					question = info[1] + ":::" + info[2]; 
					new Thread(new ListenningMessage(question)).start();
					StaticVariable.users.get(ipAddress).setContent(info[2]);
					System.out.println(info[0] + "," + info[1] + "," + info[2]);				
					break;
			}
			
		}
		// 跳出循环，表明该Socket对应的客户端已经关闭
		// 删除该Socket
		StaticVariable.quitSocket = s.getInetAddress().toString().substring(1);
		StaticVariable.users.remove(s.getInetAddress().toString().substring(1));
	}

	// 定义读取客户端数据的方法
	private String readFromClient() {
		try {
			return br.readLine();
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("客户端退出了。。。");
		}
		return null;
	}
	
	/**
	 * 向所有客户端发送消息
	 * @param msg
	 */
	public static void sendToAllClient(String msg) {
		// 遍历所有的已连接的客户端，将消息发送给所有的客户端
		for (Map.Entry<String, OnlineUser> user: StaticVariable.users.entrySet()) {
			try {
				System.out.println(msg);
				OnlineUser val = user.getValue();
                PrintWriter pw =val.getPw();  
                pw.println(msg);  
                pw.flush();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
		}
	}
	
	/**
	 * 向客户端发送消息
	 * @param msg
	 */
	public void sendToClient(String msg) {
		System.out.println(msg);
        PrintWriter pw = null;
		try {
			pw = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		};
		if (pw != null) {
			pw.println(msg);  
			pw.flush();			
		}
	}
	
	public static String getIpAddress() {
		return ipAddress;
	}
	
}

/**
 * 当客户端有消息发送过来时，执行线程
 * @author wanli
 *
 */
class ListenningMessage implements Runnable {

	private String question;// 客户端提交的问题
	
	public ListenningMessage(String question) {
		this.question = question;
	}
	
	@Override
	public void run() {
		Display.getDefault().syncExec(new Runnable(){
			public void run() {
				// 在表格中添加一项
				TableItem item = new TableItem(StaticVariable.askQuestions, SWT.NONE);
				item.setImage(new Image(StaticVariable.parent.getDisplay(), "image/unanswer.png"));
				item.setText(question);
				item.setFont(new Font(StaticVariable.parent.getDisplay(), "Arial", 20, SWT.NONE));
				StaticVariable.unanswerMap.put(item, true);
				StaticVariable.askQuestion.setText(StaticVariable.unanswerMap.size() + " 提问");
				// 使用弹窗的方式提醒客户端有消息发送过来
				MessagePOP_UP window = new MessagePOP_UP();
				window.open();
			}	
		});
	}
	
}
