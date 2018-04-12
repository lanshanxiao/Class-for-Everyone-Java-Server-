package com.wanli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.wanli.swing.entities.OnlineUser;

public class StaticVariable {
	
	public static Composite parent;										// 主窗体的Composite类
	public static Map<String, OnlineUser> users = new HashMap<>();		// 保存所有Socket的Map
	public static String quitSocket = "";								// 记录断开连接的socket，用来移除TreeItem
	public static String onlineNumsStr = "在线人数：";						// 显示在线人数
	public static int onlineNumsInt = 0;								// 统计在线人数
	public static Label onlining;										// 显示在线人数的Label
	public static StyledText text;										// 题目文本显示
	public static Button refresh;										// 刷新成绩表按钮
	public static Button scoreChartBtn;									// 以图表的形式显示当前成绩数据
	public static Button historyCharBtn;								// 以图表的形式显示历史成绩数据
	public static Table scoreTab;										// 显示成绩表格
	public static Table historyTab;										// 显示历史成绩表格
	public static Table askQuestions;									// 显示学生提出的问题
	public static Combo historyCombo;									// 所有历史表格的下拉框
	public static String[] questions;									// 保存所有问题
	public static int index = 1;										// 标记第几题
	public static ArrayList<TreeItem> rooms = new ArrayList<>();		// 存储创建的所有教室
	public static String instruction;									// 服务器给客户端发送的指令
	public static String tableName;										// 表名，用于查询数据库中的表数据
	public static String className;										// 记录创建的教室的名称
	public static Map<String, TreeItem> onlineUsers = new HashMap<>();	// 显示所有在线人数的Tree
	public static TabItem askQuestion;									// 提问选项卡
	public static Map<TableItem, Boolean> unanswerMap = new HashMap<>();// 存储学生提问中为回答的问题 
}
