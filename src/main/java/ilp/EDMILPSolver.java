package ilp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class EDMILPSolver {
    private String backgroundKnowledge = "";
    private String modes = "";
    private String examples = "";
    private ArrayList<String> result;

    public EDMILPSolver () {}

    public ArrayList<String> getResult() {
        return result;
    }

    public void solve(String backgroundKnowledge, String modes, String examples) throws Exception {
        this.backgroundKnowledge = backgroundKnowledge;
        this.modes = modes;
        this.examples = examples;
        File bkFile = createTemporaryFile("bk-", ".pl", this.backgroundKnowledge);
        File modesFile = createTemporaryFile("modes-", ".pl", this.modes);
        File examplesFile = createTemporaryFile("examples-", ".pl", this.examples);
        String command = getCommand(bkFile, modesFile, examplesFile);
        runILPSolver(command);
    }

    private void runILPSolver(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        this.result = new ArrayList<>();
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            result.add(s);
        }
    }

    private String getCommand(File bkFile, File modesFile, File examplesFile) {
        String command = "python ";
        command += Thread.currentThread().getContextClassLoader().getResource("popper/popper.py").getPath();
        command += " " + examplesFile.getAbsolutePath() + " " + modesFile.getAbsolutePath() + " " + bkFile.getAbsolutePath();
        return command;
    }

    private File createTemporaryFile(String prefix, String suffix, String content) throws IOException {
        Path tempFile = Files.createTempFile(prefix, suffix);
        Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));
        File f = tempFile.toFile();
        f.deleteOnExit();
        return f;
    }
}
