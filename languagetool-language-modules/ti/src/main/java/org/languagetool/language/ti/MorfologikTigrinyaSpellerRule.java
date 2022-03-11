package org.languagetool.language.ti;

import org.languagetool.Language;
import org.languagetool.UserConfig;
import org.languagetool.rules.spelling.morfologik.MorfologikSpellerRule;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

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

}
