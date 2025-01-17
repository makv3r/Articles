package util;

import org.w3c.tidy.Tidy;

import java.io.*;

public final class PrettyHTML {

    private PrettyHTML() {
    }

    public static String applyTidy(String html) {
        // https://www.programcreek.com/java-api-examples/?class=org.w3c.tidy.Tidy&method=setFixBackslash

        Tidy tidy = new Tidy();

        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);
        tidy.setDropEmptyParas(true);
        tidy.setTrimEmptyElements(false);
        tidy.setFixUri(false);
        tidy.setFixBackslash(false);
        tidy.setEncloseBlockText(false);
        tidy.setEncloseText(false);
        tidy.setHideEndTags(false);
        tidy.setIndentContent(true);
        tidy.setLiteralAttribs(false);
        tidy.setLogicalEmphasis(false);
        tidy.setMakeClean(true);
        tidy.setNumEntities(true);
        tidy.setWord2000(false);
        tidy.setXHTML(false);
        tidy.setQuoteNbsp(true);
        tidy.setXmlPi(false);
        tidy.setPrintBodyOnly(true);
        tidy.setIndentAttributes(false);
        tidy.setIndentCdata(false);
        tidy.setIndentContent(false);
        tidy.setBreakBeforeBR(false);
        tidy.setSpaces(0);
        tidy.setHideComments(true);
        tidy.setFixComments(true);
        tidy.setSmartIndent(true);
        tidy.setWraplen(30000);

        StringWriter result = new StringWriter();
        tidy.parse(new StringReader(html), result);

        return result.toString();
    }
}
