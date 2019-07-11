package stringsattached;

import java.util.ArrayList;

/**
 *
 * @author Melike Geçer
 */
public class SAStringLiteral {

    private int stringLiteralId;
    private int lineNumber;
    private String content;

    private ArrayList<SLType> typeList;

    private int fileId;
    private String fileName;
    private String filePath;

    public SAStringLiteral(int stringLiteralId, int lineNumber, String content, int fileId, String fileName, String filePath) {
        this.stringLiteralId = stringLiteralId;
        this.lineNumber = lineNumber;
        this.content = content;

        this.typeList = new ArrayList<>();

        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getContent() {
        return content;
    }

    public int getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getStringLiteralId() {
        return stringLiteralId;
    }

    private static class SLType {

        private int typeId;
        private String typeName;

        public SLType(int typeId, String typeName) {
            this.typeId = typeId;
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getTypeId() {
            return typeId;
        }

    }

}
