package com.wanli.classforereryone.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanli.swing.entities.OnlineUser;
import com.wanli.utils.StaticVariable;

/**
 * �����Ƿ��пͻ�������
 * @author wanli
 *
 */
public class MyServer implements Runnable {

	//���屣������Socket��Map
	private ServerSocket ss = null;
	
	public MyServer() {
		try {
			// ����Ҫ���Ӹ÷�������Ӧ�õĶ˿ں�
			ss = new ServerSocket(30000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			// ���д������������һֱ�ȴ����˵�����
			Socket s = null;
			try {
				s = ss.accept();
System.out.println(s.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (s != null) {
				System.out.println("�ͻ������ӳɹ�������");
//				try {
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			//ÿ���ͻ������Ӻ�����һ��ServerThread�߳�Ϊ�ÿͻ��˷���
			try {
				new Thread(new ServerThread(s)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
