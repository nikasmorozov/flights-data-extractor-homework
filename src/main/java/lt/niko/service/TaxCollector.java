package lt.niko.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class TaxCollector {

   public void collectTaxes(String baseUrl) throws IOException {
       WebClient webClient = new WebClient();
       webClient.getOptions().setCssEnabled(false);
       webClient.getOptions().setThrowExceptionOnScriptError(false);

       HtmlPage htmlPage = webClient.getPage(baseUrl);

       List<HtmlElement> elements = htmlPage.getByXPath("//div[@class='fly5-flights fly5-depart th']" +
               "//button[@class='btn btn-outline-danger select-flight' and @data-cabin-class='0']");

       System.out.println(elements.size());

       HtmlButton selectButton1 = (HtmlButton) htmlPage.getByXPath("//div[@class='fly5-flights fly5-depart th']" +
               "//button[@class='btn btn-outline-danger select-flight']").get(0);
       HtmlButton selectButton2 = (HtmlButton) htmlPage.getByXPath("//div[@class='fly5-flights fly5-return th']" +
               "//button[@class='btn btn-outline-danger select-flight']").get(0);
       selectButton1.click();
       selectButton2.click();

       HtmlButton button = htmlPage.getFirstByXPath("//button[@id='continue-btn']");
       htmlPage =  button.click();

       HtmlElement taxes = htmlPage.getFirstByXPath("//*[contains(text(),'Tax')]");

       System.out.println(taxes.asNormalizedText());
   }
}
