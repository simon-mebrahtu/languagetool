package org.languagetool.language.ti;

import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.rules.spelling.morfologik.MorfologikSpellerRule;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Morfologik Hunspell dictionary adaptor for Tigrinya
 *
 * @author Biniam Gebremichael
 */


public class MorfologikTigrinyaSpellerRule extends MorfologikSpellerRule {

  public MorfologikTigrinyaSpellerRule(ResourceBundle messages, Language language, UserConfig userConfig, List<Language> altLanguages) throws IOException {
    super(messages, language, userConfig, altLanguages);
  }

  @Override
  public String getFileName() {
    return "/ti/ti_ER.dict";
  }

  @Override
  public final String getId() {
    return "MORFOLOGIK_RULE_TI";
  }

  @Override
  protected boolean isLatinScript() {
    return false;
  }

  //ignore all characters except ሀ-ፚ
  private static final Pattern he2fie = Pattern.compile("[\\x{1200}-\\x{135A}]{1,20}") ;
  @Override
  protected boolean ignoreWord(String word) throws IOException {
    Matcher matcher = he2fie.matcher(word);
    return super.ignoreWord(word) || !matcher.matches() || word.length()>1;
  }

}
