package com.miaoxingman.docker.client.core.command;

public interface CommandFactory {
    public ListImagesCmd listImagesCmd();
    public PullImageCmd pullImageCmd(String repository);
    public RemoveImageCmd removeImageCmd(String imageId);
    public VersionCmd versionCmd();
}