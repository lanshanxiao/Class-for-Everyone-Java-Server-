package com.wanli.classforereryone.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.Mmmm;
import com.wanli.swing.entities.OnlineUser;
import com.wanli.swing.frame.MessagePOP_UP;
import com.wanli.utils.StaticVariable;

public class ServerThread implements Runnable {

	// 定义当前线程所处理的Socket
	Socket s = null;
	// 该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	// 接收连接上服务端的客户端IP地址
	private static String ipAddress = "";
	// 存储学生提出的问题
	private String question;
	private 
	
	public ServerThread(Socket s) throws IOException {
		this.s = s;
		// 初始化该Socket对应的输入流
		br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
	}
	
	@Override
	public void run() {
		
		String content = null;
		// 采用循环不断从Socket中读取客户端发送过来的数据
		while ((content = readFromClient()) != null) {
			
			String[] info = content.split(",");
			switch(info[0]) {
				// 读取注册信息
				case "1":
					
					break;
				// 读取登录信息
				case "2":
					break;
				// 读取回答问题的信息
				case "3":
					break;
				// 读取提问的信息
				case "4":
					break;
			}
			StaticVariable.users.get(s.getInetAddress().toString().substring(1)).setInetAddress(s.getInetAddress().toString().substring(1));
			StaticVariable.users.get(s.getInetAddress().toString().substring(1)).setUsername(info[0]);
			StaticVariable.users.get(s.getInetAddress().toString().substring(1)).setImei(info[1]);
			ipAddress = s.getInetAddress().toString().substring(1);
			// 若发送的消息是客户端发送的提问信息，则弹出提示框
			if (info.length == 3) {
				question = info[0] + ":::" + info[2];				
				new Thread(new ListenningMessage(question)).start();
				StaticVariable.users.get(s.getInetAddress().toString().substring(1)).setContent(info[2]);
				System.out.println(info[0] + "," + info[1] + "," + info[2]);				
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
			e.printStackTrace();
		}
		return null;
	}
	
	// 向客户端发送消息
	public static void sendToClient(String msg) {
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
				StaticVariable.askQuestion.setText(StaticVariable.unanswerNum + " 提问");
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
