/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 20:42
*/
public class unicode {
    public static void main(String[] args) {

            String str = "\\u767b\\u5f55\\u914d\\u7f6e\\uff0c\\u53c2\\u89c1";
//        String str = "假装这里有Emoji表情";
//        unicode(str);
            System.out.println("decodeUnicode:"+decodeUnicode(str));
            System.out.println("decodeUnicode2:"+decodeUnicode2(unicode(str)));
        }

        public static String unicode(String source){
            StringBuffer sb = new StringBuffer();
            char [] source_char = source.toCharArray();
            String unicode = null;
            for (int i=0;i<source_char.length;i++) {
                unicode = Integer.toHexString(source_char[i]);
                if (unicode.length() <= 2) {
                    unicode = "00" + unicode;
                }
                sb.append("\\u" + unicode);
            }
            System.out.println(sb);
            return sb.toString();
        }

        public static String decodeUnicode(String unicode) {
            StringBuffer sb = new StringBuffer();

            String[] hex = unicode.split("\\\\u");

            for (int i = 1; i < hex.length; i++) {
                int data = Integer.parseInt(hex[i], 16);
                sb.append((char) data);
            }
            return sb.toString();
        }

        public static String decodeUnicode2(String dataStr) {
            int start = 0;
            int end = 0;
            final StringBuffer buffer = new StringBuffer();
            while (start > -1) {
                end = dataStr.indexOf("\\u", start + 2);
                String charStr = null;
                if (end == -1) {
                    charStr = dataStr.substring(start + 2, dataStr.length());
                } else {
                    charStr = dataStr.substring(start + 2, end);
                }
                char letter = (char) Integer.parseInt(charStr, 16);
                buffer.append(new Character(letter).toString());
                start = end;
            }
            return buffer.toString();
        }


}
