[![][travis img]][travis]
[![][license img]][license]
##### DollarX Java [![][maven-java img]][maven-java]
##### DollarX Scala [![][maven-scala img]][maven-scala]
# DollarX

## What is it?
A library with the goal of making interactions with a web browser for the purpose 
of testing web applications trivial and fun.

## Properies
* Two versions: Scala(dollarX) and Java (jdollarX). Both are immutable.
* Flexible syntax that allows to declare arbitrarily complex DOM elements easily.
* Optimized for atomic interaction with the browser, to avoid common pitfalls in testing web applications and optimize performance.
* Includes custom matchers for both Hamcrest and ScalaTest. The matchers provide useful error messages.
* Easy to troubleshoot, since toString() output is basically English.
* Two "flavors": The standard, with multiple browsers instances support, and a simplified API for a single browser instance.
* Check dollarx-example and jdollarx-example for examples of working tests.



dollarx-example, jdollarx-example contain several behavior tests and other examples that demonstrate the use of the library. 
Beyond that, the unit tests in dollarx, jdollarx contain many examples. 

Documentation is here 
## Documentation

[JavaDoc...](http://htmlpreview.github.io/?https://github.com/loyada/dollarx/blob/master/jdollarx/javadoc/index.html)

[Detailed Documentation](http://dollarx.readthedocs.io/en/latest/)

### Use case examples (using *dollarx-java*):
##### Extracting an element from a W3C Document:
```java
        NodeList nodes = PathParsers.findAllByPath("<div>foo</div><span></span>><div>boo</div>",
                                                     BasicPath.div.before(BasicPath.span)); 
       // and using static imports makes the expression simpler:
       //   findAllByPath("...", div.before(span))                                              
```

##### Testing for existence in a W3C document using dollarx custom matchers:
```java
       import static com.github.loyada.jdollarx.custommatchers.CustomMatchers.*;
       import static com.github.loyada.jdollarx.BasicPath.*;

       Document doc = PathParsers.getDocumentFromString("<div><span></span></div>");
       assertThat(div.inside(div), isAbsentFrom(doc));
       // or ...
       assertThat(span.inside(div), isPresentIn(doc));
       // or you might want ...
       assertThat(div.withClass("foo"), isPresent(5).timesOrLessIn(doc));
```
Let's say the last assertion in our examples fails. Then the error message would look like:
```
 Expected: document contains div with class "foo" at most 5 times
     but: div with class "foo" appears 6 times
```

##### Testing for existence in browser:
```java
       import static com.github.loyada.jdollarx.custommatchers.CustomMatchers.*;

        InBrowser browser; 
 
        assertThat(div, isPresentIn(browser));
        // which is equivalent to:
        assertThat(browser, hasElement(div));
        //  or you  might  want to assert something like:
        assertThat(span.inside(div), isPresent(5).timesIn(browser));   
```

##### Performing actions in the browser:
```java
         InBrowser browser; 
         Path myElement;
         
         browser.scrollTo(myElement);
         browser.hoverOver(myElement);
         browser.clickAt(myElement);
```
 In case you have a single browser instance,the code becomes simpler:
```java
          import static com.github.loyada.jdollarx.singlebrowser.InBrowserSinglton.*;

          Path myElement;
          
          scrollTo(myElement);
          hoverOver(myElement);
          clickAt(myElement);
          // or alternatively, if you prefer the OO style:
          myElement.click();        
```

### Behavior test examples instructions
For the behavior tests examples (in dollarx-example, jdollarx-example) to work, you need to
download the chrome selenium driver and set an environment variable 'CHROMEDRIVERPATH' to its path location.

###  Maven Dependency
[Just look here...](http://search.maven.org/#search%7Cga%7C1%7Cdollarx)

[travis]:https://travis-ci.org/loyada/dollarx
[travis img]:https://travis-ci.org/loyada/dollarx.svg?branch=master

[maven-scala]:http://search.maven.org/#search|gav|1|g:"com.github.loyada.dollarx"%20AND%20a:"dollarx-scala"
[maven-scala img]:https://maven-badges.herokuapp.com/maven-central/com.github.loyada.dollarx/dollarx-scala/badge.svg

[maven-java]:http://search.maven.org/#search|gav|1|g:"com.github.loyada.dollarx"%20AND%20a:"dollarx-java"
[maven-java img]:https://maven-badges.herokuapp.com/maven-central/com.github.loyada.dollarx/dollarx-java/badge.svg

[license]:LICENSE.txt
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg