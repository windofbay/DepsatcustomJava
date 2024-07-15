package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;

public class DepSatKURProcess implements IFormListenerFactory {
    @Override
    public IFormServerEventHandler getClassInstance(IFormReference objRef) {
        return new DepSatKURProcessHandler();
    }
}
