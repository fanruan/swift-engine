package com.fr.swift.cloud.netty.rpc.client.sync;

import com.fr.swift.cloud.basic.Request;
import com.fr.swift.cloud.basic.Response;
import com.fr.swift.cloud.basic.SwiftResponse;
import com.fr.swift.cloud.log.SwiftLogger;
import com.fr.swift.cloud.log.SwiftLoggers;
import com.fr.swift.cloud.netty.rpc.client.AbstractRpcClientHandler;
import com.fr.swift.cloud.netty.rpc.exception.OverWaitException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * This class created on 2018/6/12
 *
 * @author Lucifer
 * @description
 * @since Advanced FineBI 5.0
 */
@ChannelHandler.Sharable
public class SyncRpcClientHandler extends AbstractRpcClientHandler<Response> {
    private static final Map<String, SyncRpcMessageEntity> SYNC_RPC_MESSAGE_ENTITY_MAP = new ConcurrentHashMap<>();
    public static final String POOL_KEY = "SyncRpcClientHandler";
    private static final SwiftLogger LOGGER = SwiftLoggers.getLogger(SyncRpcClientHandler.class);

    public SyncRpcClientHandler(String address) {
        super(address);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Response response) {
        if (SYNC_RPC_MESSAGE_ENTITY_MAP.containsKey(response.getRequestId())) {
            SyncRpcMessageEntity syncRpcMessageEntity = SYNC_RPC_MESSAGE_ENTITY_MAP.get(response.getRequestId());
            syncRpcMessageEntity.setResponse(response);
            syncRpcMessageEntity.getCountDownLatch().countDown();
            LOGGER.info("message arrive");
        } else {
            LOGGER.info("message is to late,such a waste");
        }
        LOGGER.info("Receive response :response id is {} ", response.getRequestId());
    }

    @Override
    public Response send(final Request request) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SyncRpcMessageEntity syncRpcMessageEntity = new SyncRpcMessageEntity(request, countDownLatch);
        SYNC_RPC_MESSAGE_ENTITY_MAP.put(request.getRequestId(), syncRpcMessageEntity);
        channel.writeAndFlush(request).sync().addListener((ChannelFutureListener) channelFuture -> LOGGER.info("Send request : " + request.getRequestId()));
        countDownLatch.await(5, TimeUnit.MINUTES);
        SYNC_RPC_MESSAGE_ENTITY_MAP.remove(request.getRequestId());
        if (countDownLatch.getCount() > 0) {
            SwiftResponse swiftResponse = new SwiftResponse();
            swiftResponse.setException(new OverWaitException());
            swiftResponse.setRequestId(request.getRequestId());
            return swiftResponse;
        }
        return syncRpcMessageEntity.getResponse();
    }
}
