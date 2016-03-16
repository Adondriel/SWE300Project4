import java.io.*;
import java.text.*;
import java.util.*;
/**
 * @author Benjamin Uleau
 *
 */
public class Hours {
	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException{
		ArrayList<Worker> workers=new ArrayList<>();		
		File f=new File("src/employees2.txt");
		FileReader output=new FileReader(f);
		BufferedReader br=new BufferedReader(output);	
		
		File f2=new File("src/commissions.txt");
		FileReader output2=new FileReader(f2);
		BufferedReader br2=new BufferedReader(output2);
		
		File f3=new File("src/hours.txt");
		FileReader output3=new FileReader(f3);
		BufferedReader br3=new BufferedReader(output3);
		
		String input=null;
		while((input=br.readLine())!=null){
			String[] t=input.split("\t");
			Worker w=new Worker(t[0], t[1], t[2], t[3], t[4], 
					t[5], t[6], t[7], t[8], t[9], t[10],t[11], 
					t[12], t[13], t[14]);
			workers.add(w);
		}
		
		input=null;
		while((input=br2.readLine())!=null){
			String id;
			String[] t=input.split("\t");
			for(Worker w : workers){
				if(t[0].equals(w.getID())){
					w.setCommissionEarned(Double.parseDouble(t[2]));
				}
			}
		}
		
		input=null;
		boolean found=false;
		String currentID="000";
		while((input=br3.readLine())!=null){
			String[] t=input.split("\t");
			for(Worker w : workers){
				if(t[0].equals(w.getID())){
					found=true;
					currentID=w.getID();
					w.addToList(t[1]);
					w.addToList(t[2]);
				}
				else{
					found=false;
				}
			}
			if(!found){
				for(Worker w : workers){
					if(w.getID().equals(currentID)){
					w.addToList(t[1]);
					w.addToList(t[2]);
					}
				}
			}
		}
		

		String d1=args[0];
		String[] d1Array=d1.split("/");
		String d2=args[1];
		String[] d2Array=d2.split("/");
		String actualD1=formatDate(d1Array);
		String actualD2=formatDate(d2Array);

		for(Worker w : workers){
			w.getPayForDate(actualD2);
		}
		sort(workers);
	}
	
	private static String formatDate(String[] dateSplit) {
		String month;
		if(dateSplit[0].equalsIgnoreCase("01") || dateSplit[0].equalsIgnoreCase("1")){month="Jan";}
		else if(dateSplit[0].equalsIgnoreCase("02") || dateSplit[0].equalsIgnoreCase("2")){month="Feb";}
		else if(dateSplit[0].equalsIgnoreCase("03") || dateSplit[0].equalsIgnoreCase("3")){month="Mar";}
		else if(dateSplit[0].equalsIgnoreCase("04") || dateSplit[0].equalsIgnoreCase("4")){month="April";}
		else if(dateSplit[0].equalsIgnoreCase("05") || dateSplit[0].equalsIgnoreCase("5")){month="May";}
		else if(dateSplit[0].equalsIgnoreCase("06") || dateSplit[0].equalsIgnoreCase("6")){month="June";}
		else if(dateSplit[0].equalsIgnoreCase("07") || dateSplit[0].equalsIgnoreCase("7")){month="July";}
		else if(dateSplit[0].equalsIgnoreCase("08") || dateSplit[0].equalsIgnoreCase("8")){month="Aug";}
		else if(dateSplit[0].equalsIgnoreCase("09") || dateSplit[0].equalsIgnoreCase("9")){month="Sep";}
		else if(dateSplit[0].equalsIgnoreCase("10")){month="Oct";}
		else if(dateSplit[0].equalsIgnoreCase("11")){month="Nov";}
		else if(dateSplit[0].equalsIgnoreCase("12")){month="Dec";}
		else{return "Invalid month";}
		return dateSplit[1]+"-"+month;
	}

	/**
	 * @param workers 
	 */
	public static void sort(ArrayList<Worker> workers){
		ArrayList<Worker> sortedWorkers=new ArrayList<>();
		Set<String> set = new HashSet<String>();
		String[] setArray=new String[1000];
		int k=0;
		for(Worker w : workers){
			set.add(w.getDept());
		}
		
		for(String s : set){
			setArray[k]=s;
			k++;
			for(Worker w : workers){
				if(w.getDept().equalsIgnoreCase(s)){
					sortedWorkers.add(w);
				}
			}
		}
		double departmentPay=0;
		DecimalFormat numberFormat1=new DecimalFormat("#.00");
		double d=sortedWorkers.get(0).calculatePayHours();
		String nF=numberFormat1.format(d);
		
		double d2=sortedWorkers.get(0).calculateTotalPay();
		String nF2=numberFormat1.format(d2);
		System.out.println(setArray[0]);
		
		System.out.printf("%-10s, %-20s $%-25s $%-30s $%-35s %n", sortedWorkers.get(0).getLName(), sortedWorkers.get(0).getFName(), nF, sortedWorkers.get(0).getCommissionEarned(), nF2);
		
		for(int i=1; i<sortedWorkers.size(); i++){
			departmentPay+=sortedWorkers.get(i-1).calculateTotalPay();
			if(!sortedWorkers.get(i).getDept().equals(sortedWorkers.get(i-1).getDept())){
				//System.out.println("\t\tTotal Payroll: "+ departmentPay);
				System.out.printf("%-10s %-21s TotalPayroll: $%-30s", "", "", departmentPay);
				departmentPay=0;
				System.out.println("\n"+sortedWorkers.get(i).getDept()+"\n");
			}
			
			DecimalFormat numberFormat = new DecimalFormat("#.00");
			double dbl=sortedWorkers.get(i).calculatePayHours();
			String newFormat=numberFormat.format(dbl);
			
			double dbl2=sortedWorkers.get(i).calculateTotalPay();
			String newFormat2=numberFormat.format(dbl2);
			System.out.printf("%-10s, %-20s $%-25s $%-30s $%-35s %n", sortedWorkers.get(i).getFName(), sortedWorkers.get(i).getLName(), newFormat, sortedWorkers.get(i).getCommissionEarned(), newFormat2);
		}		
	}
	
	public static void displayWorker(ArrayList<Worker> workers, int index){
		System.out.println("ID\t"+workers.get(index).getID());
		System.out.println("LNAME\t"+workers.get(index).getLName());
		System.out.println("FNAME\t"+workers.get(index).getFName());
		System.out.println("DEPT\t"+workers.get(index).getDept());
		System.out.println("TITLE\t"+workers.get(index).getTitle());
		System.out.println("SALARY\t"+workers.get(index).getSalary());
		System.out.println("PER\t"+workers.get(index).getPer());
		System.out.println("COMMISSION\t"+workers.get(index).getCommission());
		System.out.println("TAXID\t"+workers.get(index).getTaxID());
		System.out.println("STREET\t"+workers.get(index).getStreet());
		System.out.println("CITY\t"+workers.get(index).getCity());
		System.out.println("STATE\t"+workers.get(index).getState());
		System.out.println("ZIP\t"+workers.get(index).getZip());
		System.out.println("COUNTRY\t"+workers.get(index).getCountry());
		System.out.println("PHONE\t"+workers.get(index).getPhone());
		
	}
	
	public String getHours(String startDate, String endDate){
		
		return "";
	}
}
