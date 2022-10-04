package org.languagetool.language.tig;

import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.rules.spelling.morfologik.MorfologikSpellerRule;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MorfologikTigreSpellerRule extends MorfologikSpellerRule {
  public MorfologikTigreSpellerRule(ResourceBundle messages, Language language, UserConfig userConfig, List<Language> altLanguages) throws IOException {
    super(messages, language, userConfig, altLanguages);
  }

  @Override
  public String getFileName() {
    return "/tig/tig_ER.dict";
  }

  @Override
  public final String getId() {
    return "MORFOLOGIK_RULE_TIG";
  }

  @Override
  protected boolean isLatinScript() {
    return false;
  }

  //ignore all characters except ሀ-ፚ
  private static Pattern he2fie = Pattern.compile("[\\x{1200}-\\x{135A}]{1,20}") ;
  @Override
  protected boolean ignoreWord(String word) throws IOException {
    Matcher matcher = he2fie.matcher(word);
    return super.ignoreWord(word) || !matcher.matches();
  }

}
