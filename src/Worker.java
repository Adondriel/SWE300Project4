import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Benjamin Uleau
 *
 */
public class Worker{
	private String id;
	private String lName;
	private String fName;
	private String dept;
	private String title;
	private String salary;
	private String per;
	//private double commission;
	private String commission;
	private String taxID;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	
	private double hours=0;
	private double commissionEarned;
	private double totalSales;
	ArrayList<String> workList=new ArrayList<>();
	@SuppressWarnings("javadoc")
	public Worker(String id, String lName, String fName, String dept, String title, String salary, String per, String commission, String taxID, String street, String city, String state, String zip, String country, String phone){
		this.id=id;
		this.lName=lName;
		this.fName=fName;
		this.dept=dept;
		this.title=title;
		this.salary=salary;
		this.per=per;
		this.commission = (commission.equals("")) ? "0.0" : commission;
		//hours=0;
		this.taxID=taxID;
		this.street=street;
		this.city=city;
		this.state=state;
		this.zip=zip;
		this.country=country;
		this.phone=phone;
	}
	
	String getID(){return id;}	
	String getLName(){return lName;}
	String getFName(){return fName;}
	String getDept(){return dept;}
	String getTitle(){return title;}
	String getSalary(){return salary;}
	String getPer(){return per;}
	String getCommission(){return commission;}
	String getTaxID(){return taxID;}
	String getStreet(){return street;}
	String getCity(){return city;}
	String getState(){return state;}
	String getZip(){return zip;}
	String getCountry(){return country;}
	String getPhone(){return phone;}
	
	void setHours(double hoursWorked){hours=hoursWorked;}
	double getHours(){
		return hours;
	}
	
	void setCommissionEarned(double sCommission){commissionEarned=sCommission;};
	double getCommissionEarned(){return commissionEarned;}
	
	double calculatePayHours(){
		try{
		double actualSalary=Double.parseDouble(salary);
		if(hours<=40){
			return hours*actualSalary;
		}
		double timeAndAHalf=actualSalary*1.5;
		return timeAndAHalf*hours;
		}catch(NumberFormatException e){
			return 0;
		}
	}

	double calculateTotalPay(){
		return calculatePayHours()+getCommissionEarned();
	}
	public void addToList(String s){
		workList.add(s);
	}
	public void getPayForDate(String s){
		boolean foundCorrectDate=false;
		int backtrackCounter=0;
		for(int i=0; i<workList.size(); i++){
			if(workList.get(i).equalsIgnoreCase(s)){
				setHours(Double.parseDouble(workList.get(i+1)));
				foundCorrectDate=true;
			}
		}
		while(foundCorrectDate=false){
			String[] t=s.split("-");
			int dateInt=Integer.parseInt(t[0]);
			dateInt--;
			String backToString=Integer.toString(dateInt);
			String newDate=backToString+"-"+t[1];
			
			for(int i=0; i<workList.size(); i++){
				if(workList.get(i).equalsIgnoreCase(newDate)){
					setHours(Double.parseDouble(workList.get(i+1)));
					foundCorrectDate=true;
				}
			}
			if(backtrackCounter>6){
				System.out.println("No employee data found for specified date.");
				break;
			}
		}
	}
}