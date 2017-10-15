package com.miaoxingman.docker.client.core.command;

import com.miaoxingman.docker.client.api.command.VersionCmd;
import com.miaoxingman.docker.client.api.model.Version;

public class VersionCmdImpl extends AbstrDockerCmd<VersionCmd, Version> implements VersionCmd {

    @Override
    public String toString() {
        return "version";
    }

    public VersionCmdImpl(VersionCmd.Exec exec) {
        super(exec);
    }
}
