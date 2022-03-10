/**
 * Created by biniamg on 3/6/2022.
 */
package org.languagetool.language;

import org.jetbrains.annotations.NotNull;
import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.language.ti.MorfologikTigrinyaSpellerRule;
import org.languagetool.rules.*;
import org.languagetool.rules.spelling.SpellingCheckRule;
import org.languagetool.tagging.Tagger;
import org.languagetool.language.tagger.TigrinyaTagger;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;

import java.io.IOException;
import java.util.*;

public class Tigrinya extends Language {

  @Override
  public String getName() {
    return "Tigrinya";
  }

  @Override
  public String getShortCode() {
    return "ti";
  }

  @Override
  public String[] getCountries() {
    return new String[]{"ER", "ET"};
  }

  @Override
  public SentenceTokenizer createDefaultSentenceTokenizer() {
    return new SRXSentenceTokenizer(this);
  }

  @NotNull
  @Override
  public Tagger createDefaultTagger() {
    return new TigrinyaTagger();
  }

  @Override
  public Contributor[] getMaintainers() {
    return new Contributor[]{new Contributor("Biniam Gebremichael")};
  }

    @Override
    public List<Rule> getRelevantRules(ResourceBundle messages, UserConfig userConfig, Language motherTongue, List<Language> altLanguages) throws IOException {
        return Arrays.asList(
                new CommaWhitespaceRule(messages),
                new DoublePunctuationRule(messages),
                new MultipleWhitespaceRule(messages, this),
                new LongSentenceRule(messages, userConfig, 50),
                new SentenceWhitespaceRule(messages),
          new MorfologikTigrinyaSpellerRule(messages, this, userConfig, altLanguages)
        );
    }

}
