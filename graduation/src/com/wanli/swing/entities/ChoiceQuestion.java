package com.wanli.swing.entities;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "choice_question")
public class ChoiceQuestion implements Serializable, QuestionType {

//	private String no;				// ��Ŀ���
	private String question;		// ��Ŀ
	private String type = "choice"; // ���ͣ�ѡ����
	private List<String> options;	// ѡ��
	private String answer;			// ��
	
	public ChoiceQuestion(String no, String question, String answer, List<String> options) {
//		this.no = no;
		this.question = question;
		this.answer = answer;
		this.options = options;
	}
	
	public ChoiceQuestion() {
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
	
	@XmlElementWrapper(name = "options")
	@XmlElement(name = "option")
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
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
		return "ChoiceQuestion [question=" + question + ", type=" + type + ", options=" + options.toString()
				+ ", answer=" + answer + "]";
	}
	
	
}
