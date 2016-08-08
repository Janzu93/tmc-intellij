package fi.helsinki.cs.tmc.intellij.services;


import fi.helsinki.cs.tmc.core.exceptions.TmcCoreException;
import fi.helsinki.cs.tmc.intellij.holders.TmcSettingsManager;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

/**
 * Pops up user friendly warnings for CourseAndExerciseManager exceptions.
 * For example pops up an error message on startup if there is no internet connection
 * or TMC user settings (username, password or server address) has not been initialized.
 */
public class ErrorMessageService {

    /**
     * Error message, if TMC username or password are not initialized.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String notifyAboutUsernamePasswordAndServerAddress(TmcCoreException exception) {
        return errorCode(exception)
                + "Seems like you don't have your TMC settings initialized. \n"
                + "Set up your username, password and TMC server address.";
    }

    /**
     * Error message, if internet connection appears to be offline.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String notifyAboutInternetConnection(TmcCoreException exception) {
        return errorCode(exception) + "Check your internet connection.";
    }

    /**
     * Error message, if TMC server address has not been initialized.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String notifyAboutEmptyServerAddress(TmcCoreException exception) {
        return errorCode(exception)
                + "You need to set up TMC server address "
                + "to be able to download and submit exercises.";
    }

    /**
     * Error message, if TMC username or password is incorrect.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String notifyAboutIncorrectUsernameOrPassword(TmcCoreException exception) {
        return errorCode(exception) + "TMC Username or Password incorrect.";
    }
    /**
     * Error message, prints out the cause of the current exception.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String errorCode(TmcCoreException exception) {
        return exception.getCause().getMessage() + ". \n";
    }

    /**
     * Error message, prints out the cause of the current exception.
     * @param exception The cause of an error.
     * @return String. Error message that will be shown to the user.
     */
    private String errorCode(Exception exception, String str) {
        return exception + ". \n" + str;
    }

    /**
     * Creates a new notification group TMC_NOTIFICATION
     * for TMC notifications to the notification board.
     */
    public static final NotificationGroup TMC_NOTIFICATION =
            new NotificationGroup("TMC Error Messages",
                    NotificationDisplayType.STICKY_BALLOON, true);

    /**
     * Generates a notification popup.
     * @param str Notification message.
     */
    private void initializeNotification(String str, NotificationType type) {
        Project projects = new ObjectFinder().findCurrentProject();
        Notification notification = TMC_NOTIFICATION
                .createNotification(str,
                        type);
        Notifications.Bus.notify(notification, projects);
    }

    /**
     * Controls which error message will be shown to the user.
     * @param exception The cause of an error.
     */
    public void showMessage(final TmcCoreException exception) {

        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                String str = exception.getCause().getMessage();
                NotificationType type = NotificationType.WARNING;
                if (str.contains("Download failed: tmc.mooc.fi: unknown error")) {
                    initializeNotification(notifyAboutInternetConnection(exception), type);
                } else if (!TmcSettingsManager.get().userDataExists()) {
                    initializeNotification(notifyAboutUsernamePasswordAndServerAddress(exception),
                            type);
                } else if (str.contains("401")) {
                    initializeNotification(notifyAboutIncorrectUsernameOrPassword(exception),
                            NotificationType.ERROR);
                } else if (TmcSettingsManager.get().getServerAddress().isEmpty()) {
                    initializeNotification(notifyAboutEmptyServerAddress(exception), type);
                } else {
                    initializeNotification(errorCode(exception), NotificationType.ERROR);
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Controls which error message will be shown to the user.
     * @param exception The cause of an error.
     * @param errorMessage Error message.
     */
    public void showMessage(final Exception exception, final String errorMessage) {

        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            @Override
            public void run() {
                initializeNotification(errorCode(exception, errorMessage), NotificationType.ERROR);
                exception.printStackTrace();
            }
        });
    }
}

/*
        ToolBar toolBar = WindowManager.getInstance()
                .getToolBar(DataKeys.PROJECT.getData(actionEvent.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(toolBar.getComponent()),
                        Balloon.Position.atRight);
*/
