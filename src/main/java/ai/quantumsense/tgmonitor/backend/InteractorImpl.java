package ai.quantumsense.tgmonitor.backend;

import ai.quantumsense.tgmonitor.backend.pojo.PatternMatch;
import ai.quantumsense.tgmonitor.backend.pojo.TelegramMessage;
import ai.quantumsense.tgmonitor.servicelocator.ServiceLocator;

public class InteractorImpl implements Interactor{

    private PatternMatcher matcher;
    private Notificator notificator;

    public InteractorImpl(PatternMatcher matcher, Notificator notificator, ServiceLocator<Interactor> interactorLocator) {
        System.out.println("InteractorImpl constructor");
        this.matcher = matcher;
        this.notificator = notificator;
        interactorLocator.registerService(this);
    }

    // Called by multiple threads from Telethon component
    @Override
    public void messageReceived(TelegramMessage msg) {
        System.out.println("Message received: " + msg.getText());
        matcher.newMessage(msg);
    }

    @Override
    public void matchFound(PatternMatch match) {
        System.out.println("Pattern match found in: " + match.getMessage().getText());
        notificator.notify(match);
    }
}
