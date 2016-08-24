package fi.helsinki.cs.tmc.intellij.spyware;


import com.intellij.openapi.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivateSpywareListeners {

    private static final Logger logger = LoggerFactory.getLogger(TextInputListener.class);

    private Project project;

    public ActivateSpywareListeners(Project project) {
        logger.info("Activating spyware listeners.");
        this.project = project;
        new SpywareRunListener(project);
        new SpywareFileListener(project);
        new SpywareTabListener(project);
    }
}
