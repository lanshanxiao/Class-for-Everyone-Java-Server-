package com.wanli.swing.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fill_in_the_blanks")
public class FillInTheBlanks implements QuestionType {

//	private String no;							// 题目编号
	private String question;					// 题目
	private String type = "fill_in_the_blanks"; // 类型：填空题
	private List<String> answer;				// 答案
	
	public FillInTheBlanks() {
	}
	
	public FillInTheBlanks(String no, String question, List<String> answer) {
		super();
//		this.no = no;
		this.question = question;
		this.answer = answer;
	}
//	@XmlAttribute(name = "no")
//	public String getNo() {
//		return no;
//	}
//	public void setNo(String no) {
//		this.no = no;
//	}
	
	@XmlElement(name = "question")
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	@XmlElement(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement(name = "answer")
	public List<String> getAnswer() {
		return answer;
	}
	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "FillInTheBlanks [question=" + question + ", type=" + type + ", answer=" + answer.toString() + "]";
	}
	
	
}
