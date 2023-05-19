package org.languagetool.language;

import org.jetbrains.annotations.NotNull;
import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.language.tig.MorfologikTigreSpellerRule;
import org.languagetool.language.tig.TigreWordTokenizer;
import org.languagetool.rules.*;
import org.languagetool.tagging.Tagger;
import org.languagetool.tagging.xx.DemoTagger;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;
import org.languagetool.tokenizers.Tokenizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


/**
 * main class for Tigre Language
 *
 * @author Simon Ogbamichael
 */

public class Tigre extends Language {

  @Override
  public String getName() {
    return "Tigre";
  }

  @Override
  public String getShortCode() {
    return "tig";
  }

  @Override
  public String[] getCountries() {
    return new String[]{"ER"};
  }

  @Override
  public SentenceTokenizer createDefaultSentenceTokenizer() {
    return new SRXSentenceTokenizer(this);
  }
  @Override
  public Tokenizer createDefaultWordTokenizer() {
    return new TigreWordTokenizer();
  }

  @NotNull
  @Override
  public Tagger createDefaultTagger() {
    return new DemoTagger();
  }

  @Override
  public Contributor[] getMaintainers() {
    return new Contributor[]{new Contributor("Simon Ogbamichael")};
  }

  @Override
  public List<Rule> getRelevantRules(ResourceBundle messages, UserConfig userConfig, Language motherTongue, List<Language> altLanguages) throws IOException {
    return Arrays.asList(
      new CommaWhitespaceRule(messages),
      new MultipleWhitespaceRule(messages, this),
      new LongSentenceRule(messages, userConfig, 50),
      new SentenceWhitespaceRule(messages),
      new MorfologikTigreSpellerRule(messages, this, userConfig, altLanguages)
    );
  }

}