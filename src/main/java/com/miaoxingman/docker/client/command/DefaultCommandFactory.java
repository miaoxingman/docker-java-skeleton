package com.miaoxingman.docker.client.command;

public class DefaultCommandFactory implements CommandFactory {

    @Override
    public VersionCmd versionCmd() {
        return new VersionCmd();
    }

}
