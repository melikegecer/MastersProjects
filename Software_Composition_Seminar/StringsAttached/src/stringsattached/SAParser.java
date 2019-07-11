package stringsattached;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melike Geçer
 */
public class SAParser {

    private static final String PATH = "CodeDataSet";
    private static final String SL_FILE_INSERT_QUERY = "INSERT INTO sl_file (file_id, file_name, file_path) values (?, ?, ?)";
    private static final String SL_TYPE_INSERT_QUERY = "INSERT INTO sl_type (type_id, type_name) values (?, ?)";
    private static final String SL_PROJECT_INSERT_QUERY = "INSERT INTO sl_project (project_id, project_name, project_path) values (?, ?, ?)";
    private static final String SL_HAS_TYPE_INSERT_QUERY = "INSERT INTO sl_has_type (sl_id, type_id) values (?, ?)";
    private static final String STRING_LITERAL_INSERT_QUERY = "INSERT INTO string_literal (file_id, line_number, content) values (?, ?, ?)";
    private static final String SL_PROJECT_HAS_FILE_INSERT_QUERY = "INSERT INTO sl_project_has_file (file_id, project_id) values (?, ?)";

    private static SADBConnector connector = SADBConnector.getInstance();
    private static PreparedStatement statement;

    public static void main(String[] args) {
        try {
            getProjectNames(PATH);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SAParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void getProjectNames(String path) {
        File root = new File(path);
        File[] list = root.listFiles();

        for (int i = 0; i < list.length; i++) {
            try {
                String projectName = list[i].getName();
                int projectId = projectName.hashCode();
                String projectPath = path + "\\" + projectName;
                //
                statement = SADBConnector.getConnection().prepareStatement(SL_PROJECT_INSERT_QUERY);
                statement.setInt(1, projectId);
                statement.setString(2, projectName);
                statement.setString(3, projectPath);
                statement.execute();
                //
                getJavaFiles(projectPath, projectId);
            } catch (SQLException ex) {
                Logger.getLogger(SAParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void getJavaFiles(String path, int projectId) {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                getJavaFiles(f.getPath(), projectId);
            } else {
                if (f.getName().endsWith(".java")) {
                    try {
                        int fileId = f.getPath().hashCode();
                        String fileName = f.getName();
                        String filePath = f.getPath();
                        //
                        statement = SADBConnector.getConnection().prepareStatement(SL_FILE_INSERT_QUERY);
                        statement.setInt(1, fileId);
                        statement.setString(2, fileName);
                        statement.setString(3, filePath);
                        statement.execute();
                        //
                        statement = SADBConnector.getConnection().prepareStatement(SL_PROJECT_HAS_FILE_INSERT_QUERY);
                        statement.setInt(1, fileId);
                        statement.setInt(2, projectId);
                        statement.execute();
                        //
                        parseStringLiterals(f, fileId);
                    } catch (SQLException ex) {
                        Logger.getLogger(SAParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            System.out.println(f.getName());
        }
    }

    private static void parseStringLiterals(File file, int fileId) {
        int countLine = 0;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.replaceAll("\\s", "").startsWith("//") || line.replaceAll("\\s", "").startsWith("/*") || line.replaceAll("\\s", "").startsWith("*") || line.replaceAll("\\s", "").startsWith("*/")) {
                } else {
                    String[] ss = line.split("\"");
                    for (int i = 0; i < ss.length; i++) {
                        if (i % 2 == 1) {
                            try {
                                //
                                statement = SADBConnector.getConnection().prepareStatement(STRING_LITERAL_INSERT_QUERY);
                                statement.setInt(1, fileId);
                                statement.setInt(2, countLine+1);
                                statement.setString(3, ss[i]);
                                statement.execute();
                                //
                            } catch (SQLException ex) {
                                Logger.getLogger(SAParser.class.getName()).log(Level.SEVERE, null, ex);
                                System.out.println("*****  " + countLine + file.getPath() + ss[i]);
                            }
                        }
                    }
                }
                countLine++;
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(SAParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
