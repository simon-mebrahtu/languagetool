package org.languagetool.language.ti;


import org.languagetool.tokenizers.WordTokenizer;


/**
 * Tigrinya tokenizer.
 *
 * @author Biniam Gebremichael
 */

public class TigrinyaWordTokenizer extends WordTokenizer {

  @Override
  public String getTokenizingCharacters() {
//    todo: also add English characters as token characters. This way we can skip spell-checking non geez characters?
    return super.getTokenizingCharacters() + "፡።፣፤፥፦፧፨";
  }
}

