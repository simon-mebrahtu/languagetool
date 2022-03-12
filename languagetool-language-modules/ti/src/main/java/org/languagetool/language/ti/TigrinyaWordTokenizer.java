package org.languagetool.language.ti;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.languagetool.tokenizers.Tokenizer;


public class TigrinyaWordTokenizer implements Tokenizer {
 private static final Pattern p = Pattern.compile("[፡።፣፤፥፦፧፨]");
  @Override
  public List<String> tokenize(String text) {
    return Collections.singletonList(p.matcher(text).replaceAll(""));
  }
}

