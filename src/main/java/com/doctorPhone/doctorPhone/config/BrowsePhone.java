package com.doctorPhone.doctorPhone.config;

import com.doctorPhone.doctorPhone.common.Phone_Details;
import com.doctorPhone.doctorPhone.repo.Phone_Repo;
import com.doctorPhone.doctorPhone.service.Phone_Service;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BrowsePhone implements InitializingBean {
    private FirefoxDriver driver = null;
    private String url[] = {"https://www.betium.it/Holder.aspx?page=bet"};
    private String codes[] = {"CALCIO"};
    private HashMap<String, String> handlers = new HashMap<>();

    @Autowired
    private Phone_Service phoneService;
    @Autowired
    private Phone_Repo phoneRepo;

    public void initialise() throws Exception {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);

        driver = new FirefoxDriver();

        for (int i = 0; i < url.length - 1; i++) {
            driver.executeScript("window.open()");
        }

        ArrayList<String> windowsHandles = new ArrayList<>(driver.getWindowHandles());

        for (int i = 0; i < url.length; i++) {
            handlers.put(codes[i], windowsHandles.get(i));
        }

        scrape("https://www.doctorphone.nl");
    }

    public void scrape(String link) throws InterruptedException, IOException {
        driver.get(link);
        Thread.sleep(3000);
        WebElement navibar = driver.findElementByXPath("/html/body/div[3]/div/ul/li[1]/a");
        navibar.click();
        Thread.sleep(2000);
        WebElement allPhoneBrand = driver.findElementByXPath("/html/body/div[5]/div/div/div[2]/ul");
        List<WebElement> brandName = allPhoneBrand.findElements(By.xpath("./*"));
        List<String> phones = new ArrayList<>();
        Phone_Details details = null;
        for (WebElement element : brandName) {
            phones.add(element.findElement(By.tagName("a")).getAttribute("href"));
        }

        for (String phone : phones) {
            driver.get(phone);
            List<String> list = new ArrayList<>();

            for (WebElement tabs : driver.findElementByXPath("//*[@id=\"type_container\"]").findElements(By.xpath("./*"))) {
                WebElement allPhones = tabs.findElement(By.tagName("ul"));

                for (WebElement element : allPhones.findElements(By.xpath("./*"))) {
                    WebElement element1 = element.findElement(By.tagName("a"));
                    list.add(element1.getAttribute("href"));
                    System.out.println("collected link " + element1.getAttribute("href"));
                }
            }
            for (String ss : list) {
                driver.get(ss);
                System.out.println("current link ===========" + ss + "================");

                Thread.sleep(2000);
                WebElement toestelmerk = driver.findElementByXPath("//*[@id=\"brandChozen\"]");
                WebElement pModel = driver.findElementByXPath("//*[@id=\"phoneTypeTitle\"]");
                String toestelmerkText = toestelmerk.getAttribute("innerText");
                String modelText = pModel.getAttribute("innerText");
                System.out.println("Toestelmerk =====================" + toestelmerkText);
                System.out.println("Model ==============" + modelText);
                WebElement phDetails = driver.findElementByXPath("//*[@id=\"reparatie_lijst\"]");

                for (WebElement allElement : phDetails.findElements(By.xpath("./*"))) {
                    details = new Phone_Details();
                    try {
                        JavascriptExecutor jse = (JavascriptExecutor) driver;
                        WebElement element2 = allElement.findElement(By.tagName("a"));
                        String innerText = element2.getAttribute("innerText");
                        String itemName = innerText.split("€")[0];
                        String price = allElement.findElement(By.tagName("a")).findElement(By.tagName("span")).getAttribute("innerText");
                        System.out.println("Title =============" + itemName);
                        jse.executeScript("arguments[0].scrollIntoView(true);", element2);
                        jse.executeScript("arguments[0].click();", element2);
//                        element2.click();
                        Thread.sleep(2000);
                        String tochDetails = "";
                        String tochTijdsduur = "";
                        String tochGarantie = "";

                        String ti = "";
                        String ga = "";
                        try {
                            tochDetails = allElement.findElements(By.xpath("./*")).get(2).findElement(By.tagName("p")).getAttribute("innerText");
                            tochTijdsduur = allElement.findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                            try {
                                tochGarantie = allElement.findElements(By.xpath("./*")).get(2).findElements(By.xpath("./*")).get(2).getAttribute("innerText");
                            } catch (IndexOutOfBoundsException e) {

                            }
                            try {
                                ti = tochTijdsduur.split(":")[1];
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                            try {
                                ga = tochGarantie.split(":")[1];
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }


                        } catch (NoSuchElementException e) {
                            try {
                                tochDetails = allElement.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(0).getAttribute("innerText");
                                tochTijdsduur = allElement.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(1).getAttribute("innerText");
                                try {
                                    tochGarantie = allElement.findElement(By.tagName("div")).findElements(By.xpath("./*")).get(2).getAttribute("innerText");
                                } catch (IndexOutOfBoundsException e1) {
                                    System.err.println("tochGarantie not found");
                                }
                                ti = tochTijdsduur;
                                ga = tochGarantie;

                            } catch (NoSuchElementException v) {
                                v.printStackTrace();
                                System.out.println("exception in line 121");
                            }
                        }

                        System.err.println("p tag values ==============" + tochDetails);
                        System.out.println("Tijdsduur tag values ================" + ti);
                        System.out.println("Garantie tag values ==================" + ga);

                        details.setCurrentLink(ss);
                        details.setToestelmerk(toestelmerkText);
                        details.setModel(modelText);
                        details.setTochuscreen(itemName);
                        details.setTochDetails(tochDetails);
                        details.setTildsduur(ti);
                        details.setGarantie(ga);
                        details.setPrice(price);
//                        Thread.sleep(1000);
                        phoneRepo.save(details);

                    } catch (Exception e) {
//                        e.printStackTrace();
                        continue;
                    }
                }
            }
            System.out.println("CHANGED===");
            continue;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initialise();
    }
}

