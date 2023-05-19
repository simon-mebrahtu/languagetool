package org.languagetool;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestTigre {
  @Test
  public void languageDetector() throws IOException {
    //load all languages:
    List<LanguageProfile> languageProfiles = new LanguageProfileReader().readAllBuiltIn();

//build language detector:
    LanguageDetector languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
      .withProfiles(languageProfiles)
      .build();

//create a text object factory
    TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();

//query:
    TextObject textObject = textObjectFactory.forText("ላኪን እግል ንቀይሩ እት እዴነ ክምቱ እግል ንትረሰዕ አለብነ።");
    Optional<LdLocale> lang = languageDetector.detect(textObject);
  }
}
