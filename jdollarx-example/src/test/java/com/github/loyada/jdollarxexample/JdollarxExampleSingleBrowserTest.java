package com.github.loyada.jdollarxexample;

import com.github.loyada.jdollarx.Operations;
import com.github.loyada.jdollarx.Path;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.loyada.jdollarx.BasicPath.*;
import com.github.loyada.jdollarx.singlebrowser.InBrowserSinglton;
import static com.github.loyada.jdollarx.singlebrowser.InBrowserSinglton.*;

import static com.github.loyada.jdollarx.ElementProperties.*;
import static com.github.loyada.jdollarx.singlebrowser.custommatchers.CustomMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class JdollarxExampleSingleBrowserTest {

    private static final String driverPath = System.getenv().get("CHROMEDRIVERPATH");

    Path searchFormWrapper = element.that(hasId("searchform")).contains(form).describedBy("search form");
    Path google = input.inside(searchFormWrapper);
    InBrowserSinglton browser;

    @BeforeClass
    public static void setup() {
        driver = new DriverSetup(true).createNewDriver("chrome", driverPath);
    }

    @Before
    public void goToGoogle() {
        driver.get("http://www.google.com");
    }

    @Test
    public void googleForAmazonAndVerifyFirstResult() throws Operations.OperationFailedException {
        //Given
        //When
        sendKeys("amazon").to(google);

        //Then
        Path results = div.that(hasId("search"));
        Path resultsLink = anchor.inside(results);
        Path amazonAsFirstResult = resultsLink.that(isWithIndex(0)).that(hasTextContaining("amazon.com"));
        assertThat(amazonAsFirstResult, isPresent());
    }

    @Test
    public void showAUsefulExceptionForOperationError() throws Operations.OperationFailedException {
        //Given
        Path warcraft = input.inside(searchFormWrapper).withText("for the horde!");
        try {
            // when
            sendKeys("amazon").to(warcraft);
            //then
        } catch (Operations.OperationFailedException e) {
            assertThat(e.getMessage(), equalTo("could not send keys to input, inside (search form), and has the text \"for the horde!\""));
            assertThat(e.getCause().getMessage(), startsWith("could not find input, inside (search form), and has the text \"for the horde!\""));
        }
    }

    @Test
    public void googleForAmazonAndFeelingLucky() throws Operations.OperationFailedException {
        //Given
        sendKeys("amazon").to(google);

        //When
        Path firstSuggestion = firstOccuranceOf(listItem.inside(form));
        hoverOver(firstSuggestion);
        Path feelingLucky = anchor.inside(firstSuggestion).withTextContaining("feeling lucky");
        clickAt(feelingLucky);

        //Then
        Path amazonMainTitle = title.that(hasTextContaining("amazon")).describedBy("amazon main title");
        assertThat(amazonMainTitle, isPresent());
    }

    @Test
    public void googleForAmazonAssertionError1() throws Operations.OperationFailedException {
        //Given
        Path searchFormWrapper = element.that(hasId("searchform")).contains(form);
        Path google = input.inside(searchFormWrapper);

        //When
        sendKeys("amazon").to(google);

        //Then
        Path results = div.that(hasId("search"));
        Path resultsLink = anchor.inside(results);
        Path amazonResult = resultsLink.that(hasTextContaining("amazon.com")).describedBy("search result for amazon");
        assertThat(amazonResult, isPresent());
        try {
            assertThat(amazonResult, isPresent(1000).times());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @Test
    public void googleForAmazonAssertionError2() throws Operations.OperationFailedException {
        //Given
        //When
        sendKeys("amazon").to(google);

        //Then
        Path results = div.that(hasId("search"));
        Path resultsLink = anchor.inside(results);
        Path warcraftResult = firstOccuranceOf(resultsLink).that(hasText("for the horde!"));
        try {
            assertThat(warcraftResult, isPresent());
        } catch (AssertionError e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void teardown() {
        driver.quit();
    }
}
