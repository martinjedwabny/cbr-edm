package ilp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class EDMProbFOILSolver {
    private String backgroundKnowledge = "";
    private String modes = "";
    private String examples = "";
    private ArrayList<String> result;

    public ArrayList<String> getResult() {
        return result;
    }

    public void solve(String backgroundKnowledge, String modes, String examples) throws Exception {
        this.backgroundKnowledge = backgroundKnowledge;
        this.modes = modes;
        this.examples = examples;
        File bkFile = createResourceFile("o-bk", ".pl", this.backgroundKnowledge + "\n" + this.examples);
        File modesFile = createResourceFile("o-modes", ".pl", this.modes);
        String command = getCommand(bkFile, modesFile);
//        runILPSolver(command);
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

    private String getCommand(File bkFile, File modesFile) {
        String command = "python ";
        command += Thread.currentThread().getContextClassLoader().getResource("popper/popper.py").getPath();
        command += " " + modesFile.getAbsolutePath() + " " + bkFile.getAbsolutePath();
        return command;
    }

    private File createTemporaryFile(String prefix, String suffix, String content) throws IOException {
        Path tempFile = Files.createTempFile(prefix, suffix);
        Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));
        File f = tempFile.toFile();
        f.deleteOnExit();
        return f;
    }

    private File createResourceFile(String prefix, String suffix, String content) throws IOException {
        File f = new File(prefix + suffix);
        Files.write(f.toPath(), content.getBytes(StandardCharsets.UTF_8));
        return f;
    }
}
