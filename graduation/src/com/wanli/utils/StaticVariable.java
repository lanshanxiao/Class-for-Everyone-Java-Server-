package com.wanli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import com.wanli.swing.entities.ChoiceQuestion;
import com.wanli.swing.entities.FillInTheBlanks;
import com.wanli.swing.entities.OnlineUser;
import com.wanli.swing.entities.QuestionType;
import com.wanli.swing.entities.TrueOrFalse;

/**
 * �洢���о�̬����
 * @author wanli
 *
 */
public class StaticVariable {
	
	public static Composite parent;										// �������Composite��
	public static Map<String, OnlineUser> users = new HashMap<>();		// ��������Socket��Map
	public static String quitSocket = "";								// ��¼�Ͽ����ӵ�socket�������Ƴ�TreeItem
	public static String onlineNumsStr = "����������";						// ��ʾ��������
	public static int onlineNumsInt = 0;								// ͳ����������
	public static Label onlining;										// ��ʾ����������Label
	public static StyledText text;										// ��Ŀ�ı���ʾ
	public static StyledText answer;									// ���ı���ʾ
	public static Button refresh;										// ˢ�³ɼ���ť
	public static Button scoreChartBtn;									// ��ͼ�����ʽ��ʾ��ǰ�ɼ�����
	public static Button historyCharBtn;								// ��ͼ�����ʽ��ʾ��ʷ�ɼ�����
	public static Table scoreTab;										// ��ʾ�ɼ����
	public static Table historyTab;										// ��ʾ��ʷ�ɼ����
	public static Table askQuestions;									// ��ʾѧ�����������
	public static Combo historyCombo;									// ������ʷ����������
	public static String[] questions;									// ������������
	public static int index = 1;										// ��ǵڼ���
	public static ArrayList<TreeItem> rooms = new ArrayList<>();		// �洢���������н���
	public static String instruction;									// ���������ͻ��˷��͵�ָ��
	public static String tableName;										// ���������ڲ�ѯ���ݿ��еı�����
	public static String className;										// ��¼�����Ľ��ҵ�����
	public static Map<String, TreeItem> onlineUsers = new HashMap<>();	// ��ʾ��������������Tree
	public static TabItem askQuestion;									// ����ѡ�
	public static Map<TableItem, Boolean> unanswerMap = new HashMap<>();// �洢ѧ��������Ϊ�ش������ 
	public static Composite questionCom;								// ��ǰ���ⴰ�ڵ������
	public static String questionType;									// ��Ŀ����
	public static Button nextOption;									// ��һ��
	public static List<ChoiceQuestion> choiceList = new ArrayList<>();	// �洢ѡ����
	public static List<TrueOrFalse> trueOrFalseList = new ArrayList<>();// �洢�Ƿ���
	public static List<FillInTheBlanks> fillblanksList = new ArrayList<>();// �洢�����
	public static Map<String, Text> choiceAllText = new HashMap<>();	// �洢ѡ��������ϵ�����Text���
	public static Map<String, Text> trueOrFalseAllText = new HashMap<>();// �洢�Ƿ�������ϵ�����Text���
	public static Map<String, Text> fillblanksAllText = new HashMap<>();// �洢���������ϵ�����Text���
	public static int creQuesIndex = 0;									// �洢������Ŀʱ���±꣬��Ǵ����˶�����Ŀ
	public static boolean firstOpenPrepareLessonsShell = true;			// ����Ƿ�������������һ�δ򿪱��δ���
//	public static Map<String, String> questionsMap = new HashMap<>();	// �洢���е�����
	public static List<String> questionsList = new ArrayList<>();		// �洢���е�����
	public static Combo questionSelect;									// ѡ�������������
	public static StyleRange style, range;								// ���
	public static Color color;											// �����ı���ɫ
	public static List<Integer> correct = new ArrayList<>();			// �洢������ȷ�ĸ���
	public static List<Integer> error = new ArrayList<>();				// �洢���д���𰸵ĸ���
	public static List<Integer> unResponse = new ArrayList<>();			// �洢����δ����ĸ���
	public static Map<String, String> answers = new HashMap<>();		// �洢ÿһ��ѧ���ش�����д�
	public static boolean firstInsert = true;							// ���ɼ����в������ݣ���������Ƿ��ǵ�һ�β���
	public static List<String> statisticalData;							// �洢����ͳ������
	public static Table table;											// ��������ı��
	public static List<QuestionType> allQuestionList = new ArrayList<>();				// �洢�������͵�list
}
