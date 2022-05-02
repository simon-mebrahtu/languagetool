package org.languagetool.language.ti;

import org.languagetool.JLanguageTool;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. Run this class to clean up the dictionary
 * 2. run DictionaryBuilder to compile it to a .dict file
 * 3. make sure the .dict file is valid by running DictionaryExporter
 *
 * @author Biniam Gebremichael
 */

public class TigrinyaDictionaryCleaner {

  static final String hunspell_dic = "/ti/hunspell/ti_ER.dic";
  static final String hunspell_freq = "/ti/hunspell/ti_ER_wordlist.xml";



  public static void main(String[] args) throws IOException {
    URL freq = JLanguageTool.getDataBroker().getFromResourceDirAsUrl(hunspell_freq);
    URL dic = JLanguageTool.getDataBroker().getFromResourceDirAsUrl(hunspell_dic);
    TigrinyaDictionaryCleaner validator = new TigrinyaDictionaryCleaner();
    List<PipeLine<String>> filter = Arrays.asList(
      //accept frequency >2 and word length > 1
      //replace "ፀ" "ኀ" "ሠ" family with their corresponding Tigrinya families
      new ReplacableWords(),
      // remove words that have numbers or english characters
      new InvalidWords(),
      // remove words that start or end with '
      new InvalidStart(),
      new SplitVerbFromProposition()
    );

    Set<TigrinyaWordFreq> wordFreqs = validator.readDictionary(freq,dic, filter);
    validator.saveWordlistDictionary(freq, wordFreqs);
    validator.saveDictionary(dic, wordFreqs);
  }

  static class InvalidWords implements PipeLine<String> {
    String pattern = "[፩|፪|፫|፬|፭|፮|፯|፰|፱|፲|፳|፴|፵|፶|፷|፸|፹|፺|፻|፼|A-Z|a-z|0-9]";
    Pattern r = Pattern.compile(pattern);

    @Override
    public String consume(String word,Integer frequency,Map<String,String> posMap) {
      return r.matcher(word).find() ? "" : word;
    }
  }

  static class SplitVerbFromProposition implements PipeLine<String> {
    Pattern r = Pattern.compile("^(ስለ|ምስ|ከም|እንተ|ብዘይ)(.+)$");

    @Override
    public String consume(String word,Integer frequency,Map<String,String> posMap) {
      Matcher matcher = r.matcher(word);
      if(matcher.find()){
        String verb = matcher.group(2);
        String pos = posMap.get(verb);
        String posw = posMap.get(word);
        //remove joined words if root exists
        if(verb.length()>2 && posw!=null && pos!=null && (posw.startsWith("V")  || pos.startsWith("V")) ){
          return "";
        }
        else if(verb.length()>3 && pos!=null ){
          System.out.println(matcher.group(1)+" "+verb+" - "+ posw+"/"+ pos);
          return "";
        }
      }
      return word;
    }
  }

  static class InvalidStart implements PipeLine<String> {
    Pattern r = Pattern.compile("^'");
    Pattern r2 = Pattern.compile("'$");

    @Override
    public String consume(String word,Integer frequency,Map<String,String> posMap) {
      return r.matcher(word).find() || r2.matcher(word).find() ? "" : word;
    }
  }

  static class ReplacableWords implements PipeLine<String> {
    @Override
    public String consume(String word,Integer frequency, Map<String,String> posMap) {
      List<String> wrongTigrinya = Arrays.asList("ፀ", "ፁ", "ፂ", "ፃ", "ፄ", "ፅ", "ፆ", "ፇ", "ኀ", "ኁ", "ኂ", "ኃ", "ኄ", "ኅ", "ኆ", "ኇ", "ሠ", "ሡ", "ሢ", "ሣ", "ሤ", "ሥ", "ሦ", "ሧ");
      List<String> correctTigrinya = Arrays.asList("ጸ", "ጹ", "ጺ", "ጻ", "ጼ", "ጽ", "ጾ", "ጿ", "ሀ", "ሁ", "ሂ", "ሃ", "ሄ", "ህ", "ሆ", "ሇ", "ሰ", "ሱ", "ሲ", "ሳ", "ሴ", "ስ", "ሶ", "ሷ");
      for (int i = 0; i < wrongTigrinya.size(); i++) {
        word = word.replace(wrongTigrinya.get(i), correctTigrinya.get(i));
      }
      return word;
    }
  }

  private void saveWordlistDictionary(URL url, Set<TigrinyaWordFreq> dictionary) throws IOException {
    String file = url.getFile().replace("target/classes", "src/main/resources");

    System.out.println("saving to = " + file);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write("<wordlist locale=\"ti\" description=\"Tigrinya\" date=\"1646943648009\" version=\"1\">\n");
    for (TigrinyaWordFreq wordFreq : dictionary) {
      out.write(wordFreq.toString() + "\n");
    }
    out.write("</wordlist>");
    out.close();
  }

  private Map<String,String> readPos(URL url) throws IOException {
    Map<String,String> posMap = new HashMap<>();
    String pattern = "(.+)\t(.+)\t(\\w+)";
    Pattern r = Pattern.compile(pattern);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    int count=0;
    while ((line = reader.readLine()) != null) {
      Matcher m = r.matcher(line);
      if (m.find()) {
        String word = m.group(1);
        String pos = m.group(3);
        posMap.put(word,pos);
      }
    }
    return posMap;
  }

  private void saveDictionary(URL url, Set<TigrinyaWordFreq> dictionary) throws IOException {
    String file = url.getFile().replace("target/classes", "src/main/resources");
    System.out.println("saving to = " + file);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    for (TigrinyaWordFreq wordFreq : dictionary) {
      out.write(wordFreq.toDic() + "\n");
    }
    out.close();
  }

  private Set<TigrinyaWordFreq> readDictionary(URL freq, URL dic, List<PipeLine<String>> pipes) throws IOException {
    SortedSet<TigrinyaWordFreq> dictionary = new TreeSet<>();
    Map<String,String> posMap = readPos(dic);
    String pattern = "<w\\s+f=\"(\\d+)\"\\s+[^>]+>([^<]+)</w>";
    Pattern r = Pattern.compile(pattern);
    BufferedReader reader = new BufferedReader(new InputStreamReader(freq.openStream()));
    String line;
    int count=0;
    while ((line = reader.readLine()) != null) {
      Matcher m = r.matcher(line);
      if (m.find()) {
        String word = m.group(2);
        Integer frequency = Integer.valueOf(m.group(1));
        for (PipeLine<String> pipe : pipes) {
          word = pipe.consume(word,frequency,posMap);
//          if (!word.equals(line)) {
//            System.out.println(word + " " + line);
//          }
        }
        if (word.length() > 1 && frequency>2) {
          dictionary.add(new TigrinyaWordFreq(word, posMap.get(word), frequency));
          count++;
        }
      }
    }
    reader.close();
    System.out.println("read " + count + " words. Writing " + dictionary.size() + ". Removed " + (count-dictionary.size()));
    return dictionary;
  }

  interface PipeLine<T> {
    String consume(T word,Integer frequency, Map<String,String> posMap);
  }

}
