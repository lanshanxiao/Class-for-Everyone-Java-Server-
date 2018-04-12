package com.wanli.swing.service;

import java.util.List;

import com.wanli.swing.dao.DBDao;

public class DBService {

	private DBDao dbDao = null;
	
	public DBService() {
		dbDao = new DBDao();
	}
	
	/**
	 * 创建表
	 * @param num：指定列数
	 * @param tableName：指定表名
	 */
	public void createTable(int num, String tableName) {
		dbDao.createTable(num, tableName);
	}
	
	/**
	 * 获取成绩数据
	 * @param tableName：表名
	 * @return 返回获取的成绩数据
	 */
	public List<String[]> getScoreData(String tableName) {
		return dbDao.getScoreData(tableName);
	}
	
	/**
	 * 获取表的总列数
	 * @param tableName：表名
	 * @return 列数
	 */
	public int getTableColumn(String tableName) {
		return dbDao.getTableColumn(tableName);
	}
	
	/**
	 * 获取graduation_scoretab数据库的所有表名
	 * @return:返回表名的list
	 */
	public List<String> getTableList() {
		return dbDao.getTableList();
	}
	
	/**
	 * 向表中添加记录
	 * @param userName：用户名
	 * @param tableName：表名
	 */
	public void addRecord(String userName, String tableName, String[] answers) {
		dbDao.addRecord(userName, tableName, answers);
	}
}
