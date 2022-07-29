package org.languagetool.language.tig;

import org.languagetool.tokenizers.WordTokenizer;

public class TigreWordTokenizer extends WordTokenizer {
  @Override
  public String getTokenizingCharacters() {
//    todo: also add English characters as token characters. This way we can skip spell-checking non geez characters?
    return super.getTokenizingCharacters() + "፡።፣፤፥፦፧፨-";
  }
}
