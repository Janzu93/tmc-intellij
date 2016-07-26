package fi.helsinki.cs.tmc.intellij.services;

import fi.helsinki.cs.tmc.core.domain.Course;
import fi.helsinki.cs.tmc.core.domain.Exercise;
import fi.helsinki.cs.tmc.core.domain.ProgressObserver;
import fi.helsinki.cs.tmc.intellij.holders.TmcCoreHolder;
import fi.helsinki.cs.tmc.intellij.ui.projectlist.ProjectListManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseAndExerciseManager {

    public static void setDatabase(HashMap<String, ArrayList<Exercise>> database) {
        CourseAndExerciseManager.database = database;
    }

    static HashMap<String, ArrayList<Exercise>> database;
    private static ArrayList<Course> courses;

    public CourseAndExerciseManager() {
        try {
            initiateDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setup() {
        if (database == null) {
            try {
                initiateDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Exercise get(String course, String exercise) {
        try {
            ArrayList<Exercise> exercises = database.get(course);
            for (Exercise exc : exercises) {
                if (exc.getName().equals(exercise)) {
                    return exc;
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static ArrayList<Exercise> getExercises(String course) {
        try {
            return database.get(course);
        } catch (Exception e) {

        }
        return null;
    }

    public static HashMap<String, ArrayList<Exercise>> getDatabase() {
        return database;
    }

    static void initiateDatabase() throws Exception {
        ArrayList<String> directoriesOnDisk = new ObjectFinder().listAllDownloadedCourses();
        database = new HashMap<>();
        courses = new ArrayList<>();
        courses = (ArrayList<Course>) TmcCoreHolder.get()
                .listCourses(ProgressObserver.NULL_OBSERVER).call();
        for (Course course : courses) {
            ArrayList<Exercise> exercises;
            if (directoriesOnDisk.contains(course.getName())) {
                try {
                    course = TmcCoreHolder.get()
                            .getCourseDetails(ProgressObserver.NULL_OBSERVER, course).call();
                    exercises = (ArrayList<Exercise>) new CheckForExistingExercises()
                            .getListOfDownloadedExercises(course.getExercises());
                    database.put(course.getName(), exercises);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateSinglecourse(String course, CheckForExistingExercises checker) {
        Course crse = new ObjectFinder().findCourseByName(course, TmcCoreHolder.get());
        ArrayList<Exercise> existing = (ArrayList<Exercise>) checker
                .getListOfDownloadedExercises(crse.getExercises());
        database.put(course, existing);
        ProjectListManager.refreshCourse(course);
    }

    public static void updateAll() {
        try {
            initiateDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isCourseInDatabase(String string) {
        return database.keySet().contains(string);
    }
}
