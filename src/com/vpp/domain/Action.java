package com.vpp.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Action
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String actionId;
	private String details;
	private Calendar requiredBy;
	private String owningUser;
	private boolean complete;

	// needed for JPA - ignore until then
	public Action() {}

	public Action(String details, Calendar requiredBy, String owningUser)
	{
		this.details = details;
		this.requiredBy = requiredBy;
		this.owningUser = owningUser;
		this.complete = false;
	}

	public Action(String actionId, String details, Calendar requiredBy, String owningUser, boolean complete)
	{
		this.actionId = actionId;
		this.details = details;
		this.requiredBy = requiredBy;
		this.owningUser = owningUser;
		this.complete = complete;
	}

	public boolean isOverdue()
	{
		Calendar dateNow = new java.util.GregorianCalendar();
		return dateNow.after(this.requiredBy);
	}

	public String toString()
	{
		return "Action for " + this.owningUser + ": " + this.details + ", required by " + this.requiredBy.getTime();
	}

	public void completeAction()
	{
		this.complete = true;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Calendar getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(Calendar requiredBy) {
		this.requiredBy = requiredBy;
	}

	public String getOwningUser() {
		return owningUser;
	}

	public void setOwningUser(String owningUser) {
		this.owningUser = owningUser;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}