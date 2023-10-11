FROM alpine:3.15.5

ARG VERSION
ENV VERSION $VERSION

COPY fatalism-account-service-${VERSION}-github.zip /fatalism-account-service/fatalism-account-service-${VERSION}-github.zip

RUN unzip /fatalism-account-service/fatalism-account-service-${VERSION}-github.zip -d /fatalism-account-service \
    && rm -rf /fatalism-account-service/fatalism-account-service-${VERSION}-github.zip \
    && chmod +x /fatalism-account-service/scripts/startup.sh

FROM openjdk:8-jre-slim
LABEL maintainer="g632104866@gmail.com;finchcn@gmail.com;ameizi<sxyx2008@163.com>"

ENV APOLLO_RUN_MODE "Docker"
ENV SERVER_PORT 8090

RUN echo "deb http://mirrors.aliyun.com/debian/ bullseye main non-free contrib" > /etc/apt/sources.list \
    && echo "deb http://mirrors.aliyun.com/debian-security/ bullseye-security main" >> /etc/apt/sources.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends procps curl bash tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

COPY --from=0 /fatalism-account-service /fatalism-account-service

EXPOSE $SERVER_PORT

CMD ["/fatalism-account-service/scripts/startup.sh"]