package com.miaoxingman.docker.client.core.command;

import javax.ws.rs.ClientErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.miaoxingman.docker.client.api.BadRequestException;
import com.miaoxingman.docker.client.api.ConflictException;
import com.miaoxingman.docker.client.api.DockerException;
import com.miaoxingman.docker.client.api.InternalServerErrorException;
import com.miaoxingman.docker.client.api.NotAcceptableException;
import com.miaoxingman.docker.client.api.NotFoundException;
import com.miaoxingman.docker.client.api.NotModifiedException;
import com.miaoxingman.docker.client.api.command.DockerCmd;
import com.miaoxingman.docker.client.api.command.DockerCmdExec;
import com.miaoxingman.docker.client.core.command.AbstrDockerCmd;


public abstract class AbstrDockerCmd<CMD_T extends DockerCmd<RES_T>, RES_T> implements DockerCmd<RES_T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstrDockerCmd.class);
    protected DockerCmdExec<CMD_T, RES_T> execution;

    public AbstrDockerCmd(DockerCmdExec<CMD_T, RES_T> execution) {
        Preconditions.checkNotNull(execution, "execution was not specified");
        this.execution = execution;
    }
    @Override
    public RES_T exec() throws DockerException {
        
        LOGGER.debug("Cmd: {}", this);
        
        try {
            return execution.exec((CMD_T)this);
        } catch (ClientErrorException exception) {
            int status = exception.getResponse().getStatus();
            switch(status) {
                case 204: return null;
                case 304: throw new NotModifiedException(exception);
                case 400: throw new BadRequestException(exception);
                case 404: throw new NotFoundException(exception);
                case 406: throw new NotAcceptableException(exception);
                case 409: throw new ConflictException(exception);
                case 500: throw new InternalServerErrorException(exception);
                default: throw toDockerException(exception);
            }
        }       
    }
    
    protected DockerException toDockerException(ClientErrorException exception) {
        LOGGER.info("toDockerException");
        return new DockerException(exception.getMessage(), exception.getResponse().getStatus(), exception);
    } 
}
