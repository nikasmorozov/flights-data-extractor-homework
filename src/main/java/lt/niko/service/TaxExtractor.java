package lt.niko.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TaxExtractor {

   public BigDecimal collectTaxes(String baseUrl, int i, int j) throws IOException {
       WebClient webClient = new WebClient();
       webClient.getOptions().setCssEnabled(false);
       webClient.getOptions().setThrowExceptionOnScriptError(false);

       HtmlPage htmlPage = webClient.getPage(baseUrl);

       List<HtmlElement> departResults = htmlPage
               .getByXPath("//div[@class='fly5-flights fly5-depart th']/*/div[contains(@class, 'fly5-result-')]");

       HtmlButton departSelectButton = (HtmlButton) departResults.get(i)
               .getByXPath("*//button[@class='btn btn-outline-danger select-flight']").get(0);

       List<HtmlElement> returnResults = htmlPage
               .getByXPath("//div[@class='fly5-flights fly5-return th']/*/div[contains(@class, 'fly5-result-')]");

       HtmlButton returnSelectButton = (HtmlButton) returnResults.get(j)
               .getByXPath("*//button[@class='btn btn-outline-danger select-flight']").get(0);

       departSelectButton.click();
       returnSelectButton.click();

       HtmlButton button = htmlPage.getFirstByXPath("//button[@id='continue-btn']");
       htmlPage =  button.click();

       List<HtmlElement> taxList = htmlPage.getByXPath("//*[contains(text(),'Tax')]");

       BigDecimal taxSum = new BigDecimal("0");

       for(HtmlElement tax : taxList) {
           taxSum = taxSum.add(BigDecimal.valueOf(Double.parseDouble(tax.asNormalizedText()
                   .substring(tax.asNormalizedText().lastIndexOf(")") + 1))));
       }
       webClient.close();
       return taxSum;
   }
}
