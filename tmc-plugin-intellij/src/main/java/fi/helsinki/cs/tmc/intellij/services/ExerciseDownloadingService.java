package fi.helsinki.cs.tmc.intellij.services;

import fi.helsinki.cs.tmc.core.TmcCore;
import fi.helsinki.cs.tmc.core.domain.Course;
import fi.helsinki.cs.tmc.core.domain.Exercise;
import fi.helsinki.cs.tmc.core.domain.ProgressObserver;
import fi.helsinki.cs.tmc.intellij.holders.ProjectListManagerHolder;
import fi.helsinki.cs.tmc.intellij.io.SettingsTmc;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Offers method for downloading exercises from selected course.
 */
public class ExerciseDownloadingService {

    private static final Logger logger = LoggerFactory
            .getLogger(ExerciseDownloadingService.class);

    public static void startDownloadExercise(final TmcCore core,
                                             final SettingsTmc settings,
                                             final CheckForExistingExercises checker,
                                             ObjectFinder objectFinder,
                                             ThreadingService threadingService,
                                             Project project) throws Exception {
        logger.info("Preparing to start downloading exercises. @ExerciseDownloadingService");
        Thread run = createThread(core, settings, checker, objectFinder);
        ThreadingService
                .runWithNotification(run,
                        "Downloading exercises, this may take several minutes",
                        project);
    }

    @NotNull
    private static Thread createThread(final TmcCore core,
        final SettingsTmc settings,
        final CheckForExistingExercises checker,
        final ObjectFinder finder) {
        logger.info("Creating a new thread. @ExerciseDownloadingService");

        return new Thread() {
            @Override
            public void run() {
                ErrorMessageService errorMessageService = new ErrorMessageService();
                try {
                    logger.info("Starting to download exercise. @ExerciseDownloadingService");
                    final Course course = finder.findCourseByName(settings.getCourse().getName(), core);
                    List<Exercise> exercises = course.getExercises();
                    exercises = checker.clean(exercises, settings);
                    try {
                        core.downloadOrUpdateExercises(ProgressObserver.NULL_OBSERVER,
                                exercises).call();
                    } catch (Exception exception) {
                        logger.info("Failed to download exercises. @ExerciseDownloadingService");
                        new ErrorMessageService().showMessage(exception,
                                "Failed to download exercises.", true);
                    }
                } catch (Exception except) {
                    logger.warn("Failed to download exercises. "
                                    + "Course not selected. @ExerciseDownloadingService",
                            except, except.getStackTrace());
                    errorMessageService.showMessage(except,
                            "You need to select a course to be able to download.", true);
                }

                ApplicationManager.getApplication().invokeLater(
                        new Runnable() {
                            public void run() {
                                ApplicationManager.getApplication().runWriteAction(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                logger.info("Updating project list. "
                                                        + "@ExerciseDownloadingService");
                                                new CourseAndExerciseManager().updateAll();
                                                ProjectListManagerHolder.get().refreshAllCourses();
                                            }
                                        }
                                );
                            }
                        });;
            }
        };
    }
}
