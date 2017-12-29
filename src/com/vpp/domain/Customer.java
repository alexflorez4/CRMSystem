package com.vpp.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer
{
	@Id
	private String customerId;

	private String companyName;
	private String email;
	private String telephone;
	private String notes;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Call> calls;

	// needed for JPA
	public Customer() {}

	public Customer(String customerId, String companyName, String email, String telephone, String notes)
	{
		this(customerId, companyName, notes);
		this.email = email;
		this.telephone = telephone;
	}

	public Customer(String customerId, String companyName, String notes)
	{
		this.customerId = customerId;
		this.companyName = companyName;
		this.notes = notes;
		this.calls = new ArrayList<Call>();
	}

	public void addCall(Call callDetails)
	{
		this.calls.add(callDetails);
	}

	public String toString()
	{
		return this.customerId + ": " + this.companyName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Call> getCalls() {
		return calls;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}
}

