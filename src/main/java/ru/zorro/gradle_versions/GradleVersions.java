package ru.zorro.gradle_versions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradleVersions {

    //private final static int FILES_WALK_DEPTH = 1;
    //private final static int DIRS_WALK_DEPTH = 1;

    private static final String BUILD_GRADLE_FILE = "build.gradle";
    private static final String WORD_VERSION = "version";
    private static final String SYMBOL_EQUALS = "=";
    private static final String SYMBOL_QUOTE = "\"";
    private static final String MDDR_GROUP = "ru.ase.uar.mddr";

    private GradleVersions() {
    }

    public static void scanGradleVersions(String[] args) throws IOException {
        System.out.println("---------------start---------------");
        String scanDirectory = getScanDirectory(args);
        scanDirectory = "k:\\java_projects\\_planner_idea_workspace_20230912_small\\modules";
        System.out.println("basePath: " + scanDirectory);
        List<Path> paths = getGradleFiles(scanDirectory);
        List<Artefact> artefacts = GetArtefacts(paths);
        for (Artefact artefact:artefacts) System.out.println("artefact: " + artefact.toString());

        //todo !!!!!! убрать uuid из артефакта и сделать сравнение по группа артефакт версия
        // функции проверки на уникальность артефактов, на дубли без учета версии

        System.out.println("---------------end---------------");
    }


    // получение списка артефактов
    private static List<Artefact> GetArtefacts(List<Path> paths) throws IOException {
        List<Artefact> artefacts = new ArrayList<>();
        for (Path path : paths) {
            Artefact artefact = getArtefact(path);
            if (artefact != null)
                artefacts.add(artefact);
        }
        return artefacts;
    }

    // получение артефакта из build.gradle файла
    private static Artefact getArtefact(Path path) throws IOException {
        try (Scanner scanner = new Scanner(new File(path.toAbsolutePath().toString()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (containsIgnoreCase(line, WORD_VERSION) && line.contains(SYMBOL_EQUALS) && line.contains(SYMBOL_QUOTE)) {
                    String artefactName = getArtefactName(path);
                    String version = parseVersion(line);
                    if (artefactName != null && !artefactName.trim().isEmpty() && version != null && !version.trim().isEmpty()) {
                        return new Artefact(UUID.randomUUID(), MDDR_GROUP, artefactName, version, path.toAbsolutePath().toString());
                    }
                }
            }
        }

        return null;
    }

    // получение номера версии из строки с версией
    private static String parseVersion(String line)
    {
        line = line.toLowerCase();

        int versionStartIndex = line.indexOf(WORD_VERSION);
        int equalsStartIndex = line.indexOf(SYMBOL_EQUALS, versionStartIndex + WORD_VERSION.length());
        int firstQuoteStartIndex = line.indexOf(SYMBOL_QUOTE, equalsStartIndex  + SYMBOL_EQUALS.length());
        int lastQuoteStartIndex = line.indexOf(SYMBOL_QUOTE, firstQuoteStartIndex + SYMBOL_EQUALS.length());

        if (firstQuoteStartIndex > 0 && lastQuoteStartIndex > firstQuoteStartIndex) {
            return line.substring(firstQuoteStartIndex + SYMBOL_QUOTE.length(), lastQuoteStartIndex);
        }

        return null;
    }

    // получение артефакта из имени файла
    private static String getArtefactName(Path path)
    {
        return path.getParent().getFileName().toString();
    }

    // получение всех build.gradle файлов
    private static List<Path> getGradleFiles(String scanDirectory) throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(scanDirectory), Integer.MAX_VALUE)) {
            return walk
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList())
                    .stream()
                    .filter(path -> path.getFileName().compareTo(Paths.get(BUILD_GRADLE_FILE)) == 0)
                    .collect(Collectors.toList());
        }
    }

    // получение директории в которой происходит сканирование
    private static String getScanDirectory(String[] args) {
        String baseDir = Paths.get("").toAbsolutePath().toString();
        if (args != null && args.length > 0 && Files.exists(Paths.get(args[0])))
             baseDir = Paths.get(args[0]).toAbsolutePath().toString();
        return baseDir;
    }

    // сравнение 2х строк без учета регистра
    private static boolean containsIgnoreCase(String str, String searchStr) {
        if(str == null || searchStr == null)
            return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

}
