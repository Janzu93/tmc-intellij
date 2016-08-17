package fi.helsinki.cs.tmc.intellij.services;

import com.intellij.openapi.project.Project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Offers methods to get info from a path.
 */
public class PathResolver {

    private static final Logger logger = LoggerFactory.getLogger(PathResolver.class);

    /**
     * Returns an array containing a course name and exercise name.
     * The course name will be in the second last element of the
     * array and the exercise in the last one.
     * @param project project info is wanted from.
     * @return array containing the course and exercise name derived
     *     from a project's path
     */
    public static String[] getCourseAndExerciseName(Project project) {
        logger.info("Processing getCourseAndExerciseName"
                + " with Project parameter. @PathResolver");
        return getCourseAndExerciseName(project.getBasePath());

    }

    /**
     * Returns an array containing a course name and exercise name.
     * The course name will be in the second last element of the
     * array and the exercise in the last one.
     * @param path path of a project the info is wanted from
     * @return array containing the course and exercise name
     *     derived from the given path
     */
    public static String[] getCourseAndExerciseName(Path path) {
        logger.info("Processing getCourseAndExerciseName"
                + " with Path parameter. @PathResolver");
        return getCourseAndExerciseName(path.toString());
    }

    /**
     * Returns an array containing a course name and exercise name.
     * The course name will be in the second last element of the
     * array and the exercise in the last one.
     * @param path path of a project as string
     * @return array containing the course and exercise name derived
     *     from the given path
     */
    public static String[] getCourseAndExerciseName(String path) {
        logger.info("Processing getCourseAndExerciseName "
                + "with String parameter. @PathResolver");
        if (path == null) {
            return null;
        }

        if (osIsUnixBased(path)) {
            return path.split("/");
        }
        return path.split("\\\\");
    }

    private static boolean osIsUnixBased(String path) {
        return path.contains("/");
    }
}