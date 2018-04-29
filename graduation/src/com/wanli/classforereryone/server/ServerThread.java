package com.wanli.classforereryone.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import com.wanli.swing.Mmmm;
import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.OnlineUser;
import com.wanli.swing.frame.MessagePOP_UP;
import com.wanli.swing.service.DBService;
import com.wanli.swing.service.DBServiceUser;
import com.wanli.utils.StaticVariable;

/**
 * 每个客户的专属线程，可以接收客户端发送的消息，也可以向客户端发送消息
 * @author wanli
 *
 */
public class ServerThread implements Runnable {

	// 定义当前线程所处理的Socket
	private static Socket s = null;
	// 该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	// 接收连接上服务端的客户端IP地址
	private static String ipAddress = "";
	// 存储学生提出的问题
	private String question;
	// 存储所有教室
	private StringBuilder allClass = new StringBuilder();
	// 操作用户数据库
	private DBServiceUser dbServiceUser;
	// 操作成绩表数据库
	private DBService dbService;
	
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
		ipAddress = s.getInetAddress().toString().substring(1);
		// 采用循环不断从Socket中读取客户端发送过来的数据
		while ((content = readFromClient()) != null) {
<<<<<<< HEAD
			System.out.println(content);
=======
			
>>>>>>> fa5ef85e9666c0c35e4117db1ac219b0328c353c
			String[] info = content.split(",");
//			System.out.println(Arrays.toString(info));
			switch(info[0]) {
				// 读取注册信息
				// 客户端发送过来的字符串格式：操作编号,手机号,昵称,密码,邮箱
				case "1":
					if (dbServiceUser.addUser(info)) {
						// 注册成功
						sendToClient("1");
					} else {
						// 注册失败
						sendToClient("1-false");
					}
					break;
				// 读取登录信息
				// 客户端发送过来的字符串格式：操作编号,手机或邮箱,密码
				case "2":
//					System.out.println(Arrays.toString(info));
					if (dbServiceUser.getUserByNameAndPassword(info[1], info[2])) {
						// 有客户端连接就把连接的客户端使用map存储
						try {
							System.out.println("客户端连接成功！！！");
							StaticVariable.users.put(s.getInetAddress().toString().substring(1), new OnlineUser(s));
							StaticVariable.answers.put(info[1], "");
						} catch (IOException e) {
							System.out.println("登录失败...");
							e.printStackTrace();
						}
						
						// 登录成功
						sendToClient("2");
//						System.out.println("登录了。。。。。");
						StaticVariable.users.get(ipAddress).setInetAddress(s.getInetAddress().toString().substring(1));
						StaticVariable.users.get(ipAddress).setUsername(info[1]);
System.out.println("....2");
					} else {
						// 登录失败
						sendToClient("2-false");
					}
					break;
				// 读取回答问题的信息
				// 客户端发送过来的字符串格式：操作编号,昵称,内容
				case "3":
					Display.getDefault().syncExec(new Runnable() {
						
						@Override
						public void run() {
							// StaticVariable.questionSelect.getSelectionIndex() > 0表示已经选择题目，可以进行答题
							if (StaticVariable.questionSelect.getSelectionIndex() > 0) {
								String[] answers = info[2].split(" ");
								// 保存用户回答的答案
								List<String> answerList = new ArrayList<>();
								StaticVariable.answers.put(info[1], "");
								StaticVariable.answers.replace(info[1], info[2]);
								int index = StaticVariable.questionSelect.getSelectionIndex();
								String[] strs = StaticVariable.questionsMap.get(Integer.toString(index)).split(",");						
								// answers.length > 1 表示有多个答案
								if (answers.length > 1) {
									// 多个答案用空隔分开，过滤掉多余的空隔
									for (int i = 0; i < answers.length; i++) {
										if (!answers[i].equals("")) {
											answerList.add(answers[i]);
										}
									}
									// 初始化存储正确答案个数，错误答案个数，未回答个数的list
									for (int i = 3; i < strs.length; i++) {
										StaticVariable.correct.add(new Integer(0));
										StaticVariable.error.add(new Integer(0));
										StaticVariable.unResponse.add(new Integer(0));					
									}
									// 分别计算回答正确和回答错误的个数
									for (int i = 3; i < strs.length; i++) {
										// 判断正确与否
										if (strs[i].equals(answerList.get(i - 3))) {
											// 回答正确
											int value = StaticVariable.correct.get(i - 3).intValue();
											value++;
											StaticVariable.correct.set(i - 3, new Integer(value));
										} else {
											// 回答错误
											int value = StaticVariable.error.get(i - 3).intValue();
											value++;
											StaticVariable.error.set(i - 3, new Integer(value));
										}
									}
								} else {
									// 初始化存储正确答案个数，错误答案个数，未回答个数的list
									StaticVariable.correct.add(new Integer(0));
									StaticVariable.error.add(new Integer(0));
									StaticVariable.unResponse.add(new Integer(0));
									// 只有一个答案
									answerList.add(info[2]);
									// 判断正确与否
									if (answerList.get(0).equals(strs[3])) {
										// 回答正确
										int value = StaticVariable.correct.get(0).intValue();
										value++;
										StaticVariable.correct.set(0, new Integer(value));
									} else {
										// 回答错误
										int value = StaticVariable.error.get(0).intValue();
										value++;
										StaticVariable.error.set(0, new Integer(value));
									}
								}
								System.out.println(StaticVariable.answers);
								System.out.println(answerList);
								System.out.println(StaticVariable.correct);
								System.out.println(StaticVariable.error);
							}
						}
					});
//					System.out.println(info[2]);
					break;
				// 读取提问的信息，并且弹框提示有学生提问
				// 客户端发送过来的字符串格式：操作编号,昵称,内容
				case "4":
					// question为最终显示的提问的内容
					question = info[1] + ":::" + info[2]; 
					new Thread(new ListenningMessage(question)).start();
					StaticVariable.users.get(ipAddress).setContent(info[2]);
					System.out.println(info[0] + "," + info[1] + "," + info[2]);				
					break;
				// 忘记密码，重置密码
				// 客户端发送过来的字符串格式：操作编号,手机或邮箱,密码
				case "5":
					// 若密码为null，则表示是查询手机号或邮箱是否已经注册过
					if (info[2].equals("null")) {
						if (dbServiceUser.getByUsername(info[1])) {
							// 注册过给客户端发送成功提示
							sendToClient("5");
						} else {
							// 未注册过给客户端发送失败提示
							sendToClient("5-false");
						}
					} else {
						// 若密码不是null，则表示是修改密码
						dbServiceUser.updatePassword(info[1], info[2]);
						sendToClient("5");
					}
					break;
				// 向客户端发送教室信息
				case "6":
					if (StaticVariable.rooms.size() > 0) {
						allClass.setLength(0);
						for (int i = 0; i < StaticVariable.rooms.size(); i++) {
							if (i == 0) {
								allClass.append(StaticVariable.rooms.get(i).getText());									
							} else {
								allClass.append("," + StaticVariable.rooms.get(i).getText());
							}
						}							
					}
					// 向客户端发送教室信息
					sendToClient(allClass.toString());
					break;
				// 获取题目
				case "7":
					int index = StaticVariable.questionSelect.getSelectionIndex();
					if (index > 0) {
						sendToClient(StaticVariable.questionsMap.get(Integer.toString(index)));
					}
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
	
//	public static void sendToClient(ChoiceQuestion choice) {
//		String no = "1";
//		String question = "1111";
//		List<String> options = new ArrayList<>();
//		options.add("A.1");
//		options.add("B.2");
//		options.add("C.3");
//		options.add("D.4");
//		String answer = "A";
//		choice = new ChoiceQuestion(no, question, answer, options);
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter(s.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		};
//		if (pw != null) {
//			pw.println(choice);  
//			pw.flush();			
//		}
//	}
	
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
System.out.println("发送消息");
		}
	}
	
	// 获取连接的客户端的ip地址
	public static String getIpAddress() {
		return ipAddress;
	}
	
}

/**
 * 当客户端有消息发送过来时，执行线程，显示客户端的提问信息
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
