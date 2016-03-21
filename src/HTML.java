//import junit.framework.Assert;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HTML {
	
	private HtmlPage loginPage, monthlyPeriodPage, hourSubmissionPage;
	private HtmlForm loginForm, monthlyForm;
	private HtmlTextInput loginNameField, clientPrimeField;
	private HtmlPasswordInput loginPassField;
	private HtmlSubmitInput loginSubmitBtn, monthlyFindBtn;
	private HtmlAnchor monthlyAnchor;
	private HtmlTable primeTable;
	// for testing
	private HtmlElement xPathTemp;
	private List<HtmlTableCell> cellList;
	private List<HtmlTableRow> rowList;

	public HTML() throws Exception{
		try(final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);){
			
			// gather login page info needed for user login and pw
			loginPage = webClient.getPage("https://apps.state.or.us/exprsWeb/");		
			loginForm = loginPage.getFormByName("LoginForm");
			loginNameField = loginForm.getInputByName("loginName");
			loginPassField = loginForm.getInputByName("password");
			loginSubmitBtn = loginForm.getInputByName("clientAction");
			
			// login
			loginNameField.setText("REDACTED"); 	// username for testing
			loginPassField.setText("REDACTED");		// password for testing
			monthlyPeriodPage = loginSubmitBtn.click();
			Thread.sleep(500);
			
			// get from default home page to monthly page
			monthlyAnchor = monthlyPeriodPage.getFirstByXPath("//*[@id=\"menuitem2\"]/a");
			monthlyPeriodPage = monthlyAnchor.click();
			Thread.sleep(500);
			
			// input client prime number and change date range if needed
			
			// start date and end date
			// //*[@id="startDate"]
			// //*[@id="endDate"]
			// logout xpath
			// //*[@id="menutop"]/a[5]
			
			// for testing
			//xPathList = monthlyPeriodPage.getByXPath("xpathstring");
			//System.out.println(xPathList.get(0).toString());
			
			clientPrimeField = monthlyPeriodPage.getFirstByXPath("//*[@id=\"content\"]/form/table/tbody[1]/tr[1]/td[2]/input");
			monthlyFindBtn = monthlyPeriodPage.getFirstByXPath("//*[@id=\"content\"]/form/p/input[1]");
			
			// input client prime and see if results come up
			// select appropriate position
			clientPrimeField.setText("OG300R7T");
			monthlyPeriodPage = monthlyFindBtn.click();
			Thread.sleep(500);
			
			primeTable = monthlyPeriodPage.getHtmlElementById("clientProvider");
			rowList = primeTable.getRows();
			for(int i = 0; i < rowList.size(); i++) {
				//System.out.print(i);
				System.out.println(i + " " + rowList.get(i).asText() );
				//System.out.print(" ");
			}
			
			cellList = primeTable.getRow(2).getCells();
			
			//for(int i = 9; i < cellList.size(); i += 9) {
			/**
			for(int i = 0; i < cellList.size(); i++) {
				//System.out.print(i);
				System.out.println(i + " " + cellList.get(i).asText() );
				//System.out.print(" ");
			}*/
			
			//System.out.println(primeTable.getRow(2).asText());
			//System.out.println(monthlyPeriodPage.getTitleText());
			//System.out.println(monthlyPeriodPage.asText());
			
			// xPathTemp = 
			//for( Object s : xPathList){
			//	System.out.println(s.toString());
			//}
			//*/
			
			webClient.close();
		}
		catch(Exception ex){
			System.out.println("Something went wrong.");
			System.out.println(ex);
		}
		
	}
	
	public static void main (String[] args) throws Exception{
		HTML html = new HTML();
		System.out.println("Main was executed");
		
	}
	
}
