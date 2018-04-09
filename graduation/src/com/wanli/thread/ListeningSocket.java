package com.wanli.thread;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

import com.wanli.classforereryone.server.ServerThread;
import com.wanli.utils.StaticVariable;

public class ListeningSocket implements Runnable  {

	private int socketNum = 0;
	
	public ListeningSocket() {
		socketNum = StaticVariable.users.size();
	}
	
	@Override
	public void run() {
		while (true) {
			if (StaticVariable.rooms.size() > 0 && StaticVariable.users.size() > socketNum) {
				if (ServerThread.getIpAddress() != "") {
					socketNum = StaticVariable.users.size();
					StaticVariable.onlineNumsInt++;
					Display.getDefault().syncExec(new Runnable(){
						public void run() {
							TreeItem treeItem = new TreeItem(StaticVariable.rooms.get(0), SWT.NONE);
							treeItem.setText(ServerThread.getIpAddress());
							StaticVariable.onlineUsers.put(ServerThread.getIpAddress(), treeItem);
							StaticVariable.onlining.setText(StaticVariable.onlineNumsStr + StaticVariable.onlineNumsInt);
						}
						
					});
				}
			} else {
				Display.getDefault().syncExec(new Runnable(){
					public void run() {
						if (StaticVariable.onlineUsers != null && StaticVariable.onlineUsers.size() > 0) {
							if (StaticVariable.quitSocket != "") {
								StaticVariable.onlineNumsInt--;
								socketNum = StaticVariable.users.size();
								StaticVariable.onlineUsers.get(StaticVariable.quitSocket).dispose();
								StaticVariable.quitSocket = "";
								StaticVariable.onlining.setText(StaticVariable.onlineNumsStr + StaticVariable.onlineNumsInt);
							}
						}
					}
				});
			}
			
//			System.out.println(MainFrame.rooms.size());
//			System.out.println(MyServer.users.size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
