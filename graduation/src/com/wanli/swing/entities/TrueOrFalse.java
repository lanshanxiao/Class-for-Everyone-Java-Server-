package com.wanli.swing.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "true_or_false")
public class TrueOrFalse implements QuestionType {

//	private String no;							// 题目编号
	private String question;					// 题目
	private String type = "true_or_false"; 		// 类型：填空题
	private String answer;						// 答案
	
	public TrueOrFalse(String no, String question, String answer) {
		super();
//		this.no = no;
		this.question = question;
		this.answer = answer;
	}
	
	public TrueOrFalse() {
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "TrueOrFalse [question=" + question + ", type=" + type + ", answer=" + answer + "]";
	}
	
	
	
}
