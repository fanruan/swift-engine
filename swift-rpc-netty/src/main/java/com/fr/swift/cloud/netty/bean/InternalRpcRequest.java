package com.fr.swift.cloud.netty.bean;


import com.fr.swift.cloud.basic.SwiftRequest;

/**
 * This class created on 2018/6/6
 *
 * @author Lucifer
 * @description
 * @since Advanced FineBI 5.0
 */
public class InternalRpcRequest extends SwiftRequest {

    private static final long serialVersionUID = -4343246390244615885L;

    @Override
    public RpcServiceType requestType() {
        return RpcServiceType.INTERNAL;
    }

}

