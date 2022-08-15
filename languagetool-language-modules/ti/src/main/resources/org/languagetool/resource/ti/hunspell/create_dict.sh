#!/bin/bash

# You need to have recode, hunspell and hunspell-tools installed.
# Set LT_VERSION to the version you are currently building on.
# Download or create the files for ADDITIONAL_DICT_FILE, UNKNOWN_TO_HUNSPELL and FREQ_FILE.

echo "Create morfologik spelling dictionary, based on Hunspell dictionary"
echo "This script assumes you have the full LanguageTool build environment"
echo "Please call this script from the LanguageTool top-level directory"
echo ""

if [ $# -ne 2 ]
then
  SCRIPT=`basename $0`
  echo "Usage: $SCRIPT <langCode> <countryCode>"
  echo "  For example: $SCRIPT ti ER"
  exit 1
fi

REPO=$HOME/.m2/repository
LT_VERSION=5.9-SNAPSHOT
INPUT_ENCODING=utf8
OUTPUT_ENCODING=utf8

CPATH=$REPO/com/carrotsearch/hppc/0.8.2/hppc-0.8.2.jar:$REPO/org/carrot2/morfologik-fsa-builders/2.1.7/morfologik-fsa-builders-2.1.7.jar:$REPO/org/carrot2/morfologik-stemming/2.1.7/morfologik-stemming-2.1.7.jar:$REPO/org/carrot2/morfologik-fsa/2.1.7/morfologik-fsa-2.1.7.jar:$REPO/org/carrot2/morfologik-tools/2.1.7/morfologik-tools-2.1.7.jar:$REPO/commons-cli/commons-cli/1.4/commons-cli-1.4.jar:languagetool-tools/target/languagetool-tools-${LT_VERSION}.jar
LANG_CODE=$1
COUNTRY_CODE=$2
PREFIX=${LANG_CODE}_${COUNTRY_CODE}
LANG_MAIN_DIR=languagetool-language-modules/${LANG_CODE}/src/main/resources/org/languagetool/resource/$LANG_CODE
CONTENT_DIR=${LANG_MAIN_DIR}/hunspell
INFO_FILE=${LANG_MAIN_DIR}/${PREFIX}.info
OUTPUT_FILE=${LANG_MAIN_DIR}/${PREFIX}.dict
FREQ_FILE=${CONTENT_DIR}/${PREFIX}_wordlist.xml
DIC_FILE=${CONTENT_DIR}/${PREFIX}.dic
TEMP_FILE=${PREFIX}.text
echo  $CPATH

java -cp $CPATH:languagetool-standalone/target/LanguageTool-$LT_VERSION/LanguageTool-$LT_VERSION/languagetool.jar:languagetool-standalone/target/LanguageTool-$LT_VERSION/LanguageTool-$LT_VERSION/libs/languagetool-tools.jar \
  org.languagetool.tools.SpellDictionaryBuilder -i $DIC_FILE -info $INFO_FILE -o $OUTPUT_FILE  -freq $FREQ_FILE

# Test the compiled dict by exporting it back to a text file (output text file is written in the current directory
java -cp $CPATH:languagetool-standalone/target/LanguageTool-$LT_VERSION/LanguageTool-$LT_VERSION/languagetool.jar:languagetool-standalone/target/LanguageTool-$LT_VERSION/LanguageTool-$LT_VERSION/libs/languagetool-tools.jar   org.languagetool.tools.DictionaryExporter -i $OUTPUT_FILE -info $INFO_FILE -o $TEMP_FILE