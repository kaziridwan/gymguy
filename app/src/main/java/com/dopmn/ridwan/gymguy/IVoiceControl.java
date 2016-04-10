package com.dopmn.ridwan.gymguy;

/**
 * Created by ridwan on 4/9/16.
 */
public interface IVoiceControl {
    public abstract void processVoiceCommands(String... voiceCommands); // This will be executed when a voice command was found

    public void restartListeningService(); // This will be executed after a voice command was processed to keep the recognition service activated
}
