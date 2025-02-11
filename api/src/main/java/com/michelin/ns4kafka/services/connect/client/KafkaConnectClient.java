package com.michelin.ns4kafka.services.connect.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.michelin.ns4kafka.models.Connector;
import com.michelin.ns4kafka.services.connect.KafkaConnectClientProxy;
import com.michelin.ns4kafka.services.connect.client.entities.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Client(KafkaConnectClientProxy.PROXY_PREFIX)
public interface KafkaConnectClient {
    @Get("/connectors?expand=info&expand=status")
    Map<String, ConnectorStatus> listAll(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster);

    @Put("/connector-plugins/{connectorClass}/config/validate")
    ConfigInfos validate(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connectorClass,
            @Body ConnectorSpecs connectorSpec);

    //@Retryable(predicate = ConnectRestService.RebalanceRetryPredicate.class)
    @Put("/connectors/{connector}/config")
    ConnectorInfo createOrUpdate(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector,
            @Body ConnectorSpecs connectorSpec);

    @Delete("/connectors/{connector}")
    HttpResponse delete(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector);


    @Get("/connector-plugins")
    List<ConnectorPluginInfo> connectPlugins(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster);

    @Get("/connectors/{connector}/status")
    ConnectorStateInfo status(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector);

    @Post("/connectors/{connector}/tasks/{taskid}/restart")
    HttpResponse restart(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector,
            int taskid);

    @Put("/connectors/{connector}/pause")
    HttpResponse pause(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector
    );

    @Put("/connectors/{connector}/resume")
    HttpResponse resume(
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_SECRET) String secret,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_KAFKA_CLUSTER) String cluster,
            @Header(value = KafkaConnectClientProxy.PROXY_HEADER_CONNECT_CLUSTER) String connectCluster,
            String connector
    );
}
