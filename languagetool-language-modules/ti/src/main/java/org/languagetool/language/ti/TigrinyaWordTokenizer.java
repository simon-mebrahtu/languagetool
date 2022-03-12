package org.languagetool.language.ti;


import org.languagetool.tokenizers.WordTokenizer;


public class TigrinyaWordTokenizer extends WordTokenizer {

  @Override
  public String getTokenizingCharacters() {
    return super.getTokenizingCharacters() + "፡።፣፤፥፦፧፨";
  }
}

