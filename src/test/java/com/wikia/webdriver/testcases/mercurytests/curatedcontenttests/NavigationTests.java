package com.wikia.webdriver.testcases.mercurytests.curatedcontenttests;

import com.wikia.webdriver.common.contentpatterns.MercuryPaths;
import com.wikia.webdriver.common.contentpatterns.MercurySubpages;
import com.wikia.webdriver.common.contentpatterns.MercuryWikis;
import com.wikia.webdriver.common.core.url.UrlChecker;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.CuratedContentPageObject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @ownership: Content X-Wing
 */
public class NavigationTests extends NewTestTemplate {

  @BeforeMethod(alwaysRun = true)
  public void prepareTest() {
    driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
    wikiURL = urlBuilder.getUrlForWiki(MercuryWikis.MERCURY_CC);
  }

  // CCT06
  @Test(groups = {"MercuryCuratedNavigationTests_001", "MercuryCuratedNavigationTests",
                  "MercuryCuratedContentTests", "Mercury"})
  public void MercuryCuratedNavigationTests_001_navigateThroughCategory() {
    CuratedContentPageObject category = new CuratedContentPageObject(driver);
    category.openCuratedMainPage(wikiURL, MercurySubpages.CC_MAIN_PAGE);

    category.clickOnCuratedContentElementByIndex(1);
    category.waitForLoadingSpinnerToFinish();

    category
        .isTitleVisible()
        .isLinkToMainPageVisible()
        .isSectionVisible()
        .isCuratedContentItemVisibleByIndex(1);

    String sectionTitle = category.getTitle();
    String expectedUrlPath = MercuryPaths.ROOT_PATH_CATEGORY + sectionTitle;
    UrlChecker.isPathContainedInCurrentUrl(driver, expectedUrlPath);

    String previousUrl = driver.getCurrentUrl();
    category.navigateToMainPage();
    String nextUrl = driver.getCurrentUrl();
    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_PATH);

    driver.navigate().back();
    UrlChecker.isUrlEqualToCurrentUrl(driver, previousUrl);

    category.navigateForward();
    UrlChecker.isUrlEqualToCurrentUrl(driver, nextUrl);
  }

  // CCT07
  @Test(groups = {"MercuryCuratedNavigationTests_002", "MercuryCuratedNavigationTests",
                  "MercuryCuratedContentTests", "Mercury"})
  public void MercuryCuratedNavigationTests_002_navigateThroughSection() {
    CuratedContentPageObject section = new CuratedContentPageObject(driver);
    section.openCuratedMainPage(wikiURL, MercurySubpages.CC_MAIN_PAGE);

    section.clickOnCuratedContentElementByIndex(0);
    section.waitForLoadingSpinnerToFinish();

    section
        .isTitleVisible()
        .isLinkToMainPageVisible()
        .isSectionVisible()
        .isCuratedContentItemVisibleByIndex(1);

    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_PATH_SECTION + section.getTitle());
  }

  // CCT11
  @Test(groups = {"MercuryCuratedNavigationTests_003", "MercuryCuratedNavigationTests",
                  "MercuryCuratedContentTests", "Mercury"})
  public void MercuryCuratedNavigationTests_003_navigateThroughNamespaces() {
    CuratedContentPageObject category = new CuratedContentPageObject(driver);
    category
        .openCuratedContentPage(wikiURL, MercurySubpages.CC_CATEGORY_ARTICLES)
        .isArticleIconVisible()
        .clickOnCuratedContentElementByIndex(0)
        .waitForLoadingSpinnerToFinish();
    category.isArticle();
    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_ARTICLE_PATH);

    category
        .openCuratedContentPage(wikiURL, MercurySubpages.CC_CATEGORY_BLOGS)
        .isBlogIconVisible()
        .clickOnCuratedContentElementByIndex(0)
        .waitForLoadingSpinnerToFinish();
    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_ARTICLE_PATH);

    category
        .openCuratedContentPage(wikiURL, MercurySubpages.CC_CATEGORY_BLOGS)
        .isImageIconVisible()
        .clickOnCuratedContentElementByIndex(0)
        .waitForLoadingSpinnerToFinish();
    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_ARTICLE_PATH);

    category
        .openCuratedContentPage(wikiURL, MercurySubpages.CC_CATEGORY_BLOGS)
        .isVideoIconVisible()
        .clickOnCuratedContentElementByIndex(0)
        .waitForLoadingSpinnerToFinish();
    UrlChecker.isPathContainedInCurrentUrl(driver, MercuryPaths.ROOT_ARTICLE_PATH);
  }

  // CCT09
  @Test(groups = {"MercuryCuratedNavigationTests_004", "MercuryCuratedNavigationTests",
                  "MercuryCuratedContentTests", "Mercury"})
  public void MercuryCuratedNavigationTests_004_navigateThroughDifferentUrl() {
    CuratedContentPageObject section = new CuratedContentPageObject(driver);

    String expectedUrl = wikiURL + MercurySubpages.CC_CATEGORY_TEMPLATES;
    section.navigateToUrlWithPath(wikiURL, MercurySubpages.CC_CATEGORY_TEMPLATES);
    UrlChecker.isUrlEqualToCurrentUrl(driver, expectedUrl);

    expectedUrl = wikiURL + MercurySubpages.CC_SECTION_CATEGORIES;
    section.navigateToUrlWithPath(wikiURL, MercurySubpages.CC_SECTION_CATEGORIES);
    UrlChecker.isUrlEqualToCurrentUrl(driver, expectedUrl);

    expectedUrl = wikiURL;
    section.navigateToUrlWithPath(wikiURL, MercurySubpages.CC_CATEGORY_QWERTY);
    UrlChecker.isUrlEqualToCurrentUrl(driver, expectedUrl);

    section.navigateToUrlWithPath(wikiURL, MercurySubpages.CC_SECTION_QWERTY);
    UrlChecker.isUrlEqualToCurrentUrl(driver, expectedUrl);
  }
}