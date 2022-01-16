package lt.niko.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TaxCollector {

   public BigDecimal collectTaxes(String baseUrl, int i, int j) throws IOException {
       WebClient webClient = new WebClient();
       webClient.getOptions().setCssEnabled(false);
       webClient.getOptions().setThrowExceptionOnScriptError(false);

       HtmlPage htmlPage = webClient.getPage(baseUrl);

       List<HtmlElement> elements = htmlPage.getByXPath("//div[@class='fly5-flights fly5-depart th']" +
               "//button[@class='btn btn-outline-danger select-flight' and @data-cabin-class='0']");

       System.out.println(elements.size());

       HtmlButton selectButton1 = (HtmlButton) htmlPage.getByXPath("//div[@class='fly5-flights fly5-depart th']" +
               "//button[@class='btn btn-outline-danger select-flight']").get(i);
       HtmlButton selectButton2 = (HtmlButton) htmlPage.getByXPath("//div[@class='fly5-flights fly5-return th']" +
               "//button[@class='btn btn-outline-danger select-flight']").get(j);
       selectButton1.click();
       selectButton2.click();

       HtmlButton button = htmlPage.getFirstByXPath("//button[@id='continue-btn']");
       htmlPage =  button.click();

       List<HtmlElement> taxList = htmlPage.getByXPath("//*[contains(text(),'Tax')]");

       BigDecimal taxSum = new BigDecimal("0");

       for(HtmlElement tax : taxList) {
           taxSum = taxSum.add(BigDecimal.valueOf(Double.parseDouble(tax.asNormalizedText()
                   .substring(tax.asNormalizedText().lastIndexOf(")") + 1))));
       }

       return taxSum;

   }
}
