package com.wanli.swing.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Questions")
public class Question {

	private List<ChoiceQuestion> choiceList;
	private List<TrueOrFalse> trueOrFalseList;
	private List<FillInTheBlanks> fillBlanksList;
	
	@XmlElement(name = "ChoiceQuestions")
	public List<ChoiceQuestion> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(List<ChoiceQuestion> choiceList) {
		this.choiceList = choiceList;
	}
	
	@XmlElement(name = "TrueOrFalseQuestions")
	public List<TrueOrFalse> getTrueOrFalseList() {
		return trueOrFalseList;
	}
	public void setTrueOrFalseList(List<TrueOrFalse> trueOrFalseList) {
		this.trueOrFalseList = trueOrFalseList;
	}
	
	@XmlElement(name = "FillBlanksQuestions")
	public List<FillInTheBlanks> getFillBlanksList() {
		return fillBlanksList;
	}
	public void setFillBlanksList(List<FillInTheBlanks> fillBlanksList) {
		this.fillBlanksList = fillBlanksList;
	}
	
}
