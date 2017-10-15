package com.miaoxingman.docker.client.core.command;

public class DefaultCommandFactory implements CommandFactory {

    @Override
    public VersionCmd versionCmd() {
        return new VersionCmd();
    }

    @Override
    public ListImagesCmd listImagesCmd() {
        return new ListImagesCmd();
    }

    @Override
    public PullImageCmd pullImageCmd(String repository) {
        return new PullImageCmd(repository);
    }

    @Override
    public RemoveImageCmd removeImageCmd(String imageId) {
        return new RemoveImageCmd(imageId);
    }

}
